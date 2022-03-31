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
package com.dezota.gis;

import javax.inject.Inject;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			ST_Intersection
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		binary
 *  @description	Returns a geometry object that is the geometric intersection of the source objects.
 *  @example		ST_AsText(ST_Intersection(ST_Point(1,1), ST_Point(1,1))) -> 'POINT (1 1)'
 *  				ST_AsText(ST_Intersection(ST_GeomFromText('LINESTRING(0 2, 0 0, 2 0)',true), ST_GeomFromText('LINESTRING(0 3, 0 1, 1 0, 3 0)',true))) -> 'MULTILINESTRING ((1 0, 2 0), (0 2, 0 1))'
 *  				ST_AsText(ST_Intersection(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true), ST_GeomFromText('POLYGON ((1 1, 4 1, 4 4, 1 4))',true))) -> 'POLYGON ((2 1, 2.666666666666667 1, 2 3, 2 1))'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_intersection", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.INTERNAL)
public class STIntersection implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.NullableVarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.NullableVarBinaryHolder geom2Param;

    @Output
    org.apache.arrow.vector.holders.NullableVarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        if (geom1Param.isSet == 0 || geom2Param.isSet == 0)
        {
            out.isSet = 0;
        }
        else {
            com.esri.core.geometry.ogc.OGCGeometry geom1;
            com.esri.core.geometry.ogc.OGCGeometry geom2;
            geom1 = com.esri.core.geometry.ogc.OGCGeometry
                    .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
            geom2 = com.esri.core.geometry.ogc.OGCGeometry
                    .fromBinary(geom2Param.buffer.nioBuffer(geom2Param.start, geom2Param.end - geom2Param.start));

            try {
                com.esri.core.geometry.ogc.OGCGeometry intersectionGeom = geom1.intersection(geom2);

                java.nio.ByteBuffer bufferedGeomBytes = intersectionGeom.asBinary();

                int outputSize = bufferedGeomBytes.remaining();
                buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
                out.start = 0;
                out.end = outputSize;
                buffer.setBytes(0, bufferedGeomBytes);
                out.isSet = 1;
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to perform intersections between '" + geom1.asText() + "' and '" + geom2.asText() + "'");
            }
        }
    }
}
