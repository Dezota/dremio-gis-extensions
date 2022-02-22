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
 *  @name			ST_JSONPath
 *  @args			([string] jsonPath, [string] jsonData)
 *  @returnType		string
 *  @description	Extract a portion of {{jsonData}} as a string by following the specified path in the JSON Object from {{jsonPath}}.
 *  @example		SELECT ST_JSONPath('/coordinates[Array][0]',ST_AsGeoJSON(ST_Envelope(the_geom))) FROM utah_county_taxparcels
 *  				SELECT ST_JSONPath('/crs[Object]/properties[Object]/name',ST_AsGeoJSON(ST_Envelope(the_geom))) FROM utah_county_taxparcels
 *  				Example JSON Paths (similar to XPath for XML):
 *  				'/data[Array]'
 *  				'/data[Array][1]/id[String]'
 *  				'/data[Array][1]/likes[Object]'
 *  				'/data[Array][1]/likes[Object]/summary[Object]/total_count[String]'
 *  				'/data[Array][3]'
 *  				'/data[Array][id=131272076894593_1420960724592382]/likes[Object]/summary[Object]/total_count'
 *  				'/fbids[String]'
 *  				'/quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/enterpriseValue[Object]/fmt[String]'
 *  				'/quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/forwardPE[Object]/raw[Double]'
 *  				'quoteSummary[6]/result[4][0]/defaultKeyStatistics[6]/sharesOutstanding[6]/raw[1]'
 *  				'quoteSummary[6]/result[Array]'
 *  				'quoteSummary[6]/result[Array][0]'
 *  				'quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/lastSplitDate[Object]/raw1[Long]'
 *  				'quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/sharesOutstanding[Object]/raw[Integer]'
 *
 *  @author			Brian Holman <bholman@dezota.com>
 *
 */


@FunctionTemplate(name = "st_jsonpath", scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STJSONPath implements SimpleFunction {
    @Param
    org.apache.arrow.vector.holders.VarCharHolder jsonPath;

    @Param
    org.apache.arrow.vector.holders.VarCharHolder jsonData;

    @Output
    org.apache.arrow.vector.holders.VarCharHolder out;

    @Inject
    org.apache.arrow.memory.ArrowBuf buffer;

    public void setup() {
    }

    public void eval() {
        String jsonDataString = com.dremio.gis.FunctionHelpers.toStringFromUTF8(jsonData.start, jsonData.end,
                jsonData.buffer);
        String jsonPathString = com.dremio.gis.FunctionHelpers.toStringFromUTF8(jsonPath.start, jsonPath.end,
                jsonPath.buffer);
        com.dremio.gis.JSONDataReader jdr = new com.dremio.gis.JSONDataReader();
        jdr.SUPPRESS_JSON_EXCEPTION = false;
        String outputString = null;
        org.codehaus.jettison.json.JSONObject jsonDataObj= null;
        try {
            jsonDataObj = new org.codehaus.jettison.json.JSONObject(jsonDataString);
            try {
                try {
                    outputString = jdr.getStringValue(jsonPathString, jsonDataObj);
                }
                catch (ClassCastException strException) {
                    try {
                        org.codehaus.jettison.json.JSONArray outputArray = jdr.getJSONArrayValue(jsonPathString, jsonDataObj);
                        outputString = outputArray.toString();
                    }
                    catch (ClassCastException arrayException) {
                        org.codehaus.jettison.json.JSONObject outputObj = jdr.getJSONObjectValue(jsonPathString, jsonDataObj);
                        outputString = outputObj.toString();
                    }
                }

                int outputSize = outputString.getBytes().length;
                buffer = out.buffer = buffer.reallocIfNeeded(outputSize);
                out.start = 0;
                out.end = outputSize;
                buffer.setBytes(0, outputString.getBytes());
            } catch (org.codehaus.jettison.json.JSONException e) {
                throw new IllegalArgumentException("Invalid JSON Path: "+e.toString());
            }
        } catch (org.codehaus.jettison.json.JSONException e) {
            throw new IllegalArgumentException("Invalid JSON Data: "+e.toString());
        }
    }
}