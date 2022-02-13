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
package com.dremio.gis;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

import javax.inject.Inject;

/*
 *
 * @summary Convert geometry from WKB
 *
 * @usage SELECT ST_AsText(ST_GeomFromWKB(the_geom_wkb))
 *        FROM table("cartodb".external_query(
 *        'SELECT *, ST_AsBinary(the_geom) as the_geom_wkb FROM acs.acs_2019_5yr_place_49_utah'))
 *
 * @author Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geomfromwkb", scope = FunctionTemplate.FunctionScope.SIMPLE,
  nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STGeomFromWKB implements SimpleFunction {
  @Param
  org.apache.arrow.vector.holders.VarBinaryHolder input;

  @Output
  org.apache.arrow.vector.holders.VarBinaryHolder out;

  @Inject
  org.apache.arrow.memory.ArrowBuf buffer;

  public void setup() {
  }

  public void eval() {
    com.esri.core.geometry.ogc.OGCGeometry geom;
    geom = com.esri.core.geometry.ogc.OGCGeometry.fromBinary(input.buffer.nioBuffer(input.start, input.end - input.start));

    java.nio.ByteBuffer geomBytes = geom.asBinary();
    
    int outputSize = geomBytes.remaining();
    buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
    out.start = 0;
    out.end = outputSize;
    buffer.setBytes(0, geomBytes);
  }
}
