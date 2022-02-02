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

public class StringFunctionHelpers {
    public static String toStringFromUTF8(int start, int end, org.apache.arrow.memory.ArrowBuf buffer) {
        byte[] buf = new byte[end - start];
        buffer.getBytes(start, buf, 0, end - start);
        String s = new String(buf, com.google.common.base.Charsets.UTF_8);
        return s;
    }
}
