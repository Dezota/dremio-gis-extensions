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
 *  @name			H3_ToCenterChild
 *  @args			([bigint] {h3Value}, [number] {childResolution})
 *  @returnType		bigint
 *  @description	Returns the center child (finer) index for the given hexagon at the given resolution.
 *  @example		H3_AsText(H3_ToCenterChild(H3_FromText('837b59fffffffff'), 4)) -> '847b591ffffffff'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_tocenterchild", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class H3ToCenterChild implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.BigIntHolder h3inValue;

    @Param
    org.apache.arrow.vector.holders.IntHolder childResolution;

    @Output
    org.apache.arrow.vector.holders.BigIntHolder h3outValue;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        com.uber.h3core.H3Core h3;
        try {
            h3 = com.uber.h3core.H3Core.newInstance();
            h3outValue.value = h3.h3ToCenterChild(h3inValue.value, childResolution.value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to initialize H3 library or bad H3 list.");
        }
    }
}
