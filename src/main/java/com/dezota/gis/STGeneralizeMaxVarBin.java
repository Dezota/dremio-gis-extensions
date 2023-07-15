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

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

import javax.inject.Inject;

/**
 *
 *  @name			ST_Generalize_MaxVarBin
 *  @args			([binary] {geometry}, [number] {numPasses})
 *  @returnType		binary
 *  @description	Simplifies geometries using the Douglas-Peucker algorithm if the binary size is greater than Dremio's max varbinary
 *                  with the closest value below that threshold. numPasses should be between 1-9 and will default to 3 if out of that range.
 *                  Value should be stored rather than derived with every query due to the number of iterations of the generalize function
 *                  required to find the closest value to Dremio's max to limit the loss of resolution.
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_generalize_maxvarbin", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeneralizeMaxVarBin implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.NullableIntHolder numPassesParam;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {

    }

    public void eval() {
        int numPasses = 3;
        if (numPassesParam.isSet == 1 && numPassesParam.value > 0 && numPassesParam.value < 10)
            numPasses = numPassesParam.value;

        com.esri.core.geometry.ogc.OGCGeometry geom1;
        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
        com.esri.core.geometry.ogc.OGCGeometry generalizedGeom =com.dezota.gis.FunctionHelpers.reduceGeometryMaxVarBinary(geom1, numPasses);
        java.nio.ByteBuffer bufferedGeomBytes = generalizedGeom.asBinary();

        int outputSize = bufferedGeomBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, bufferedGeomBytes);
    }
}
