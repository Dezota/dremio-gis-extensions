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

/**
 *
 *  @name			ST_PointN
 *  @args			([binary] {geometry}, [number] {index})
 *  @returnType		binary
 *  @description	Returns the point that is the nth vertex in an LineString or MultiPoint (1-based index)
 *  @example		ST_AsText(ST_PointN(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)'), 2)) -> 'POINT (3 2.2)'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_pointn", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STPointN implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.NullableIntHolder indexParam;

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

        int idx = indexParam.value - 1;  // 1-based UI, 0-based engine

        com.esri.core.geometry.Geometry esriGeom = geom1.getEsriGeometry();
        com.esri.core.geometry.SpatialReference geomSR = geom1.getEsriSpatialReference();

        com.esri.core.geometry.Point pn = null;
        switch(esriGeom.getType()) {
            case Line:
            case Polyline:
                com.esri.core.geometry.MultiPath lines = (com.esri.core.geometry.MultiPath)(esriGeom);
                try {
                    pn = lines.getPoint(idx);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid index.");
                }
                break;
            case MultiPoint:
                com.esri.core.geometry.MultiPoint mp = (com.esri.core.geometry.MultiPoint)(esriGeom);
                try {
                    pn = mp.getPoint(idx);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid index.");
                }
                break;
            default:  // ST_Geometry ST_PointN gives ERROR on Point or Polygon (on PostgreSQL)
                throw new IllegalArgumentException("Invalid type.");
        }

        com.esri.core.geometry.ogc.OGCGeometry pnOGC = com.esri.core.geometry.ogc.OGCGeometry.createFromEsriGeometry(pn, geomSR);
        java.nio.ByteBuffer pointBytes = pnOGC.asBinary();

        int outputSize = pointBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, pointBytes);
    }
}
