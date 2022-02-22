#!/bin/bash
node genFunctionMD.js > sqlFunctions.md
pandoc --toc sqlFunctions.md -H header.sty -f markdown -o DremioGISExtFuncRef.pdf
doctoc --notitle sqlFunctions.md
