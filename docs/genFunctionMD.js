/* Generate Markdown Function Reference from sqlFunctions.json used in Dremio DAC UI function reference */
const fs = require("fs");
var counter = 0;
fs.readFile("./sqlFunctions.json", "utf8", (err, response) => {
    if (err) {
        console.error(err);
        return;
    }
    console.log("# Dremio GIS Extensions - SQL Function Reference\n" +
        "## Authored by Brian Holman <bholman@dezota.com>\n\n" +
        "The **Dremio GIS Extensions** allows Dremio to perform standard GIS functions within Dremio SQL with 66 industry-standard GIS functions. "+
        "These extensions use the [*Esri Java Geometry Library*](https://github.com/Esri/geometry-api-java/wiki/) for the underlying implementation of the core geometry functions. "+
        "The author made heavy use of Esri's [*Spatial Framework for Hadoop*](https://github.com/Esri/spatial-framework-for-hadoop) as a reference for a similar implementation that also relies on the same library. \n\n" +
        "There were two significant gaps in the Geometry Library supplied by Esri that limited transforming geometries from `EPSG: 4326` to other coordinate systems and performing geodesic rather than 2D area and length calculations. "+
        "Geodesic area function helpers backing the `ST_GeodesicAreaWGS84` function are copied almost exactly from the [*Trino Geospatial Library*](https://github.com/trinodb/trino/tree/master/plugin/trino-geospatial) as found in our `FunctionHelpers.stSphericalArea()` and `FunctionHelpers.computeSphericalExcess()`. "+
        "Conversion to other coordinate systems in the `ST_Transform` function leverages the [Proj4J Library](https://trac.osgeo.org/proj4j/). "+
        "All of the referenced works are also published under the *Apache 2.0 License*."+
        "\n\n");
    const data = JSON.parse(response);
    data.forEach(function1);
});

function function1(currentValue, index) {
    if (currentValue["name"].toString().startsWith('ST_')) {
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
                console.log("| `SELECT "+queryResults[0]+"` | `"+queryResults[1]+ "` |")
            }
        }
        else
            console.log("-------------\n");
    }
}