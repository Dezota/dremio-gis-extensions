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

import javax.inject.Inject;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			ST_Difference
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		binary
 *  @description	Returns a geometry object that is the difference of the source objects.
 *  @example		ST_AsText(ST_Difference(ST_GeomFromText('MULTIPOINT (1 1, 1.5 1.5, 2 2)',true), ST_Point(1.5, 1.5))) -> 'MULTIPOINT ((1 1), (2 2))'
 *  				ST_AsText(ST_Difference(ST_GeomFromText('POLYGON ((0 0, 0 10, 10 10, 10 0))',true), ST_GeomFromText('POLYGON ((0 0, 0 5, 5 5, 5 0))',true))) -> 'POLYGON ((5 0, 10 0, 10 10, 0 10, 0 5, 5 5, 5 0))'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_difference", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STDifference implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom2Param;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1;
        com.esri.core.geometry.ogc.OGCGeometry geom2;
        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
        geom2 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom2Param.buffer.nioBuffer(geom2Param.start, geom2Param.end - geom2Param.start));

        com.esri.core.geometry.ogc.OGCGeometry diffGeom = geom1.difference(geom2);

        java.nio.ByteBuffer bufferedGeomBytes = diffGeom.asBinary();

        int outputSize = bufferedGeomBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, bufferedGeomBytes);
    }
}
