#!/bin/bash
node genFunctionMD.js > sqlFunctions.md
cat titleblock.txt sqlFunctions.md > sqlFunctionsPrePDF.md
pandoc --pdf-engine=xelatex --toc sqlFunctionsPrePDF.md -H header.sty -f markdown  -V 'mainfont:IBMPlexSans-Light'  -V 'monofont:IBMPlexMono' -o DremioGISExtFuncRef.pdf
rm sqlFunctionsPrePDF.md
doctoc --notitle sqlFunctions.md
