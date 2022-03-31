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
 *  @name			H3_Polyfill
 *  @args			[binary] {geometry}, [number] {resolution}
 *  @returnType		bigint[]
 *  @description	Returns an array with all the H3 cell indexes for the given polygon or multipolygon including automatically handling the inner holes.
 *  @example		H3_Polyfill(ST_GeomFromText('POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))',false), 1) -> [582059465512058900,582072659651592200,582068261605081100,582081455744614400,582855511930568700,582063863558570000,582077057698103300,582851113884057600]
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_polyfill", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL, derivation = H3ListOutputDerivation.class)
public class H3Polyfill implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder inputGeomParam;

    @Param
    org.apache.arrow.vector.holders.IntHolder resolution;

    @Output
    org.apache.arrow.vector.complex.writer.BaseWriter.ComplexWriter out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.uber.h3core.H3Core h3;
        com.esri.core.geometry.ogc.OGCGeometry inputGeom;
        if (!(resolution.value >= 0 && resolution.value <= 15))
            throw new IllegalArgumentException("H3 Resolution must be between 0 and 15.");
        try {
            h3 = com.uber.h3core.H3Core.newInstance();
            inputGeom = com.esri.core.geometry.ogc.OGCGeometry
                    .fromBinary(inputGeomParam.buffer.nioBuffer(inputGeomParam.start, inputGeomParam.end - inputGeomParam.start));

            if (inputGeom != null) {
                com.esri.core.geometry.ogc.OGCGeometry unionGeom;
                if (inputGeom.geometryType().equals("Polygon")) {
                    unionGeom = inputGeom;
                } else if (inputGeom.geometryType().equals("MultiPolygon")) {
                    /* Union any MultiPolygons as a Single Polygon */
                    com.esri.core.geometry.SpatialReference geomSR = inputGeom.getEsriSpatialReference();
                    com.esri.core.geometry.GeometryCursor inputGeomCur = inputGeom.getEsriGeometryCursor();
                    com.esri.core.geometry.GeometryCursor unionGeomCur =
                            com.esri.core.geometry.OperatorUnion.local().execute(inputGeomCur, geomSR, null);
                    unionGeom = com.esri.core.geometry.ogc.OGCGeometry.createFromEsriCursor(unionGeomCur, geomSR);
                } else {
                    throw new IllegalArgumentException("Must be a Polygon or MultiPolygon.");
                }
                com.esri.core.geometry.ogc.OGCPolygon polyCastGeom = (com.esri.core.geometry.ogc.OGCPolygon) unionGeom;
                com.esri.core.geometry.ogc.OGCLineString extRingGeom = polyCastGeom.exteriorRing();

                /* Create the polyfill points from the external ring of the polygon */
                java.util.List<com.uber.h3core.util.GeoCoord> polyfillPoints = new java.util.ArrayList<>();
                for (int i = 0; i < extRingGeom.numPoints(); i++) {
                    com.uber.h3core.util.GeoCoord singlePoint = new com.uber.h3core.util.GeoCoord(extRingGeom.pointN(i).Y(), extRingGeom.pointN(i).X());
                    polyfillPoints.add(singlePoint);
                }

                /* Iterate through the interior rings for the holes */
                java.util.List<java.util.List<com.uber.h3core.util.GeoCoord>> listOfHoles = new java.util.ArrayList<>();
                for (int i = 0; i < polyCastGeom.numInteriorRing(); i++) {
                    java.util.ArrayList<com.uber.h3core.util.GeoCoord> holePoints = new java.util.ArrayList<>();
                    com.esri.core.geometry.ogc.OGCLineString holeGeom = polyCastGeom.interiorRingN(i);
                    for (int j = 0; j < holeGeom.numPoints(); j++) {
                        com.uber.h3core.util.GeoCoord singlePoint = new com.uber.h3core.util.GeoCoord(holeGeom.pointN(j).Y(), holeGeom.pointN(j).X());
                        holePoints.add(singlePoint);
                    }
                    listOfHoles.add(holePoints);
                }

                /* Perform H3 polyfill action */
                java.util.List<Long> polyfillValues = h3.polyfill(polyfillPoints, listOfHoles, resolution.value);
                java.util.Iterator<Long> polyfillIterator = polyfillValues.iterator();

                org.apache.arrow.vector.complex.writer.BaseWriter.ListWriter listWriter = out.rootAsList();
                listWriter.startList();
                while(polyfillIterator.hasNext()) {
                    Long Lv = (java.lang.Long)(polyfillIterator.next());
                    listWriter.bigInt().writeBigInt(Lv.longValue());
                }
                listWriter.endList();
            } else {
                out.rootAsList();
                return;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Bad geometry or unable to initialize H3 library: " + e.getMessage());
        }
    }
}
