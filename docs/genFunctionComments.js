/* Generate Header Comments from sqlFunctions.json used in Dremio DAC UI function reference */
const fs = require("fs");

fs.readFile("./sqlFunctions.json", "utf8", (err, response) => {
    if (err) {
        console.error(err);
        return;
    }
    const data = JSON.parse(response);
    data.forEach(function1);
});

function function1(currentValue, index) {
    if (currentValue["name"].toString().startsWith('ST_')) {
        console.log("\n/**\n" + " *");
        console.log(" *  @name\t\t\t" + currentValue["name"]);
        console.log(" *  @args\t\t\t" + currentValue["args"]);
        console.log(" *  @returnType\t\t" + currentValue["returnType"]);
        console.log(" *  @description\t" + currentValue["description"]);
        if (currentValue["example"])
            console.log(" *  @example\t\t" + currentValue["example"].replaceAll('\n','\n *  \t\t\t\t'));
        console.log(" *\n" +
            " *  @author\t\t\tBrian Holman <bholman@dezota.com>\n" +
            " *\n" +
            " */");
    }
}