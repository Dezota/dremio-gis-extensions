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
 *  @name			ST_Disjoint
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		boolean
 *  @description	Returns true if the intersection of the two geometries produces an empty set; otherwise, it returns false.
 *  @example		ST_Disjoint(ST_GeomFromText('LINESTRING (0 0, 0 1)',true), ST_GeomFromText('LINESTRING (1 1, 1 0)',true)) -> true
 *  				ST_Disjoint(ST_GeomFromText('LINESTRING (0 0, 1 1)',true), ST_GeomFromText('LINESTRING (1 0, 0 1)',true)) -> false
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_disjoint", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STDisjoint implements SimpleFunction {
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

        int isDisjoint = geom1.disjoint(geom2) ? 1 : 0;

        out.value = isDisjoint;
    }
}
