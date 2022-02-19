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
 *  @name			ST_GeodesicLengthWGS84
 *  @args			([binary] {geometry})
 *  @returnType		number
 *  @description	Returns distance along line on WGS84 spheroid, in meters, for geographic coordinates. Requires the geometry to be in the WGS84 spatial reference.
 *  @example		SELECT ST_GeodesicLengthWGS84(ST_GeomFromText('MultiLineString((0.0 80.0, 0.3 80.4))', 4326)) -> 45026.96274781222
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geodesiclengthwgs84", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeodesicLengthWGS84 implements SimpleFunction {
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

        com.esri.core.geometry.Geometry esriGeom = geom1.getEsriGeometry();
        switch(esriGeom.getType()) {
            case Point:
            case MultiPoint:
                out.value=0.;
                break;
            default:
                com.esri.core.geometry.MultiPath lines = (com.esri.core.geometry.MultiPath)(esriGeom);
                int nPath = lines.getPathCount();
                double length = 0.;
                for (int ix = 0; ix < nPath; ix++) {
                    int curPt = lines.getPathStart(ix);
                    int pastPt = lines.getPathEnd(ix);
                    com.esri.core.geometry.Point fromPt = lines.getPoint(curPt);
                    com.esri.core.geometry.Point toPt = null;
                    for (int vx = curPt+1; vx < pastPt; vx++) {
                        toPt = lines.getPoint(vx);
                        length += com.esri.core.geometry.GeometryEngine.geodesicDistanceOnWGS84(fromPt, toPt);
                        fromPt = toPt;
                    }
                }
                out.value = length;
                break;
        }
    }
}
