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
 *  @name			ST_Overlaps
 *  @args			([binary] {geometry1}, [binary] {geometry2})
 *  @returnType		boolean
 *  @description	Returns true if {{geometry1}} overlaps {{geometry2}}.
 *  @example		ST_Overlaps(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true)) -> true
 *  				ST_Overlaps(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true)) -> false
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_overlaps", scope = FunctionTemplate.FunctionScope.SIMPLE,
  nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STOverlaps implements SimpleFunction {
  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder geom1Param;

  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder geom2Param;

  @Output
  org.apache.arrow.vector.holders.BitHolder out;

  @Inject
  org.apache.arrow.memory.ArrowBuf buffer;

  public void setup() {
  }

  public void eval() {
    com.esri.core.geometry.ogc.OGCGeometry geom1;
    com.esri.core.geometry.ogc.OGCGeometry geom2;

    geom1 = com.esri.core.geometry.ogc.OGCGeometry
        .fromBinary(geom1Param.buffer.nioBuffer(geom1Param.start, geom1Param.end - geom1Param.start));
    geom2 = com.esri.core.geometry.ogc.OGCGeometry
        .fromBinary(geom2Param.buffer.nioBuffer(geom2Param.start, geom2Param.end - geom2Param.start));

    int overlaps = geom1.overlaps(geom2) ? 1 : 0;

    out.value = overlaps;
  }
}
