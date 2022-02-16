/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
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
 * @summary Simplifies geometries using the Douglas-Peucker algorithm
 *
 * @usage SELECT ST_Generalize(geometry ST_Geometry, maxDeviation FLOAT, removeDegenerateParts BOOLEAN)
 *
 *        SELECT ST_GeoSize(ST_Generalize(ST_GeomFromEWKB(the_geom), 0.000001, true)) AS Gen
 *        FROM "clickhouse"."acs_gis".counties WHERE name = 'Millard'
 *
 * @author Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_generalize", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeneralize implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.Float8Holder maxDeviation;

    @Param
    org.apache.arrow.vector.holders.BitHolder removeDegenerateParts;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {

    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1;
        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        com.esri.core.geometry.SpatialReference geomSR = geom1.getEsriSpatialReference();
        com.esri.core.geometry.GeometryCursor geom1cur = geom1.getEsriGeometryCursor();
        com.esri.core.geometry.GeometryCursor generalizedGeomCur =
                com.esri.core.geometry.OperatorGeneralize.local().execute(geom1cur, maxDeviation.value, removeDegenerateParts.value == 1, null);
        com.esri.core.geometry.ogc.OGCGeometry generalizedGeom = com.esri.core.geometry.ogc.OGCGeometry.createFromEsriCursor(generalizedGeomCur, geomSR);
        java.nio.ByteBuffer bufferedGeomBytes = generalizedGeom.asBinary();

        int outputSize = bufferedGeomBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, bufferedGeomBytes);
    }
}
