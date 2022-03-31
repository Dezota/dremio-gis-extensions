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
 *  @name			ST_GeometryN
 *  @args			([binary] {geometry}, [number] {index})
 *  @returnType		binary
 *  @description	Takes a geometry collection and an integer index (1-based index) and returns the nth geometry object in the collection.
 *  @example		ST_AsText(ST_GeometryN(ST_GeomFromText('MULTIPOINT (10 40, 40 30, 20 20, 30 10)',true), 3)) -> 'POINT (20 20)'
 *  				ST_AsText(ST_GeometryN(ST_GeomFromText('MULTILINESTRING ((2 4, 10 10), (20 20, 7 8))',true), 2)) -> 'LINESTRING (20 20, 7 8)'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geometryn", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeometryN implements SimpleFunction {
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

        com.esri.core.geometry.ogc.OGCGeometry outGeom = null;
        if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiPoint.TYPE)
            outGeom = ((com.esri.core.geometry.ogc.OGCMultiPoint)geom1).geometryN(idx);
        else if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiLineString.TYPE)
            outGeom = ((com.esri.core.geometry.ogc.OGCMultiLineString)geom1).geometryN(idx);
        else if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiPolygon.TYPE)
            outGeom = ((com.esri.core.geometry.ogc.OGCMultiPolygon)geom1).geometryN(idx);
        else
            throw new IllegalArgumentException("Geometry must be MultiPoint, MultiLineString, or MultiPolygon.");

        java.nio.ByteBuffer pointBytes = outGeom.asBinary();

        int outputSize = pointBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, pointBytes);
    }
}
