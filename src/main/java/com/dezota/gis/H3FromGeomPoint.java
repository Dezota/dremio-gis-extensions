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
 *  @name			H3_FromGeomPoint
 *  @args			([binary] {pointGeom}, [number] {resolution})
 *  @returnType		bigint
 *  @description	Returns the H3 cell index that the point belongs to in the required {{resolution}}. It will return `null` for non-point geometry and throw an error for resolution outside the valid range [0,15].
 *  @example		H3_AsText(H3_FromGeomPoint(ST_Point(40.4168, -3.7038), 4)) -> '847b59dffffffff'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_fromgeompoint", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.INTERNAL)
public class H3FromGeomPoint implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.NullableVarBinaryHolder pointGeom;

    @Param
    org.apache.arrow.vector.holders.NullableIntHolder resolution;

    @Output
    org.apache.arrow.vector.holders.NullableBigIntHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        if (pointGeom.isSet == 0) {
            out.value = 0L;
            out.isSet = 0;
        } else {
            com.uber.h3core.H3Core h3;
            com.esri.core.geometry.ogc.OGCGeometry geom;
            if (!(resolution.value >= 0 && resolution.value <= 15))
                throw new IllegalArgumentException("H3 Resolution must be between 0 and 15.");
            try {
                h3 = com.uber.h3core.H3Core.newInstance();
                geom = com.esri.core.geometry.ogc.OGCGeometry
                        .fromBinary(pointGeom.buffer.nioBuffer(pointGeom.start, pointGeom.end - pointGeom.start));

                if (geom != null && geom.geometryType().equals("Point")) {
                    out.isSet = 1;
                    out.value = h3.geoToH3(((com.esri.core.geometry.ogc.OGCPoint) geom).Y(), ((com.esri.core.geometry.ogc.OGCPoint) geom).X(), resolution.value);
                } else {
                    out.value = 0L;
                    out.isSet = 0;
                }
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Bad geometry or unable to initialize H3 library.");
            }
        }
    }
}