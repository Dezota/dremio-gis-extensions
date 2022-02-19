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

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateTransform;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;
import com.dremio.exec.expr.annotations.Workspace;

/**
 *
 *  @name			ST_Transform
 *  @args			([binary] {geometry}, [number] {sourceSRID}, [number] {targetSRID})
 *  @returnType		binary
 *  @description	Takes the two-dimensional geometry as input and returns values converted from the spatial source reference specified by {{sourceSRID}} to the one specified by {{targetSRID}}.
 *  @example		ST_AsText(ST_Transform(ST_GeomFromText('POLYGON ((-114.04702599994988 39.90609700007656, -114.0500520000997 37.0001909997149, -109.04517199998776 36.99897700038832, -109.05002599989996 41.000691000389395, -111.04681499981234 40.997875000031286, -111.04671399965133 42.00170200004732, -114.04147700036322 41.99387299963928, -114.04702599994988 39.90609700007656))'), 4326, 3857)) -> 'POLYGON ((-12695656.860801652 4852305.919673687, -12695993.71359747 4439133.410181124, -12138853.020503571 4438964.195256694, -12139393.365302108 5012443.58678148, -12361674.899993964 5012028.231889712, -12361663.65670747 5161234.398812287, -12695039.148993252 5160061.69329091, -12695656.860801652 4852305.919673687))'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_transform", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STTransform implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

    @Param
    org.apache.arrow.vector.holders.NullableIntHolder sridSrcParam;

    @Param
    org.apache.arrow.vector.holders.NullableIntHolder sridTgtParam;

    @Workspace
    CoordinateTransform transform;

    @Workspace
    CRSFactory crsFactory;

    @Workspace
    int sridTgt;

    @Output
    org.apache.arrow.vector.holders.VarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
        int sridSrc = sridSrcParam.value;
        sridTgt = sridTgtParam.value;

        org.osgeo.proj4j.CoordinateReferenceSystem srcCrs =
                new org.osgeo.proj4j.CRSFactory().createFromName("EPSG:" + sridSrc);

        org.osgeo.proj4j.CoordinateReferenceSystem tgtCrs =
                new org.osgeo.proj4j.CRSFactory().createFromName("EPSG:" + sridTgt);

        transform = new org.osgeo.proj4j.BasicCoordinateTransform(srcCrs, tgtCrs);
    }

    public void eval() {
        com.esri.core.geometry.ogc.OGCGeometry geomSrc = com.esri.core.geometry.ogc.OGCGeometry
                .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));

        org.osgeo.proj4j.ProjCoordinate result = new org.osgeo.proj4j.ProjCoordinate();
        com.esri.core.geometry.SpatialReference sr = com.esri.core.geometry.SpatialReference.create(sridTgt);
        java.nio.ByteBuffer geomBytes = null;

        if (geomSrc != null && geomSrc.geometryType().equals("Point")) {
            com.esri.core.geometry.ogc.OGCPoint pointGeom = (com.esri.core.geometry.ogc.OGCPoint) geomSrc;
            result = transform.transform(new org.osgeo.proj4j.ProjCoordinate(pointGeom.X(), pointGeom.Y()), result);

            geomBytes = new com.esri.core.geometry.ogc.OGCPoint(
                    new com.esri.core.geometry.Point(result.x, result.y), sr).asBinary();
        } else {
            com.esri.core.geometry.Geometry esriGeom = geomSrc.getEsriGeometry();
            com.esri.core.geometry.MultiVertexGeometry vertexGeom =
                    com.esri.core.geometry.VertexGeomAccessor.getVertexGeometry(esriGeom);
            for (int i = 0; i < vertexGeom.getPointCount(); i++) {
                com.esri.core.geometry.Point point = vertexGeom.getPoint(i);
                result = transform.transform(new org.osgeo.proj4j.ProjCoordinate(point.getX(), point.getY()), result);
                point.setXY(result.x, result.y);
                vertexGeom.setPoint(i, point);
            }

            com.esri.core.geometry.ogc.OGCGeometry tGeom =
                    com.esri.core.geometry.ogc.OGCGeometry.createFromEsriGeometry(esriGeom, sr);
            geomBytes = tGeom.asBinary();
        }

        int outputSize = geomBytes.remaining();
        buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
        out.start = 0;
        out.end = outputSize;
        buffer.setBytes(0, geomBytes);
    }
}
