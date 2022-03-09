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

import javax.inject.Inject;

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			ST_GeomFromText
 *  @args			([string] {wktString}, [boolean] {ignoreErrors}, [number] {SRID})
 *  @returnType		binary
 *  @description	Takes a well-known text representation and a spatial reference ID and returns a geometry object. Set {{ignoreErrors}} to {{true}} to ignore bad data or {{false}} to fail and show the bad WKT value.
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "st_geomfromtext", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.INTERNAL)
public class STGeomFromTextSrid implements SimpleFunction {
  @Param
  org.apache.arrow.vector.holders.NullableVarCharHolder input;

  @Param
  org.apache.arrow.vector.holders.BitHolder ignoreErrors;

  @Param
  org.apache.arrow.vector.holders.IntHolder sridParam;

  @Output
  org.apache.arrow.vector.holders.NullableVarBinaryHolder out;

  @Inject
  org.apache.arrow.memory.ArrowBuf buffer;

  public void setup() {
  }

  public void eval() {
    if (input.isSet == 0) {
      out.isSet = 0;
    } else {
      int srid = sridParam.value;
      String wktText = com.dremio.gis.FunctionHelpers.toStringFromUTF8(input.start, input.end, input.buffer);
      try {
        com.esri.core.geometry.ogc.OGCGeometry geom = com.esri.core.geometry.ogc.OGCGeometry.fromText(wktText);
        geom.setSpatialReference(com.esri.core.geometry.SpatialReference.create(srid));
        if (!geom.isEmpty()) {
          java.nio.ByteBuffer pointBytes = geom.asBinary();

          int outputSize = pointBytes.remaining();
          out.isSet = 1;
          buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
          out.start = 0;
          out.end = outputSize;
          buffer.setBytes(0, pointBytes);
        } else {
          out.isSet = 0;
        }
      } catch (Exception e) {
        if (ignoreErrors.value == 1)
          out.isSet = 0;
        else
          throw new IllegalArgumentException("Malformed WKT Geometry: '" + wktText + "'");
      }
    }
  }
}
