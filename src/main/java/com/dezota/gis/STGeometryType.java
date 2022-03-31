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
 *  @name			ST_GeometryType
 *  @args			([binary] {geometry})
 *  @returnType		string
 *  @description	Takes a geometry object and returns its geometry type (for example, Point, Line, Polygon, MultiPoint) as a string.
 *  @example		ST_GeometryType(ST_Point(1.5, 2.5)) -> 'ST_POINT'
 *  				ST_GeometryType(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true)) -> 'ST_LINESTRING'
 *  				ST_GeometryType(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true)) -> 'ST_POLYGON'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geometrytype", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeometryType implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Output
    org.apache.arrow.vector.holders.VarCharHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        StringBuffer geomTypeBuffer = new StringBuffer(geom1.geometryType().toUpperCase());
        geomTypeBuffer.insert(0, "ST_");

        int outputSize = geomTypeBuffer.toString().getBytes().length;
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, geomTypeBuffer.toString().getBytes());
    }
}
