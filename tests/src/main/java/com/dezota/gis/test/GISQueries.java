/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dezota.gis.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;

/**
 * Simple utility to run each of the SQL examples from the documentation JSON file
 */

public class GISQueries {
    public static void main(String[] args) throws Exception {
        try {
            // Get JDBC Connect Details from config properties
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            Properties configProps = new Properties();
            configProps.load(input);

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of SQLFuncDocs
            java.util.List<SQLFuncDoc> sqlfuncs = java.util.Arrays.asList(mapper.readValue(java.nio.file.Paths.get("../docs/sqlFunctions.json").toFile(), SQLFuncDoc[].class));
            java.util.Iterator<SQLFuncDoc> sqlfuncsIterator = sqlfuncs.iterator();

            Properties jdbcProps = new Properties();
            jdbcProps.setProperty("user",configProps.getProperty("dremioUser"));
            jdbcProps.setProperty("password",configProps.getProperty("dremioPassword"));

            Connection conn = null;
            Statement stmt = null;

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(configProps.getProperty("dremioSource"), jdbcProps);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            while (sqlfuncsIterator.hasNext()) {
                SQLFuncDoc sqlfunc = (SQLFuncDoc)(sqlfuncsIterator.next());

                String tags[] = sqlfunc.getTags();

                if (tags != null)
                    if (java.util.Arrays.asList(tags).contains("gis")) {
                        if (sqlfunc.getExample() != null) {
                            String[] AllLinesArray = sqlfunc.getExample().split(System.lineSeparator());
                            for (int i=0; i < AllLinesArray.length; i++)
                            {
                                String[] exampleLine = AllLinesArray[i].split("->", 2);
                                String sql;
                                if (exampleLine[0].startsWith("ST_") || exampleLine[0].startsWith("H3_"))
                                    sql = "SELECT " + exampleLine[0];
                                else
                                    sql = exampleLine[0];
                                System.out.println("Executing statement...");
                                System.out.println(sql);
                                try {
                                    ResultSet rs = stmt.executeQuery(sql);

                                    System.out.print("Printing the result\n");
                                    System.out.print("-------------------\n");

                                    while (rs.next()) {
                                        System.out.print("Actual Results:" + rs.getString(1) + "\n");
                                        System.out.print("Expected Results:" + exampleLine[1] + "\n");
                                    }

                                    System.out.print("-------------------\n");
                                    rs.close();
                                }
                                catch (Exception e)
                                {
                                    System.err.println("QUERY FAILED: " + sql);
                                }
                            }
                        }
                    }
            }

            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
