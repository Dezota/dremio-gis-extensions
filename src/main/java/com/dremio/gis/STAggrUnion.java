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

import com.dremio.exec.expr.AggrFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;
import com.dremio.exec.expr.annotations.Workspace;
import org.apache.arrow.vector.holders.NullableBigIntHolder;

/**
 *
 *  @name			ST_AggrUnion
 *  @args			([binary] {geometry})
 *  @returnType		binary
 *  @description	Returns a single geometry that is the union of all aggregate input geometries.
 *  @example		WITH GEOMLIST AS (SELECT ST_GeomFromText('polygon ((20 30, 30 30, 30 40, 20 40, 20 30))', true) AS GEOM1, ST_GeomFromText('polygon ((40 40, 40 60, 60 60, 60 40, 40 40))', true) AS GEOM2) SELECT ST_AsText(ST_AggrUnion(GEOM)) FROM GEOMLIST UNPIVOT ("GEOM" for "COL" in  (GEOM1, GEOM2)) -> 'MULTIPOLYGON (((20 30, 30 30, 30 40, 20 40, 20 30)), ((40 40, 60 40, 60 60, 40 60, 40 40)))'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_aggrunion", scope = FunctionTemplate.FunctionScope.POINT_AGGREGATE,
        nulls = FunctionTemplate.NullHandling.INTERNAL)
public class STAggrUnion implements AggrFunction{
    @Param
    org.apache.arrow.vector.holders.NullableVarBinaryHolder inGeomParam;

    @Workspace
    org.apache.arrow.vector.holders.NullableVarBinaryHolder wipGeomValue;

    @Workspace
    org.apache.arrow.vector.holders.NullableBigIntHolder nonNullCount;

    @Output
    org.apache.arrow.vector.holders.NullableVarBinaryHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    /*
     * Initialize evaluator
     */
    public void setup() {
        nonNullCount = new NullableBigIntHolder();
        nonNullCount.isSet = 1;
        nonNullCount.value = 0;
    }

    /*
     * Iterate is called once per row in a table
     */
    @Override
    public void add() {
        com.esri.core.geometry.ogc.OGCGeometry inGeom;
        com.esri.core.geometry.ogc.OGCGeometry wipGeom;
        sout: {
            /* If the current geometry is null */
            if (inGeomParam.isSet == 0) {
                // processing nullable input and the value is null, so don't do anything...
                break sout;
            }

            inGeom = com.esri.core.geometry.ogc.OGCGeometry.fromBinary(inGeomParam.buffer.nioBuffer(inGeomParam.start, inGeomParam.end - inGeomParam.start));
            if (inGeom.isEmpty()) {
                break sout;
            }

            if (nonNullCount.value == 0)
            {
                java.nio.ByteBuffer bufferedGeomBytes = inGeom.asBinary();

                int outputSize = bufferedGeomBytes.remaining();
                buffer = wipGeomValue.buffer = buffer.reallocIfNeeded(outputSize);
                wipGeomValue.start = 0;
                wipGeomValue.end = outputSize;
                buffer.setBytes(0, bufferedGeomBytes);
            }
            else
            {
                wipGeom = com.esri.core.geometry.ogc.OGCGeometry.fromBinary(wipGeomValue.buffer.nioBuffer(wipGeomValue.start, wipGeomValue.end - wipGeomValue.start));
                com.esri.core.geometry.ogc.OGCGeometry unionGeom = wipGeom.union(inGeom);

                java.nio.ByteBuffer bufferedGeomBytes = unionGeom.asBinary();
                int outputSize = bufferedGeomBytes.remaining();
                buffer = wipGeomValue.buffer = buffer.reallocIfNeeded(outputSize);
                wipGeomValue.start = 0;
                wipGeomValue.end = outputSize;
                buffer.setBytes(0, bufferedGeomBytes);
            }
            wipGeomValue.isSet = 1;
            nonNullCount.value++;
        } // end of sout block
    }

    @Override
    public void output() {
        if (nonNullCount.value > 0) {
            java.nio.ByteBuffer bufferedGeomBytes = wipGeomValue.buffer.nioBuffer(wipGeomValue.start, wipGeomValue.end - wipGeomValue.start);
            int outputSize = bufferedGeomBytes.remaining();
            buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
            out.start = 0;
            out.end = outputSize;
            buffer.setBytes(0, bufferedGeomBytes);
            out.isSet = 1;
        } else {
            out.isSet = 0;
        }
    }

    @Override
    public void reset() {
        nonNullCount.value = 0;
    }
}