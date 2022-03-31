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
 *  @name			ST_Equals
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		boolean
 *  @description	Returns true if the two geometries occupy the same space even if they have a different number of vertices, otherwise it returns false.
 *  @example		ST_Equals(ST_GeomFromText('LINESTRING (0 0, 1 1)',true),ST_GeomFromText('LINESTRING (1 1, 0 0)',true)) -> true
 *  				ST_Equals(ST_GeomFromText('LINESTRING (0 0, 1 1)',true),ST_GeomFromText('LINESTRING (1 0, 0 1)',true)) -> false
 *  				ST_Equals(ST_GeomFromText('LINESTRING (0 0, 3 3)',true),ST_GeomFromText('LINESTRING (3 3, 2 2, 1 1, 0 0)',true)) -> true
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_equals", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STEquals implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom2Param;

    @Output
    org.apache.arrow.vector.holders.BitHolder out;

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

        int equals = geom1.Equals(geom2) ? 1 : 0;

        out.value = equals;
    }
}
