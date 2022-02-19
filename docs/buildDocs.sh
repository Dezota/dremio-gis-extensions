#!/bin/bash
node genFunctionMD.js > sqlFunctions.md
pandoc sqlFunctions.md -H header.sty -f markdown -o DremioGISExtFuncRef.pdf
