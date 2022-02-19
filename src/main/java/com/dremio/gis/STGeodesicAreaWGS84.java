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

import javax.inject.Inject;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			ST_GeodesicAreaWGS84
 *  @args			([binary] {geometry})
 *  @returnType		number
 *  @description	Returns the area in square meters of a geometry on the Earth's surface using the spherical model. Requires the geometry to be in the WGS84 spatial reference.
 *  @example		ST_GeodesicAreaWGS84(ST_GeomFromText('POLYGON ((-114.04702599994988 39.90609700007656, -114.0500520000997 37.0001909997149, -109.04517199998776 36.99897700038832, -109.05002599989996 41.000691000389395, -111.04681499981234 40.997875000031286, -111.04671399965133 42.00170200004732, -114.04147700036322 41.99387299963928, -114.04702599994988 39.90609700007656))'))/4047 AS utah_acreage -> 5.416484897473004E7
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geodesicareawgs84", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeodesicAreaWGS84 implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Output
    org.apache.arrow.vector.holders.Float8Holder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geom1;

        geom1 = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
        int WGS84 = 4326;
        if (geom1.SRID() != WGS84)
            throw new IllegalArgumentException("The geometry must be in the WGS84 spatial reference.");

        com.esri.core.geometry.GeometryCursor geomCur = geom1.getEsriGeometryCursor();
        double area = 0.0;
        for (com.esri.core.geometry.Geometry subGeometry = geomCur.next(); subGeometry != null; subGeometry = geomCur.next()) {
            if (subGeometry.isEmpty())
                continue;

            com.esri.core.geometry.Geometry.Type subGeomType = subGeometry.getType();

            if (subGeomType == com.esri.core.geometry.Geometry.Type.Polygon) {
                com.esri.core.geometry.Polygon polyCast = (com.esri.core.geometry.Polygon) subGeometry;
                area += com.dremio.gis.FunctionHelpers.stSphericalArea(polyCast);

                continue;
            }

            throw new com.esri.core.geometry.GeometryException("internal error");
        }
        out.value = area;
    }
}