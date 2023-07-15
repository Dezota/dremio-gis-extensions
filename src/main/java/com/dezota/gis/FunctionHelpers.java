/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dezota.gis;

import static java.lang.Math.PI;
import static java.lang.Math.toRadians;

public class FunctionHelpers {
    private static final double EARTH_RADIUS_KM = 6371.0072;
    private static final double EARTH_RADIUS_M = EARTH_RADIUS_KM * 1000.0;

    public static String toStringFromUTF8(int start, int end, org.apache.arrow.memory.ArrowBuf buffer) {
        byte[] buf = new byte[end - start];
        buffer.getBytes(start, buf, 0, end - start);
        String s = new String(buf, com.google.common.base.Charsets.UTF_8);
        return s;
    }

    /**
     * Converts a hexadecimal string to a byte array.
     * The hexadecimal digit symbols are case-insensitive.
     *
     * @param hex a string containing hex digits
     * @return an array of bytes with the value of the hex string
     */
    public static byte[] hexToBytes(String hex)
    {
        int byteLen = hex.length() / 2;
        byte[] bytes = new byte[byteLen];

        for (int i = 0; i < hex.length() / 2; i++) {
            int i2 = 2 * i;
            if (i2 + 1 > hex.length())
                throw new IllegalArgumentException("Hex string has odd length");

            int nib1 = hexToInt(hex.charAt(i2));
            int nib0 = hexToInt(hex.charAt(i2 + 1));
            byte b = (byte) ((nib1 << 4) + (byte) nib0);
            bytes[i] = b;
        }
        return bytes;
    }

    private static int hexToInt(char hex)
    {
        int nib = Character.digit(hex, 16);
        if (nib < 0)
            throw new IllegalArgumentException("Invalid hex digit: '" + hex + "'");
        return nib;
    }

    /* Adapted from plugin/trino-geospatial/src/main/java/io/trino/plugin/geospatial/GeoFunctions.java */
    public static Double stSphericalArea(com.esri.core.geometry.Polygon polygon)
    {
        // See https://www.movable-type.co.uk/scripts/latlong.html
        // and http://osgeo-org.1560.x6.nabble.com/Area-of-a-spherical-polygon-td3841625.html
        // and https://www.element84.com/blog/determining-if-a-spherical-polygon-contains-a-pole
        // for the underlying Math

        double sphericalExcess = 0.0;

        int numPaths = polygon.getPathCount();
        for (int i = 0; i < numPaths; i++) {
            double sign = polygon.isExteriorRing(i) ? 1.0 : -1.0;
            sphericalExcess += sign * Math.abs(computeSphericalExcess(polygon, polygon.getPathStart(i), polygon.getPathEnd(i)));
        }

        // Math.abs is required here because for Polygons with a 2D area of 0
        // isExteriorRing returns false for the exterior ring
        return Math.abs(sphericalExcess * EARTH_RADIUS_M * EARTH_RADIUS_M);
    }

    /* Adapted from plugin/trino-geospatial/src/main/java/io/trino/plugin/geospatial/GeoFunctions.java */
    private static double computeSphericalExcess(com.esri.core.geometry.Polygon polygon, int start, int end)
    {
        // Our calculations rely on not processing the same point twice
        if (polygon.getPoint(end - 1).equals(polygon.getPoint(start))) {
            end = end - 1;
        }

        if (end - start < 3) {
            // A path with less than 3 distinct points is not valid for calculating an area
            throw new com.esri.core.geometry.GeometryException("Polygon is not valid: a loop contains less then 3 vertices.");
        }

        com.esri.core.geometry.Point point = new com.esri.core.geometry.Point();
        // Initialize the calculator with the last point
        polygon.getPoint(end - 1, point);

        double sphericalExcess = 0;
        double courseDelta = 0;
        boolean firstPoint = true;
        double firstInitialBearing = 0;
        double previousFinalBearing = 0;
        double previousPhi = toRadians(point.getY());
        double previousCos = Math.cos(previousPhi);
        double previousSin = Math.sin(previousPhi);
        double previousTan = Math.tan(previousPhi / 2);
        double previousLongitude = toRadians(point.getX());

        for (int i = start; i < end; i++) {
            polygon.getPoint(i, point);
            double phi = toRadians(point.getY());
            double tan = Math.tan(phi / 2);
            double longitude = toRadians(point.getX());

            // We need to check for that specifically
            // Otherwise calculating the bearing is not deterministic
            if (longitude == previousLongitude && phi == previousPhi) {
                throw new com.esri.core.geometry.GeometryException("Polygon is not valid: it has two identical consecutive vertices");
            }

            double deltaLongitude = longitude - previousLongitude;
            sphericalExcess += 2 * Math.atan2(Math.tan(deltaLongitude / 2) * (previousTan + tan), 1 + previousTan * tan);

            double cos = Math.cos(phi);
            double sin = Math.sin(phi);
            double sinOfDeltaLongitude = Math.sin(deltaLongitude);
            double cosOfDeltaLongitude = Math.cos(deltaLongitude);

            // Initial bearing from previous to current
            double y = sinOfDeltaLongitude * cos;
            double x = previousCos * sin - previousSin * cos * cosOfDeltaLongitude;
            double initialBearing = (Math.atan2(y, x) + 2 * Math.PI) % (2 * Math.PI);

            // Final bearing from previous to current = opposite of bearing from current to previous
            double finalY = -sinOfDeltaLongitude * previousCos;
            double finalX = previousSin * cos - previousCos * sin * cosOfDeltaLongitude;
            double finalBearing = (Math.atan2(finalY, finalX) + PI) % (2 * Math.PI);

            // When processing our first point we don't yet have a previousFinalBearing
            if (firstPoint) {
                // So keep our initial bearing around, and we'll use it at the end
                // with the last final bearing
                firstInitialBearing = initialBearing;
                firstPoint = false;
            }
            else {
                courseDelta += (initialBearing - previousFinalBearing + 3 * Math.PI) % (2 * Math.PI) - PI;
            }

            courseDelta += (finalBearing - initialBearing + 3 * Math.PI) % (2 * Math.PI) - PI;

            previousFinalBearing = finalBearing;
            previousCos = cos;
            previousSin = sin;
            previousPhi = phi;
            previousTan = tan;
            previousLongitude = longitude;
        }

        // Now that we have our last final bearing, we can calculate the remaining course delta
        courseDelta += (firstInitialBearing - previousFinalBearing + 3 * Math.PI) % (2 * Math.PI) - PI;

        // The courseDelta should be 2Pi or - 2Pi, unless a pole is enclosed (and then it should be ~ 0)
        // In which case we need to correct the spherical excess by 2Pi
        if (Math.abs(courseDelta) < PI / 4) {
            sphericalExcess = Math.abs(sphericalExcess) - 2 * Math.PI;
        }

        return sphericalExcess;
    }

    private static com.esri.core.geometry.ogc.OGCGeometry generalizeGeometry(com.esri.core.geometry.ogc.OGCGeometry geom1, float maxDeviation)
    {
        com.esri.core.geometry.SpatialReference geomSR = geom1.getEsriSpatialReference();
        com.esri.core.geometry.GeometryCursor geom1cur = geom1.getEsriGeometryCursor();
        com.esri.core.geometry.GeometryCursor generalizedGeomCur =
                com.esri.core.geometry.OperatorGeneralize.local().execute(geom1cur, maxDeviation, true, null);
        return (com.esri.core.geometry.ogc.OGCGeometry.createFromEsriCursor(generalizedGeomCur, geomSR));
    }

    /*
        We start from the least generalized value and use an algorithm inspired by binary search that approximates
        and refines the closest value through multiple passes through a narrower range
     */

    public static com.esri.core.geometry.ogc.OGCGeometry reduceGeometryMaxVarBinary(com.esri.core.geometry.ogc.OGCGeometry originalGeom, int numPasses)
    {
        /* These values are derived from empirical usage with US Census Place Polygons generalized with the Douglas-Peucker algorithm */
        final float deviationDivisor  = 10000000000.0F;
        long low                      = 1L;
        long high                     = 10000000000L;
        long step                     = high / 1000L;
        final long stepReducer        = 10L;
        /* Dremio Parquet Files have a max varbinary size of 65536 - in tests with 3 loops yields 19 iterations yields a favorable balance */
        final int maxBinarySize       = 65536;
        /* Three passes given a sufficiently close value to the maxBinarySize */

        com.esri.core.geometry.ogc.OGCGeometry currentGeom = originalGeom;
        int currentBinarySize = originalGeom.asBinary().remaining();

        int loops = 0;
        if (currentBinarySize > maxBinarySize) {
            while (numPasses > loops && step > 0) {
                for (long i = low; i <= high; i = i + step) {
                    currentGeom = generalizeGeometry(originalGeom, i / deviationDivisor);
                    currentBinarySize = currentGeom.asBinary().remaining();

                    if (currentBinarySize <= maxBinarySize) {
                        high = i;
                        low = i - step;
                        break;
                    }
                }
                loops++;
                step = step / stepReducer;
            }
        }
        return currentGeom;
    }
}
