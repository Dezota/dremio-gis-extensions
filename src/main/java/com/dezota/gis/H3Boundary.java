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
 *  @name			H3_Boundary
 *  @args			([bigint] {h3Value})
 *  @returnType		binary
 *  @description	Returns a polygon geography representing the H3 cell.
 *  @example		ST_AsText(H3_Boundary(H3_FromLongLat(40.4168, -3.7038, 15))) -> 'POLYGON ((-3.703802360352346 40.41680913267208, -3.7038075007518416 40.41680558484906, -3.703806130063667 40.41680018598506, -3.7037996189769617 40.41679833494421, -3.7037944785779335 40.41680188276699, -3.7037958492651386 40.416807281630845, -3.703802360352346 40.41680913267208))'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_boundary", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class H3Boundary implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.BigIntHolder h3Value;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.uber.h3core.H3Core h3;
        try {
            h3 = com.uber.h3core.H3Core.newInstance();

            if (h3.h3IsValid(h3Value.value)) {
                /* Return a list of points corresponding to the GeoBoundary of the H3 cell */
                java.util.List<com.uber.h3core.util.GeoCoord> longlatPairs = h3.h3ToGeoBoundary(h3Value.value);

                /* Iterate through the points and create a polygon */
                com.esri.core.geometry.Polygon poly = new com.esri.core.geometry.Polygon();
                java.util.Iterator<com.uber.h3core.util.GeoCoord> longlatPairsIterator = longlatPairs.iterator();
                com.uber.h3core.util.GeoCoord singlePoint = (com.uber.h3core.util.GeoCoord)(longlatPairsIterator.next());

                poly.startPath(singlePoint.lng, singlePoint.lat);
                while (longlatPairsIterator.hasNext()) {
                    singlePoint = (com.uber.h3core.util.GeoCoord)(longlatPairsIterator.next());
                    poly.lineTo(singlePoint.lng, singlePoint.lat);
                }

                /* Convert the polygon to the Geospatial format */
                com.esri.core.geometry.ogc.OGCPolygon ogcPoly = new com.esri.core.geometry.ogc.OGCPolygon(poly, com.esri.core.geometry.SpatialReference.create(4326));
                java.nio.ByteBuffer bufferedGeomBytes = ogcPoly.asBinary();

                int outputSize = bufferedGeomBytes.remaining();
                buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
                out.start = 0;
                out.end = outputSize;
                buffer.setBytes(0, bufferedGeomBytes);
            } else
                throw new IllegalArgumentException("Not a valid H3 value.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to initialize H3 library.");
        }
    }
}
