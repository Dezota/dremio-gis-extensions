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

import javax.inject.Inject;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			ST_Relate
 *  @args			([binary] {geometry1}, [binary] {geometry2}, [string] {relation})
 *  @returnType		binary
 *  @description	Compares the two geometries and returns true if the geometries meet the conditions specified by the DE-9IM pattern matrix string, otherwise, false is returned.
 *  @example		ST_Relate(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), '****T****') -> true
 *  				ST_Relate(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), 'T********') -> false
 *  				ST_Relate(ST_GeomFromText('LINESTRING (0 0, 3 3)',true), ST_GeomFromText('LINESTRING (1 1, 4 4)',true), 'T********') -> true
 *  				ST_Relate(ST_GeomFromText('LINESTRING (0 0, 3 3)',true), ST_GeomFromText('LINESTRING (1 1, 4 4)',true), '****T****') -> false
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_relate", scope = FunctionTemplate.FunctionScope.SIMPLE,
  nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STRelate implements SimpleFunction {
  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder geom2Param;

  @Param
  org.apache.arrow.vector.holders.VarCharHolder matrixParam;
  
  @Output
  org.apache.arrow.vector.holders.BitHolder out;

  @Inject
  org.apache.arrow.memory.ArrowBuf buffer;

  public void setup() {
  }

  public void eval() {
    com.esri.core.geometry.ogc.OGCGeometry geom1;
    com.esri.core.geometry.ogc.OGCGeometry geom2;
    String matrix = com.dezota.gis.FunctionHelpers.toStringFromUTF8(matrixParam.start,
        matrixParam.end, matrixParam.buffer);

    geom1 = com.esri.core.geometry.ogc.OGCGeometry
        .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
    geom2 = com.esri.core.geometry.ogc.OGCGeometry
        .fromBinary(geom2Param.buffer.nioBuffer(geom2Param.start, geom2Param.end - geom2Param.start));

    int relates = geom1.relate(geom2, matrix) ? 1 : 0;

    out.value = relates;
  }
}
