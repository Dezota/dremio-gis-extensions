/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dremio.gis;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;
import javax.inject.Inject;

/*
 *
 * @summary Convert the raw geometry string from Postgres/PostGIS that comes to Dremio as a Hex encoded string
 *
 * @usage SELECT ST_AsText(ST_GeomFromEWKB(the_geom)) FROM "cartodb".acs."acs_2019_5yr_place_49_utah"
 *
 * @author Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geomfromewkb", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeomFromEWKB implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarCharHolder input;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;


    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;


    public void setup() {
    }

    public void eval() {
        // Convert the UTF-8 representation of a Postgres EWKB binary field to a String
        String ewkbText = com.dremio.gis.StringFunctionHelpers.toStringFromUTF8(input.start, input.end,
                input.buffer);

        // Remove the SRID representation to create a wkbText representation
        StringBuffer sb = new StringBuffer(ewkbText);
        String wkbText = sb.delete(8,16).toString();

        // Convert the HEX String Representation to Binary and wrap in a Byte Buffer
        byte[] ewkbByteArr=com.dremio.gis.StringFunctionHelpers.hexToBytes(ewkbText);
        java.nio.ByteBuffer ewkbBB = java.nio.ByteBuffer.wrap(ewkbByteArr);
        byte[] wkbByteArr=com.dremio.gis.StringFunctionHelpers.hexToBytes(wkbText);
        java.nio.ByteBuffer wkbBB = java.nio.ByteBuffer.wrap(wkbByteArr);

        // Determine the byte order of the EWKB from the first byte
        byte byteOrderWKB = ewkbBB.get();
        // Always set byte order, since it may change from geometry to geometry
        if(byteOrderWKB == com.dremio.gis.WKBConstants.wkbNDR)
        {
            ewkbBB.order(java.nio.ByteOrder.LITTLE_ENDIAN);
            wkbBB.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        }
        else if(byteOrderWKB == com.dremio.gis.WKBConstants.wkbXDR)
        {
            ewkbBB.order(java.nio.ByteOrder.BIG_ENDIAN);
            wkbBB.order(java.nio.ByteOrder.BIG_ENDIAN);
        }
        else
        {
            throw new IllegalArgumentException("Unknown geometry byte order (not NDR or XDR): " + byteOrderWKB);
        }

        // Determine the geomType and if an SRID exists from the next int
        int typeInt = ewkbBB.getInt();
        int geomType = (typeInt & 0xffff) % 1000; // MultiPolygon, etc.
        boolean hasSRID = (typeInt & 0x20000000) != 0;
        if (geomType >= com.dremio.gis.WKBConstants.wkbPoint && geomType <= com.dremio.gis.WKBConstants.wkbGeometryCollection) {
            if (hasSRID) {
                int SRID = ewkbBB.getInt(); // 4326, etc.
                com.esri.core.geometry.ogc.OGCGeometry geom = com.esri.core.geometry.ogc.OGCGeometry.fromBinary(wkbBB);
                geom.setSpatialReference(com.esri.core.geometry.SpatialReference.create(SRID));
                java.nio.ByteBuffer geomBytes = geom.asBinary();

                int outputSize = geomBytes.remaining();
                buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
                out.start = 0;
                out.end = outputSize;
                buffer.setBytes(0, geomBytes);
            } else
                throw new IllegalArgumentException("Missing expected SRID from EWKB data");
        }
        else
            throw new IllegalArgumentException("Invalid geomType: "+geomType);

    }
}
