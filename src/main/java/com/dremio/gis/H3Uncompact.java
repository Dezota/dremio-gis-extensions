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

import com.dremio.exec.expr.SimpleFunction;
import com.dremio.exec.expr.annotations.FunctionTemplate;
import com.dremio.exec.expr.annotations.Output;
import com.dremio.exec.expr.annotations.Param;

/**
 *
 *  @name			H3_Uncompact
 *  @args			([bigint] {h3Value}[], [number] {resolution})
 *  @returnType		bigint[]
 *  @description	Returns an array with the indexes of a set of hexagons of the same {{resolution}} that represent the same area as the compacted input hexagons.
 *  @example		H3_Uncompact(H3_Wrap(H3_FromText('847b59dffffffff')),5) -> [601148757970518000,601148759044259800,601148760118001700,601148761191743500,601148762265485300,601148763339227100,601148764412969000]
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */

@FunctionTemplate(name = "h3_uncompact", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL, derivation = com.dremio.gis.H3ListOutputDerivation.class)
public class H3Uncompact implements SimpleFunction {
    @Param
    org.apache.arrow.vector.complex.reader.FieldReader in;

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
        try {
            h3 = com.uber.h3core.H3Core.newInstance();
            if (!in.isSet() || in.readObject() == null) {
                out.rootAsList();
                return;
            }

            if (in.getMinorType() != org.apache.arrow.vector.types.Types.MinorType.LIST) {
                throw new java.lang.UnsupportedOperationException(
                        String.format("'h3_uncompact' is supported only on LIST type input. Given input type : %s",
                                in.getMinorType().toString()
                        )
                );
            }

            java.util.Collection<Long> input = (java.util.Collection<Long>) in.readObject();

            /* Perform H3 compact action */
            java.util.List<Long> uncompactValues = h3.uncompact(input, resolution.value);
            java.util.Iterator<Long> uncompactIterator = uncompactValues.iterator();

            org.apache.arrow.vector.complex.writer.BaseWriter.ListWriter listWriter = out.rootAsList();
            listWriter.startList();
            while(uncompactIterator.hasNext()) {
                Long Lv = (java.lang.Long)(uncompactIterator.next());
                listWriter.bigInt().writeBigInt(Lv.longValue());
            }
            listWriter.endList();

        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to initialize H3 library or bad H3 list.");
        }
    }
}
