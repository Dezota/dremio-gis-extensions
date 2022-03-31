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
import com.esri.core.geometry.ogc.OGCLineString;

import javax.inject.Inject;

/**
 *
 *  @name			ST_IsRing
 *  @args			([binary] {geometry})
 *  @returnType		boolean
 *  @description	Returns true if the geometry is a linestring and the linestring is closed and simple.
 *  @example		ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 3 4, 0 4, 0 0)',true)) -> true
 *  				ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 1 1, 1 2, 2 1, 1 1, 0 0)',true)) -> false
 *  				ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 3 4)',true)) -> false
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_isring", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STIsRing implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Output
    org.apache.arrow.vector.holders.BitHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1;

        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCLineString.TYPE) {
            OGCLineString lns = (OGCLineString)geom1;
            int isRing = lns.isClosed() && lns.isSimple() ? 1 : 0;

            out.value = isRing;
        }
        else
            throw new IllegalArgumentException("Geometry must be LineString.");
    }
}
