/* Generate Markdown Function Reference from sqlFunctions.json used in Dremio DAC UI function reference */
const fs = require("fs");
var counter = 0;
fs.readFile("./sqlFunctions.json", "utf8", (err, response) => {
    if (err) {
        console.error(err);
        return;
    }
    console.log("# GIS Extensions for Dremio - SQL Function Reference\n" +
        "## Author\n\n" +
        "[Brian Holman](mailto:bholman@dezota.com)\n\n" +
        "## Legal Disclaimer\n\n" +
        "*This independent project is not affiliated with, sponsored, or endorsed by Dremio Corporation. Dremio is a registered trademark of Dremio Corporation and they retain all trademark and other intellectual property rights.  \"Dremio\" is used here by reference to integrating with their published [User-Defined Functions Specification](https://www.dremio.com/hub-additional/) for advanced users to develop their own custom functions for use in SQL queries.*\n\n"+
        "![DAC with GIS extensions](./dremio_dac_with_gis.jpg)\n\n"+
        "## Third-Party Libraries\n\n" +
        "The **GIS Extensions** allow Dremio to perform standard GIS functions within Dremio SQL with 72 industry-standard GIS functions. "+
        "These extensions use the [*Esri Java Geometry Library*](https://github.com/Esri/geometry-api-java/wiki/) for the underlying implementation of the core geometry functions. "+
        "The author made heavy use of Esri's [*Spatial Framework for Hadoop*](https://github.com/Esri/spatial-framework-for-hadoop) as a reference for a similar implementation that also relies on the same library. \n\n" +
        "There were two significant gaps in the Geometry Library supplied by Esri that limited transforming geometries from `EPSG: 4326` to other coordinate systems and performing geodesic rather than 2D area and length calculations. "+
        "Geodesic area function helpers backing the `ST_GeodesicAreaWGS84` function are copied almost exactly from the [*Trino Geospatial Library*](https://github.com/trinodb/trino/tree/master/plugin/trino-geospatial) as found in our `FunctionHelpers.stSphericalArea()` and `FunctionHelpers.computeSphericalExcess()`. "+
        "Conversion to other coordinate systems in the `ST_Transform` function leverages the [Proj4J Library](https://trac.osgeo.org/proj4j/). "+
        "All of the referenced works are also published under the *Apache 2.0 License*."+
        "\n\n" +
        "");
    const data = JSON.parse(response);
    data.forEach(function1);
});

function function1(currentValue, index) {
    if (currentValue["name"].toString().startsWith('ST_') || currentValue["name"].toString().startsWith('H3_')) {
        counter++;
        console.log("## (" + counter+") "+currentValue["name"] + '\n');
        console.log("### Definition");
        console.log(currentValue["description"].replaceAll('{{', '*').replaceAll('}}', '*') + '\n');
        console.log("### Syntax");
        console.log("<code>" + currentValue["name"] + currentValue["args"].replaceAll('[', '').replaceAll(']', '').replaceAll('{', '<em>').replaceAll('}', '</em>') + "</code>\n");
        console.log("### Return Type");
        console.log("`" + currentValue["returnType"] + "`\n");
        if (currentValue["example"]) {
            console.log("### Examples");
            const exampleLines = currentValue["example"].split('\n');
            console.log("| Query      | Result |");
            console.log("| ----------- | ----------- |");
            for (var i = 0; i < exampleLines.length; i++) {
                queryResults = exampleLines[i].split(" -> ");
                console.log("| "+ (queryResults[0].startsWith("ST_") || queryResults[0].startsWith("H3_") ? ("`SELECT "+queryResults[0]+"`") : "`"+queryResults[0]+"`")+" | "+(queryResults[1] ? "`"+queryResults[1]+"`" : "" )+ " |")
            }
        }
        else
            console.log("-------------\n");
    }
}