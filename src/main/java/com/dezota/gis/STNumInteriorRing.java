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
 *  @name			ST_NumInteriorRing
 *  @args			([binary] {geometry})
 *  @returnType		number
 *  @description	Returns the number of interior rings in the polygon geometry.
 *  @example		ST_NumInteriorRing(ST_GeomFromText('POLYGON ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true)) -> 1
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_numinteriorring", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STNumInteriorRing implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Output
    org.apache.arrow.vector.holders.IntHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1;

        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        int outInt = 0;
        if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCPolygon.TYPE)
            outInt = ((com.esri.core.geometry.ogc.OGCPolygon)(geom1)).numInteriorRing();
        else
            throw new IllegalArgumentException("Geometry must be a Polygon.");

        out.value = outInt;
    }
}
