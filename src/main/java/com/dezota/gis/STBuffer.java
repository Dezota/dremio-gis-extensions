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
 *  @name			ST_Buffer
 *  @args			([binary] {geometry}, [number] {distance})
 *  @returnType		binary
 *  @description	Returns geometry object that is the buffer surrounding source {{geometry}} at specified {{distance}}.
 *  @example		ST_Buffer(ST_Point(0, 0), 1) -> polygon approximating a unit circle
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_buffer", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STBuffer implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param(constant = true)
    org.apache.arrow.vector.holders.Float8Holder bufferRadiusParam;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        double bufferRadius = bufferRadiusParam.value;

        com.esri.core.geometry.ogc.OGCGeometry geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        com.esri.core.geometry.ogc.OGCGeometry bufferedGeom = geom1.buffer(bufferRadius);

        java.nio.ByteBuffer bufferedGeomBytes = bufferedGeom.asBinary();

        int outputSize = bufferedGeomBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, bufferedGeomBytes);
    }
}
