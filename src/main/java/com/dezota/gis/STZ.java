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

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

import javax.inject.Inject;

/**
 *
 *  @name			ST_Z
 *  @args			([binary] {geometry})
 *  @returnType		number
 *  @description	Takes a Point as an input parameter and returns its elevation (z) coordinate.
 *  @example		ST_Z(ST_GeomFromText('POINT Z (5 7 9)',true)) -> 9.0
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_z", scope = FunctionTemplate.FunctionScope.SIMPLE,
  nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STZ implements SimpleFunction {
  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder geomParam;

  @Output
  org.apache.arrow.vector.holders.Float8Holder out;

  @Inject
  org.apache.arrow.memory.ArrowBuf buffer;

  public void setup() {
  }

  public void eval() {

    com.esri.core.geometry.ogc.OGCGeometry geom;

    geom = com.esri.core.geometry.ogc.OGCGeometry
        .fromBinary(geomParam.buffer.nioBuffer(geomParam.start, geomParam.end - geomParam.start));

    if(geom != null && geom.geometryType().equals("Point")){
      out.value = ((com.esri.core.geometry.ogc.OGCPoint) geom).Z();
    } else {
      out.value = Double.NaN;
    }
  }
}
