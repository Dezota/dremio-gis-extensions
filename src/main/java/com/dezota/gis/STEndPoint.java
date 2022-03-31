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
package com.dezota.gis;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

import javax.inject.Inject;

/**
 *
 *  @name			ST_EndPoint
 *  @args			([binary] {geometry})
 *  @returnType		binary
 *  @description	Returns the last point of a Linestring.
 *  @example		ST_AsText(ST_EndPoint(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true))) -> 'POINT(3.0 2.2)'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_endpoint", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STEndPoint implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

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

        com.esri.core.geometry.Geometry esriGeom = geom1.getEsriGeometry();
        com.esri.core.geometry.SpatialReference geomSR = geom1.getEsriSpatialReference();

        if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCLineString.TYPE) {
            com.esri.core.geometry.Point pn = null;
            com.esri.core.geometry.MultiPath lines = (com.esri.core.geometry.MultiPath) (esriGeom);
            pn = lines.getPoint(lines.getPointCount()-1);

            com.esri.core.geometry.ogc.OGCGeometry pnOGC = com.esri.core.geometry.ogc.OGCGeometry.createFromEsriGeometry(pn, geomSR);
            java.nio.ByteBuffer pointBytes = pnOGC.asBinary();

            int outputSize = pointBytes.remaining();
            buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
            out.start = 0;
            out.end = outputSize;
            buffer.setBytes(0, pointBytes);
        } else
            throw new IllegalArgumentException("Invalid type.");
    }
}
