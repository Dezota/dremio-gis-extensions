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
 *  @name			ST_NumGeometries
 *  @args			([binary] {geometry})
 *  @returnType		number
 *  @description	Returns the number of geometries in the geometry collection.
 *  @example		ST_NumGeometries(ST_GeomFromText('MULTIPOINT ((10 40), (40 30), (20 20), (30 10))',true)) -> 4
 *  				ST_NumGeometries(ST_GeomFromText('MULTILINESTRING ((2 4, 10 10), (20 20, 7 8))',true)) -> 2
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_numgeometries", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STNumGeometries implements SimpleFunction {
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
        if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiPoint.TYPE)
            outInt = ((com.esri.core.geometry.ogc.OGCMultiPoint)geom1).numGeometries();
        else if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiLineString.TYPE)
            outInt = ((com.esri.core.geometry.ogc.OGCMultiLineString)geom1).numGeometries();
        else if (geom1.geometryType() == com.esri.core.geometry.ogc.OGCMultiPolygon.TYPE)
            outInt = ((com.esri.core.geometry.ogc.OGCMultiPolygon)geom1).numGeometries();
        else
            throw new IllegalArgumentException("Geometry must be MultiPoint, MultiLineString, or MultiPolygon.");

        out.value = outInt;
    }
}
