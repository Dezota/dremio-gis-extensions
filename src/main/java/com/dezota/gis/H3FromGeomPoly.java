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
 *  @name			H3_FromGeomPoly
 *  @args			([binary] {polyGeom})
 *  @returnType		bigint
 *  @description	Returns the smallest H3 cell that completely encloses the polygon with a dynamic resolution. It will return `null` if there isn't a value to fit the polygon and throw an error if a non-polygon geometry is supplied.
 *  @example		H3_FromGeomPoly(ST_GeomFromText('POLYGON ((-111.69808887205492 40.238737891364416, -111.69837535651395 40.23873740478143, -111.6983745544178 40.2384602506758, -111.69808807598443 40.23846160818078, -111.69808887205492 40.238737891364416))',true)) -> 622175171754917887
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_fromgeompoly", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.INTERNAL)
public class H3FromGeomPoly implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.NullableVarBinaryHolder polyGeom;

    @Output
    org.apache.arrow.vector.holders.NullableBigIntHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        if (polyGeom.isSet == 0) {
            out.value = 0L;
            out.isSet = 0;
        } else {
            com.uber.h3core.H3Core h3;
            com.esri.core.geometry.ogc.OGCGeometry inputGeom;
            try {
                h3 = com.uber.h3core.H3Core.newInstance();
                inputGeom = com.esri.core.geometry.ogc.OGCGeometry
                        .fromBinary(polyGeom.buffer.nioBuffer(polyGeom.start, polyGeom.end - polyGeom.start));
                if (inputGeom.geometryType().equals("Polygon") || inputGeom.geometryType().equals("MultiPolygon")) {
                    com.esri.core.geometry.Geometry esriGeom = inputGeom.getEsriGeometry();
                    com.esri.core.geometry.SpatialReference geomSR = inputGeom.getEsriSpatialReference();

                    /* Calculated the Convex Hull of the Polygon or MultiPolygon */
                    com.esri.core.geometry.Geometry convexHullEsriGeom = com.esri.core.geometry.GeometryEngine.convexHull(esriGeom);
                    com.esri.core.geometry.ogc.OGCGeometry convexHullGeom = com.esri.core.geometry.ogc.OGCGeometry.createFromEsriGeometry(convexHullEsriGeom, geomSR);

                    /* Calculated the Centroid of the Convex Hull */
                    com.esri.core.geometry.ogc.OGCPoint centroidPoint = (com.esri.core.geometry.ogc.OGCPoint)convexHullGeom.centroid();

                    /* Start from the smallest size resolution and work up until the convex hull fits completely within the hex value polygon */
                    Long FinalH3Value = 0L;
                    int FinalH3Resolution = -1;
                    for (int resolution = 15; resolution > 0; resolution--) {
                        Long h3Value = h3.geoToH3(centroidPoint.Y(), centroidPoint.X(), resolution);
                        /* Return a list of points corresponding to the GeoBoundary of the H3 cell */
                        java.util.List<com.uber.h3core.util.GeoCoord> longlatPairs = h3.h3ToGeoBoundary(h3Value);

                        /* Iterate through the points and create a polygon */
                        com.esri.core.geometry.Polygon poly = new com.esri.core.geometry.Polygon();
                        java.util.Iterator<com.uber.h3core.util.GeoCoord> longlatPairsIterator = longlatPairs.iterator();
                        com.uber.h3core.util.GeoCoord singlePoint = (com.uber.h3core.util.GeoCoord)(longlatPairsIterator.next());

                        poly.startPath(singlePoint.lng, singlePoint.lat);
                        while (longlatPairsIterator.hasNext()) {
                            singlePoint = (com.uber.h3core.util.GeoCoord)(longlatPairsIterator.next());
                            poly.lineTo(singlePoint.lng, singlePoint.lat);
                        }

                        /* Convert the H3 polygon to the Geospatial format */
                        com.esri.core.geometry.ogc.OGCPolygon h3PolyGeom = new com.esri.core.geometry.ogc.OGCPolygon(poly, geomSR);

                        /* If the user supplied polygon fits within the generated polygon of the H3 value then break out of the loop and return */
                        if (convexHullGeom.within(h3PolyGeom)) {
                            FinalH3Value = h3Value;
                            FinalH3Resolution = resolution;
                            break;
                        }
                    }

                    /* Return null if no match -- shouldn't happen or return the H3 value */
                    if (FinalH3Resolution == -1)
                    {
                        out.value = 0L;
                        out.isSet = 0;
                    }
                    else {
                        out.value = FinalH3Value.longValue();
                        out.isSet = 1;
                    }
                } else {
                    throw new IllegalArgumentException("Must be a Polygon or MultiPolygon.");
                }
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Bad geometry or unable to initialize H3 library.");
            }
        }
    }
}
