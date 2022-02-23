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
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;

import javax.inject.Inject;

/**
 *
 *  @name			ST_EnvIntersects
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		boolean
 *  @description	Returns true if the envelopes of {{geometry1}} and {{geometry2}} intersect, otherwise returns false.
 *  @example		ST_EnvIntersects(ST_GeomFromText('LINESTRING (0 0, 1 1)'), ST_GeomFromText('LINESTRING (1 3, 2 2)')) -> false
 *  				ST_EnvIntersects(ST_GeomFromText('LINESTRING (0 0, 2 2)'), ST_GeomFromText('LINESTRING (1 0, 3 2)')) -> true
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_envintersects", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STEnvIntersects implements SimpleFunction {
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

        Geometry geometry1 = geom1.getEsriGeometry();
        Geometry geometry2 = geom2.getEsriGeometry();
        Envelope env1 = new Envelope(), env2 = new Envelope();
        geometry1.queryEnvelope(env1);
        geometry2.queryEnvelope(env2);

        int intersects = env1.isIntersecting(env2) ? 1 : 0;

        out.value = intersects;
    }
}
