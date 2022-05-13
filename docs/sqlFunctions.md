<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [GIS Extensions for Dremio - SQL Function Reference](#gis-extensions-for-dremio---sql-function-reference)
  - [Author](#author)
  - [Legal Disclaimer](#legal-disclaimer)
  - [Third-Party Libraries](#third-party-libraries)
  - [(1) H3_AsText](#1-h3_astext)
    - [Definition](#definition)
    - [Syntax](#syntax)
    - [Return Type](#return-type)
    - [Examples](#examples)
    - [Definition](#definition-1)
    - [Syntax](#syntax-1)
    - [Return Type](#return-type-1)
    - [Examples](#examples-1)
    - [Definition](#definition-2)
    - [Syntax](#syntax-2)
    - [Return Type](#return-type-2)
    - [Examples](#examples-2)
    - [Definition](#definition-3)
    - [Syntax](#syntax-3)
    - [Return Type](#return-type-3)
    - [Examples](#examples-3)
    - [Definition](#definition-4)
    - [Syntax](#syntax-4)
    - [Return Type](#return-type-4)
    - [Examples](#examples-4)
    - [Definition](#definition-5)
    - [Syntax](#syntax-5)
    - [Return Type](#return-type-5)
    - [Examples](#examples-5)
    - [Definition](#definition-6)
    - [Syntax](#syntax-6)
    - [Return Type](#return-type-6)
    - [Examples](#examples-6)
    - [Definition](#definition-7)
    - [Syntax](#syntax-7)
    - [Return Type](#return-type-7)
    - [Examples](#examples-7)
    - [Definition](#definition-8)
    - [Syntax](#syntax-8)
    - [Return Type](#return-type-8)
    - [Examples](#examples-8)
    - [Definition](#definition-9)
    - [Syntax](#syntax-9)
    - [Return Type](#return-type-9)
    - [Examples](#examples-9)
    - [Definition](#definition-10)
    - [Syntax](#syntax-10)
    - [Return Type](#return-type-10)
    - [Examples](#examples-10)
    - [Definition](#definition-11)
    - [Syntax](#syntax-11)
    - [Return Type](#return-type-11)
    - [Examples](#examples-11)
    - [Definition](#definition-12)
    - [Syntax](#syntax-12)
    - [Return Type](#return-type-12)
    - [Examples](#examples-12)
    - [Definition](#definition-13)
    - [Syntax](#syntax-13)
    - [Return Type](#return-type-13)
    - [Examples](#examples-13)
    - [Definition](#definition-14)
    - [Syntax](#syntax-14)
    - [Return Type](#return-type-14)
    - [Examples](#examples-14)
    - [Definition](#definition-15)
    - [Syntax](#syntax-15)
    - [Return Type](#return-type-15)
    - [Examples](#examples-15)
    - [Definition](#definition-16)
    - [Syntax](#syntax-16)
    - [Return Type](#return-type-16)
    - [Examples](#examples-16)
    - [Definition](#definition-17)
    - [Syntax](#syntax-17)
    - [Return Type](#return-type-17)
    - [Examples](#examples-17)
    - [Definition](#definition-18)
    - [Syntax](#syntax-18)
    - [Return Type](#return-type-18)
    - [Examples](#examples-18)
    - [Definition](#definition-19)
    - [Syntax](#syntax-19)
    - [Return Type](#return-type-19)
    - [Examples](#examples-19)
    - [Definition](#definition-20)
    - [Syntax](#syntax-20)
    - [Return Type](#return-type-20)
    - [Examples](#examples-20)
    - [Definition](#definition-21)
    - [Syntax](#syntax-21)
    - [Return Type](#return-type-21)
    - [Examples](#examples-21)
    - [Definition](#definition-22)
    - [Syntax](#syntax-22)
    - [Return Type](#return-type-22)
    - [Examples](#examples-22)
    - [Definition](#definition-23)
    - [Syntax](#syntax-23)
    - [Return Type](#return-type-23)
    - [Examples](#examples-23)
    - [Definition](#definition-24)
    - [Syntax](#syntax-24)
    - [Return Type](#return-type-24)
    - [Examples](#examples-24)
    - [Definition](#definition-25)
    - [Syntax](#syntax-25)
    - [Return Type](#return-type-25)
    - [Examples](#examples-25)
    - [Definition](#definition-26)
    - [Syntax](#syntax-26)
    - [Return Type](#return-type-26)
    - [Examples](#examples-26)
    - [Definition](#definition-27)
    - [Syntax](#syntax-27)
    - [Return Type](#return-type-27)
    - [Examples](#examples-27)
    - [Definition](#definition-28)
    - [Syntax](#syntax-28)
    - [Return Type](#return-type-28)
    - [Examples](#examples-28)
    - [Definition](#definition-29)
    - [Syntax](#syntax-29)
    - [Return Type](#return-type-29)
    - [Examples](#examples-29)
    - [Definition](#definition-30)
    - [Syntax](#syntax-30)
    - [Return Type](#return-type-30)
    - [Examples](#examples-30)
    - [Definition](#definition-31)
    - [Syntax](#syntax-31)
    - [Return Type](#return-type-31)
    - [Examples](#examples-31)
    - [Definition](#definition-32)
    - [Syntax](#syntax-32)
    - [Return Type](#return-type-32)
    - [Examples](#examples-32)
    - [Definition](#definition-33)
    - [Syntax](#syntax-33)
    - [Return Type](#return-type-33)
    - [Examples](#examples-33)
    - [Definition](#definition-34)
    - [Syntax](#syntax-34)
    - [Return Type](#return-type-34)
    - [Examples](#examples-34)
    - [Definition](#definition-35)
    - [Syntax](#syntax-35)
    - [Return Type](#return-type-35)
    - [Examples](#examples-35)
    - [Definition](#definition-36)
    - [Syntax](#syntax-36)
    - [Return Type](#return-type-36)
    - [Examples](#examples-36)
    - [Definition](#definition-37)
    - [Syntax](#syntax-37)
    - [Return Type](#return-type-37)
    - [Examples](#examples-37)
    - [Definition](#definition-38)
    - [Syntax](#syntax-38)
    - [Return Type](#return-type-38)
    - [Examples](#examples-38)
    - [Definition](#definition-39)
    - [Syntax](#syntax-39)
    - [Return Type](#return-type-39)
    - [Examples](#examples-39)
    - [Definition](#definition-40)
    - [Syntax](#syntax-40)
    - [Return Type](#return-type-40)
    - [Examples](#examples-40)
    - [Definition](#definition-41)
    - [Syntax](#syntax-41)
    - [Return Type](#return-type-41)
    - [Examples](#examples-41)
    - [Definition](#definition-42)
    - [Syntax](#syntax-42)
    - [Return Type](#return-type-42)
    - [Examples](#examples-42)
    - [Definition](#definition-43)
    - [Syntax](#syntax-43)
    - [Return Type](#return-type-43)
    - [Examples](#examples-43)
    - [Definition](#definition-44)
    - [Syntax](#syntax-44)
    - [Return Type](#return-type-44)
    - [Examples](#examples-44)
    - [Definition](#definition-45)
    - [Syntax](#syntax-45)
    - [Return Type](#return-type-45)
    - [Examples](#examples-45)
    - [Definition](#definition-46)
    - [Syntax](#syntax-46)
    - [Return Type](#return-type-46)
    - [Examples](#examples-46)
    - [Definition](#definition-47)
    - [Syntax](#syntax-47)
    - [Return Type](#return-type-47)
    - [Examples](#examples-47)
    - [Definition](#definition-48)
    - [Syntax](#syntax-48)
    - [Return Type](#return-type-48)
    - [Examples](#examples-48)
    - [Definition](#definition-49)
    - [Syntax](#syntax-49)
    - [Return Type](#return-type-49)
    - [Examples](#examples-49)
    - [Definition](#definition-50)
    - [Syntax](#syntax-50)
    - [Return Type](#return-type-50)
    - [Examples](#examples-50)
    - [Definition](#definition-51)
    - [Syntax](#syntax-51)
    - [Return Type](#return-type-51)
    - [Examples](#examples-51)
    - [Definition](#definition-52)
    - [Syntax](#syntax-52)
    - [Return Type](#return-type-52)
  - [(54) ST_GeomFromText](#54-st_geomfromtext)
    - [Definition](#definition-53)
    - [Syntax](#syntax-53)
    - [Return Type](#return-type-53)
  - [(55) ST_GeomFromWKB](#55-st_geomfromwkb)
    - [Definition](#definition-54)
    - [Syntax](#syntax-54)
    - [Return Type](#return-type-54)
  - [(56) ST_GeomFromWKB](#56-st_geomfromwkb)
    - [Definition](#definition-55)
    - [Syntax](#syntax-55)
    - [Return Type](#return-type-55)
  - [(57) ST_GeoSize](#57-st_geosize)
    - [Definition](#definition-56)
    - [Syntax](#syntax-56)
    - [Return Type](#return-type-56)
  - [(58) ST_InteriorRingN](#58-st_interiorringn)
    - [Definition](#definition-57)
    - [Syntax](#syntax-57)
    - [Return Type](#return-type-57)
    - [Examples](#examples-52)
    - [Definition](#definition-58)
    - [Syntax](#syntax-58)
    - [Return Type](#return-type-58)
    - [Examples](#examples-53)
    - [Definition](#definition-59)
    - [Syntax](#syntax-59)
    - [Return Type](#return-type-59)
    - [Examples](#examples-54)
    - [Definition](#definition-60)
    - [Syntax](#syntax-60)
    - [Return Type](#return-type-60)
    - [Examples](#examples-55)
    - [Definition](#definition-61)
    - [Syntax](#syntax-61)
    - [Return Type](#return-type-61)
    - [Examples](#examples-56)
    - [Definition](#definition-62)
    - [Syntax](#syntax-62)
    - [Return Type](#return-type-62)
    - [Examples](#examples-57)
    - [Definition](#definition-63)
    - [Syntax](#syntax-63)
    - [Return Type](#return-type-63)
    - [Examples](#examples-58)
    - [Definition](#definition-64)
    - [Syntax](#syntax-64)
    - [Return Type](#return-type-64)
    - [Examples](#examples-59)
    - [Definition](#definition-65)
    - [Syntax](#syntax-65)
    - [Return Type](#return-type-65)
    - [Examples](#examples-60)
    - [Definition](#definition-66)
    - [Syntax](#syntax-66)
    - [Return Type](#return-type-66)
    - [Examples](#examples-61)
    - [Definition](#definition-67)
    - [Syntax](#syntax-67)
    - [Return Type](#return-type-67)
    - [Examples](#examples-62)
    - [Definition](#definition-68)
    - [Syntax](#syntax-68)
    - [Return Type](#return-type-68)
    - [Examples](#examples-63)
    - [Definition](#definition-69)
    - [Syntax](#syntax-69)
    - [Return Type](#return-type-69)
    - [Examples](#examples-64)
    - [Definition](#definition-70)
    - [Syntax](#syntax-70)
    - [Return Type](#return-type-70)
    - [Examples](#examples-65)
    - [Definition](#definition-71)
    - [Syntax](#syntax-71)
    - [Return Type](#return-type-71)
    - [Examples](#examples-66)
    - [Definition](#definition-72)
    - [Syntax](#syntax-72)
    - [Return Type](#return-type-72)
    - [Examples](#examples-67)
    - [Definition](#definition-73)
    - [Syntax](#syntax-73)
    - [Return Type](#return-type-73)
    - [Examples](#examples-68)
    - [Definition](#definition-74)
    - [Syntax](#syntax-74)
    - [Return Type](#return-type-74)
    - [Examples](#examples-69)
    - [Definition](#definition-75)
    - [Syntax](#syntax-75)
    - [Return Type](#return-type-75)
    - [Examples](#examples-70)
    - [Definition](#definition-76)
    - [Syntax](#syntax-76)
    - [Return Type](#return-type-76)
    - [Examples](#examples-71)
    - [Definition](#definition-77)
    - [Syntax](#syntax-77)
    - [Return Type](#return-type-77)
    - [Examples](#examples-72)
    - [Definition](#definition-78)
    - [Syntax](#syntax-78)
    - [Return Type](#return-type-78)
    - [Examples](#examples-73)
    - [Definition](#definition-79)
    - [Syntax](#syntax-79)
    - [Return Type](#return-type-79)
    - [Examples](#examples-74)
    - [Definition](#definition-80)
    - [Syntax](#syntax-80)
    - [Return Type](#return-type-80)
    - [Examples](#examples-75)
    - [Definition](#definition-81)
    - [Syntax](#syntax-81)
    - [Return Type](#return-type-81)
  - [(83) ST_PointN](#83-st_pointn)
    - [Definition](#definition-82)
    - [Syntax](#syntax-82)
    - [Return Type](#return-type-82)
    - [Examples](#examples-76)
    - [Definition](#definition-83)
    - [Syntax](#syntax-83)
    - [Return Type](#return-type-83)
  - [(85) ST_Relate](#85-st_relate)
    - [Definition](#definition-84)
    - [Syntax](#syntax-84)
    - [Return Type](#return-type-84)
    - [Examples](#examples-77)
    - [Definition](#definition-85)
    - [Syntax](#syntax-85)
    - [Return Type](#return-type-85)
  - [(87) ST_Simplify](#87-st_simplify)
    - [Definition](#definition-86)
    - [Syntax](#syntax-86)
    - [Return Type](#return-type-86)
  - [(88) ST_StartPoint](#88-st_startpoint)
    - [Definition](#definition-87)
    - [Syntax](#syntax-87)
    - [Return Type](#return-type-87)
    - [Examples](#examples-78)
    - [Definition](#definition-88)
    - [Syntax](#syntax-88)
    - [Return Type](#return-type-88)
    - [Examples](#examples-79)
    - [Definition](#definition-89)
    - [Syntax](#syntax-89)
    - [Return Type](#return-type-89)
    - [Examples](#examples-80)
    - [Definition](#definition-90)
    - [Syntax](#syntax-90)
    - [Return Type](#return-type-90)
    - [Examples](#examples-81)
    - [Definition](#definition-91)
    - [Syntax](#syntax-91)
    - [Return Type](#return-type-91)
    - [Examples](#examples-82)
    - [Definition](#definition-92)
    - [Syntax](#syntax-92)
    - [Return Type](#return-type-92)
    - [Examples](#examples-83)
    - [Definition](#definition-93)
    - [Syntax](#syntax-93)
    - [Return Type](#return-type-93)
    - [Examples](#examples-84)
    - [Definition](#definition-94)
    - [Syntax](#syntax-94)
    - [Return Type](#return-type-94)
    - [Examples](#examples-85)
    - [Definition](#definition-95)
    - [Syntax](#syntax-95)
    - [Return Type](#return-type-95)
    - [Examples](#examples-86)
    - [Definition](#definition-96)
    - [Syntax](#syntax-96)
    - [Return Type](#return-type-96)
    - [Examples](#examples-87)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# GIS Extensions for Dremio - SQL Function Reference
## Author

[Brian Holman](mailto:bholman@dezota.com)

## Legal Disclaimer

*This independent project is not affiliated with, sponsored, or endorsed by Dremio Corporation. Dremio is a registered trademark of Dremio Corporation and they retain all trademark and other intellectual property rights.  "Dremio" is used here by reference to integrating with their published [User-Defined Functions Specification](https://www.dremio.com/hub-additional/) for advanced users to develop their own custom functions for use in SQL queries.*

![DAC with GIS extensions](./dremio_dac_with_gis.jpg)

## Third-Party Libraries

The **GIS Extensions** allow Dremio to perform standard GIS functions within Dremio SQL with 72 industry-standard GIS functions. These extensions use the [*Esri Java Geometry Library*](https://github.com/Esri/geometry-api-java/wiki/) for the underlying implementation of the core geometry functions. The author made heavy use of Esri's [*Spatial Framework for Hadoop*](https://github.com/Esri/spatial-framework-for-hadoop) as a reference for a similar implementation that also relies on the same library. 

There were two significant gaps in the Geometry Library supplied by Esri that limited transforming geometries from `EPSG: 4326` to other coordinate systems and performing geodesic rather than 2D area and length calculations. Geodesic area function helpers backing the `ST_GeodesicAreaWGS84` function are copied almost exactly from the [*Trino Geospatial Library*](https://github.com/trinodb/trino/tree/master/plugin/trino-geospatial) as found in our `FunctionHelpers.stSphericalArea()` and `FunctionHelpers.computeSphericalExcess()`. Conversion to other coordinate systems in the `ST_Transform` function leverages the [Proj4J Library](https://trac.osgeo.org/proj4j/). All of the referenced works are also published under the *Apache 2.0 License*.


## (1) H3_AsText

### Definition
Returns a Hex representation of the H3 value as a string.

### Syntax
<code>H3_AsText(bigint <em>h3Value</em>)</code>

### Return Type
`string`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_AsText(H3_FromGeomPoint(ST_Point(40.4168, -3.7038), 4))` | `'847b59dffffffff'` |
## (2) H3_Boundary

### Definition
Returns a polygon geography representing the H3 cell.

### Syntax
<code>H3_Boundary(bigint <em>h3Value</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(H3_Boundary(H3_FromLongLat(40.4168, -3.7038, 15)))` | `'POLYGON ((-3.703802360352346 40.41680913267208, -3.7038075007518416 40.41680558484906, -3.703806130063667 40.41680018598506, -3.7037996189769617 40.41679833494421, -3.7037944785779335 40.41680188276699, -3.7037958492651386 40.416807281630845, -3.703802360352346 40.41680913267208))'` |
## (3) H3_Center

### Definition
Returns the center of the H3 cell as a point.  It will throw an error if the *h3Value* is not valid as an H3 Value.

### Syntax
<code>H3_Center(bigint <em>h3Value</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(H3_Center(H3_FromText('847b59dffffffff')))` | `'POINT (40.305476423174326 -3.743203325561687)'` |
## (4) H3_Compact

### Definition
Returns an array with the indexes of a set of hexagons across multiple resolutions that represent the same area as the input set of hexagons.

### Syntax
<code>H3_Compact(bigint <em>h3Value</em>)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Compact(H3_Uncompact(H3_Wrap(H3_FromText('847b59dffffffff')),5))` | `[596645165859340300]` |
## (5) H3_Distance

### Definition
Returns the grid distance between two hexagon indexes. This function may fail to find the distance between two indexes if they are very far apart or on opposite sides of a pentagon.

### Syntax
<code>H3_Distance(bigint <em>h3Value1</em>, bigint <em>h3Value2</em>)</code>

### Return Type
`numeric`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Distance(H3_FromText('847b591ffffffff'), H3_FromText('847b59bffffffff'))` | `1` |
## (6) H3_FromGeomPoint

### Definition
Returns the H3 cell index that the point belongs to in the required *resolution*. It will return `null` for non-point geometry and throw an error for resolution outside the valid range [0,15].

### Syntax
<code>H3_FromGeomPoint(binary <em>pointGeom</em>, number <em>resolution</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_AsText(H3_FromGeomPoint(ST_Point(40.4168, -3.7038), 4))` | `'847b59dffffffff'` |
## (7) H3_FromGeomPoly

### Definition
Returns the smallest H3 cell that completely encloses the polygon with a dynamic resolution. It will return `null` if there isn't a value to fit the polygon and throw an error if a non-polygon geometry is supplied.

### Syntax
<code>H3_FromGeomPoly(binary <em>polyGeom</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_FromGeomPoly(ST_GeomFromText('POLYGON ((-111.69808887205492 40.238737891364416, -111.69837535651395 40.23873740478143, -111.6983745544178 40.2384602506758, -111.69808807598443 40.23846160818078, -111.69808887205492 40.238737891364416))',true))` | `622175171754917887` |
## (8) H3_FromLongLat

### Definition
Returns the H3 cell index specified by *lon* and *lat* at the specified *resolution*.  It will throw an error for resolution outside the valid range [0,15].

### Syntax
<code>H3_FromLongLat(number <em>lon</em>, number <em>lat</em>, number <em>resolution</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_AsText(H3_FromLongLat(40.4168, -3.7038, 4))` | `'84390cbffffffff'` |
## (9) H3_FromText

### Definition
Converts from String representation of H3 cell value to the bigint representation. It will throw an error if the hex representation is not valid as an H3 Value.

### Syntax
<code>H3_FromText(string <em>h3Text</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(H3_Center(H3_FromText('847b59dffffffff')))` | `'POINT (40.305476423174326 -3.743203325561687)'` |
## (10) H3_HexRing

### Definition
Returns all cell indexes in a hollow hexagonal ring centered at the origin in no particular order. Unlike H3_Kring, this function will throw an exception if there is a pentagon anywhere in the ring.

### Syntax
<code>H3_HexRing(bigint h3Origin, int ringSize)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Hexring(H3_FromText('837b59fffffffff'), 1)` | `[592141849699811300,592141506102427600,592141712260857900,592124875989057500,592124738550104000,592140544029753300]` |
## (11) H3_IsPentagon

### Definition
Returns *true* if given H3 index is a pentagon. Returns *false* otherwise, even on invalid input.

### Syntax
<code>H3_IsPentagon(bigint h3Value)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_IsPentagon(H3_FromText('837b59fffffffff'))` | `false` |
| `SELECT H3_IsPentagon(H3_FromText('8075fffffffffff'))` | `true` |
## (12) H3_IsValid

### Definition
Returns *true* when the given index is valid, *false* otherwise.

### Syntax
<code>H3_IsValid(bigint h3Value)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_IsValid(8675309)` | `false` |
| `SELECT H3_IsValid(H3_FromText('837b59fffffffff'))` | `true` |
## (13) H3_KRing

### Definition
Returns all cell indexes in a filled hexagonal k-ring centered at the origin in no particular order.

### Syntax
<code>H3_KRing(bigint h3Origin, int ringSize)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_KRing(H3_FromText('837b59fffffffff'), 1)` | `[592141574821904400,592141506102427600,592141712260857900,592124875989057500,592124738550104000,592140544029753300,592141849699811300]` |
## (14) H3_KRing_Distances

### Definition
Returns all cell indexes and their distances in a filled hexagonal k-ring centered at the origin in no particular order.

### Syntax
<code>H3_KRing_Distances(bigint h3Origin, int ringSize)</code>

### Return Type
`struct{index, distance}`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_KRingDistances(H3_FromText('837b59fffffffff'), 1)` | `[{"index":592141574821904383,"distance":0},{"index":592141506102427647,"distance":1},{"index":592141712260857855,"distance":1},{"index":592124875989057535,"distance":1},{"index":592124738550104063,"distance":1},{"index":592140544029753343,"distance":1},{"index":592141849699811327,"distance":1}]` |
## (15) H3_Polyfill

### Definition
Returns an array with all the H3 cell indexes for the given polygon or multipolygon including automatically handling the inner holes.

### Syntax
<code>H3_Polyfillbinary <em>geometry</em>, number <em>resolution</em></code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Polyfill(ST_GeomFromText('POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))',false), 1)` | `[582059465512058900,582072659651592200,582068261605081100,582081455744614400,582855511930568700,582063863558570000,582077057698103300,582851113884057600]` |
## (16) H3_Resolution

### Definition
Returns the H3 cell resolution as an integer. It will throw an error if the *h3Value* is not valid as an H3 Value.

### Syntax
<code>H3_Resolution(bigint <em>h3Value</em>)</code>

### Return Type
`integer`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Resolution(H3_FromText('847b59dffffffff'))` | `4` |
## (17) H3_ToCenterChild

### Definition
Returns the center child (finer) index for the given hexagon at the given resolution.

### Syntax
<code>H3_ToCenterChild(bigint <em>h3Value</em>, number <em>childResolution</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_AsText(H3_ToCenterChild(H3_FromText('837b59fffffffff'), 4))` | `'847b591ffffffff'` |
## (18) H3_ToChildren

### Definition
Returns an array with the indexes of the children/descendents of the given hexagon at the given resolution.

### Syntax
<code>H3_ToChildren(bigint <em>h3Value</em>, number <em>childResolution</em>)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `WITH H3Children AS (SELECT FLATTEN(H3_ToChildren(H3_FromText('837b59fffffffff'), 4)) AS H3Values) SELECT H3_AsText(H3Values) FROM H3Children` | `'847b591ffffffff' '847b593ffffffff' '847b595ffffffff' '847b597ffffffff' '847b599ffffffff' '847b59bffffffff' '847b59dffffffff'` |
## (19) H3_ToParent

### Definition
Returns the H3 cell index of the parent of the given hexagon at the given resolution.

### Syntax
<code>H3_ToParent(bigint <em>h3Value</em>, number <em>resolution</em>)</code>

### Return Type
`bigint`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_AsText(H3_ToParent(H3_FromText('847b59dffffffff'), 3))` | `'837b59fffffffff'` |
## (20) H3_Uncompact

### Definition
Returns an array with the indexes of a set of hexagons of the same *resolution* that represent the same area as the compacted input hexagons.

### Syntax
<code>H3_Uncompact(bigint <em>h3Value</em>, number <em>resolution</em>)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Uncompact(H3_Wrap(H3_FromText('847b59dffffffff')),5)` | `[601148757970518000,601148759044259800,601148760118001700,601148761191743500,601148762265485300,601148763339227100,601148764412969000]` |
## (21) H3_Wrap

### Definition
Takes a single H3 value and wraps it in a list

### Syntax
<code>H3_Wrap(bigint <em>h3Value</em>)</code>

### Return Type
`bigint[]`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT H3_Wrap(H3_FromText('847b59dffffffff'))[0]` | `596645165859340287` |
## (22) ST_AggrConvexHull

### Definition
Creates a single geometry that is a convex hull of a geometry that resulted from a union of all aggregate input geometries.

### Syntax
<code>ST_AggrConvexHull(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `WITH GEOMLIST AS (SELECT ST_GeomFromText('polygon ((40 40, 40 60, 60 60, 60 40, 40 40))', true) AS GEOM1, ST_GeomFromText('polygon ((20 30, 30 30, 30 40, 20 40, 20 30))', true) AS GEOM2) SELECT ST_AsText(ST_AggrConvexHull(GEOM)) FROM GEOMLIST UNPIVOT ("GEOM" for "COL" in  (GEOM1, GEOM2))` | `'POLYGON ((20 30, 30 30, 60 40, 60 60, 40 60, 20 40, 20 30))'` |
## (23) ST_AggrIntersection

### Definition
Returns a single geometry that is an intersection of all aggregate input geometries.

### Syntax
<code>ST_AggrIntersection(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `WITH GEOMLIST AS (SELECT ST_GeomFromText('polygon ((5 5, 12 5, 12 10, 5 10, 5 5))', true) AS GEOM1, ST_GeomFromText('polygon ((10 8, 14 8, 14 15, 10 15, 10 8))', true) AS GEOM2, ST_GeomFromText('polygon ((6 8, 20 8, 20 20, 6 20, 6 8))', true) AS GEOM3) SELECT ST_AsText(ST_AggrIntersection(GEOM)) FROM GEOMLIST UNPIVOT ("GEOM" for "COL" in  (GEOM1, GEOM2, GEOM3))` | `'POLYGON ((10 8, 12 8, 12 10, 10 10, 10 8))'` |
## (24) ST_AggrUnion

### Definition
Returns a single geometry that is the union of all aggregate input geometries.

### Syntax
<code>ST_AggrUnion(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `WITH GEOMLIST AS (SELECT ST_GeomFromText('polygon ((20 30, 30 30, 30 40, 20 40, 20 30))', true) AS GEOM1, ST_GeomFromText('polygon ((40 40, 40 60, 60 60, 60 40, 40 40))', true) AS GEOM2) SELECT ST_AsText(ST_AggrUnion(GEOM)) FROM GEOMLIST UNPIVOT ("GEOM" for "COL" in  (GEOM1, GEOM2))` | `'MULTIPOLYGON (((20 30, 30 30, 30 40, 20 40, 20 30)), ((40 40, 60 40, 60 60, 40 60, 40 40)))'` |
## (25) ST_Area

### Definition
Returns the area of polygon or multipolygon

### Syntax
<code>ST_Area(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Area(ST_GeomFromText('POLYGON ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true)) ` | `24.0` |
## (26) ST_AsGeoJSON

### Definition
Returns the GeoJSON representation of *geometry*.

### Syntax
<code>ST_AsGeoJSON(binary <em>geometry</em>)</code>

### Return Type
`string`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsGeoJSON(ST_Point(1, 2))` | `'{"type":"Point","coordinates":[1,2],"crs":{"type":"name","properties":{"name":"EPSG:4326"}}}'` |
## (27) ST_AsText

### Definition
Returns the Well-Known Text (WKT) representation of *geometry*.

### Syntax
<code>ST_AsText(binary <em>geometry</em>)</code>

### Return Type
`string`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Point(1, 2))` | `'POINT (1 2)'` |
## (28) ST_Boundary

### Definition
Returns the closure of the combinatorial boundary of this Geometry.

### Syntax
<code>ST_Boundary(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Boundary(ST_GeomFromText('LINESTRING (0 1, 1 0)',true)))` | `'MULTIPOINT ((0 1), (1 0))'` |
| `SELECT ST_AsText(ST_Boundary(ST_GeomFromText('POLYGON ((1 1, 4 1, 1 4))',true)))` | `'MULTILINESTRING ((1 1, 4 1, 1 4, 1 1))'` |
## (29) ST_Buffer

### Definition
Returns geometry object that is the buffer surrounding source *geometry* at specified *distance*.

### Syntax
<code>ST_Buffer(binary <em>geometry</em>, number <em>distance</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Buffer(ST_Point(0, 0), 1)` | `polygon approximating a unit circle` |
## (30) ST_Centroid

### Definition
Takes a polygon, multipolygon, or multilinestring and returns the point that is in the center of the geometry's envelope. That means that the centroid point is halfway between the geometry's minimum and maximum x and y extents.

### Syntax
<code>ST_Centroid(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Centroid(ST_GeomFromText('point (2 3)',true)))` | `'POINT(2 3)'` |
| `SELECT ST_AsText(ST_Centroid(ST_GeomFromText('MULTIPOINT ((0 0), (1 1), (1 -1), (6 0))',true)))` | `'POINT(2 0)'` |
| `SELECT ST_AsText(ST_Centroid(ST_GeomFromText('linestring (0 0, 6 0)',true)))` | `'POINT(3 0)'` |
| `SELECT ST_AsText(ST_Centroid(ST_GeomFromText('POLYGON ((0 0, 0 8, 8 8, 8 0, 0 0))',true)))` | `'POINT(4 4)'` |
| `SELECT ST_AsText(ST_Centroid(ST_GeomFromText('POLYGON ((1 1, 5 1, 3 4))',true)))` | `'POINT(3 2)'` |
## (31) ST_Contains

### Definition
Returns true if *geometry1* contains *geometry2*.

### Syntax
<code>ST_Contains(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Contains(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), ST_Point(2, 3))` | `true ` |
| `SELECT ST_Contains(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), ST_Point(8, 8))` | `false` |
## (32) ST_ConvexHull

### Definition
Computes the convex hull of *geometry*. The convex hull is the smallest convex geometry that encloses all geometries in the input. One can think of the convex hull as the geometry obtained by wrapping an rubber band around a set of geometries.

### Syntax
<code>ST_ConvexHull(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_ConvexHull(ST_GeomFromText('polygon ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true)))` | `'POLYGON ((0 0, 8 0, 0 8, 0 0))'` |
## (33) ST_CoordDim

### Definition
Returns count of coordinate components.

### Syntax
<code>ST_CoordDim(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_CoordDim(ST_Point(1.5, 2.5))` | `2` |
| `SELECT ST_CoordDim(ST_GeomFromText('POINTZ (1.5 2.5 3)',true))` | `3` |
## (34) ST_Crosses

### Definition
Returns true if *geometry1* crosses *geometry2*, otherwise false.

### Syntax
<code>ST_Crosses(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Crosses(ST_GeomFromText('LINESTRING (0 0, 1 1)',true), ST_GeomFromText('LINESTRING (1 0, 0 1))',true))` | `true` |
| `SELECT ST_Crosses(ST_GeomFromText('LINESTRING (2 0, 2 3)',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `true` |
| `SELECT ST_Crosses(ST_GeomFromText('LINESTRING (0 2, 0 1)',true), ST_GeomFromText('LINESTRING (2 0, 1 0)',true))` | `false` |
## (35) ST_Densify

### Definition
Densifies a MultiPath (polygons and polylines) *geometry* by *maxLength* so that no segments are longer than given threshold value.

### Syntax
<code>ST_Densify(binary <em>geometry</em>, number <em>maxLength</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Densify(ST_GeomFromText('POLYGON ((0 0, 8 0, 0 8, 0 0))',true),4))` | `'POLYGON ((0 0, 4 0, 8 0, 5.333 2.667, 2.667 5.333, 0 8, 0 4, 0 0))'` |
## (36) ST_Difference

### Definition
Returns a geometry object that is the difference of the source objects.

### Syntax
<code>ST_Difference(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Difference(ST_GeomFromText('MULTIPOINT (1 1, 1.5 1.5, 2 2)',true), ST_Point(1.5, 1.5)))` | `'MULTIPOINT ((1 1), (2 2))'` |
| `SELECT ST_AsText(ST_Difference(ST_GeomFromText('POLYGON ((0 0, 0 10, 10 10, 10 0))',true), ST_GeomFromText('POLYGON ((0 0, 0 5, 5 5, 5 0))',true)))` | `'POLYGON ((5 0, 10 0, 10 10, 0 10, 0 5, 5 5, 5 0))'` |
## (37) ST_Dimension

### Definition
Returns spatial dimension of geometry.

### Syntax
<code>ST_Dimension(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Dimension(ST_Point(1.5, 2.5))` | `0` |
| `SELECT ST_Dimension(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true))` | `1` |
| `SELECT ST_Dimension(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true))` | `2` |
## (38) ST_Disjoint

### Definition
Returns true if the intersection of the two geometries produces an empty set; otherwise, it returns false.

### Syntax
<code>ST_Disjoint(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Disjoint(ST_GeomFromText('LINESTRING (0 0, 0 1)',true), ST_GeomFromText('LINESTRING (1 1, 1 0)',true))` | `true` |
| `SELECT ST_Disjoint(ST_GeomFromText('LINESTRING (0 0, 1 1)',true), ST_GeomFromText('LINESTRING (1 0, 0 1)',true))` | `false` |
## (39) ST_Distance

### Definition
Returns the distance between two geometry objects.

### Syntax
<code>ST_Distance(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Distance(ST_Point(0.0,0.0), ST_Point(3.0,4.0))` | `5.0` |
## (40) ST_DWithin

### Definition
Returns true if the two geometries are within the specified distance of one another; otherwise, it returns false.

### Syntax
<code>ST_DWithin(binary <em>geometry1</em>, binary <em>geometry2</em>, number <em>distance</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_DWithin(ST_GeomFromText('POLYGON ((10.02 20.01, 11.92 35.64, 25.02 34.15, 19.15 33.94, 10.02 20.01))',true), ST_Point (1,2),100)` | `true` |
| `SELECT ST_DWithin(ST_GeomFromText('POLYGON ((101.02 200.01, 111.92 350.64, 250.02 340.15, 190.15 330.94, 101.02 200.01))',true), ST_Point (10.02,20.01), 100)` | `false` |
## (41) ST_EndPoint

### Definition
Returns the last point of a Linestring.

### Syntax
<code>ST_EndPoint(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_EndPoint(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true)))` | `'POINT(3.0 2.2)'` |
## (42) ST_Envelope

### Definition
Returns the minimum bounding box of the geometry object as a polygon

### Syntax
<code>ST_Envelope(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Envelope(ST_GeomFromText('LINESTRING (0 0, 2 2))',true)))` | `'POLYGON ((0 0, 2 0, 2 2, 0 2, 0 0))'` |
| `SELECT ST_AsText(ST_Envelope(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true)))` | `'POLYGON ((2 0, 3 0, 3 3, 2 3, 2 0))'` |
## (43) ST_EnvIntersects

### Definition
Returns true if the envelopes of *geometry1* and *geometry2* intersect, otherwise returns false.

### Syntax
<code>ST_EnvIntersects(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_EnvIntersects(ST_GeomFromText('LINESTRING (0 0, 1 1)',true), ST_GeomFromText('LINESTRING (1 3, 2 2)',true))` | `false` |
| `SELECT ST_EnvIntersects(ST_GeomFromText('LINESTRING (0 0, 2 2)',true), ST_GeomFromText('LINESTRING (1 0, 3 2)',true))` | `true` |
## (44) ST_Equals

### Definition
Returns true if the two geometries occupy the same space even if they have a different number of vertices, otherwise it returns false.

### Syntax
<code>ST_Equals(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Equals(ST_GeomFromText('LINESTRING (0 0, 1 1)',true),ST_GeomFromText('LINESTRING (1 1, 0 0)',true))` | `true` |
| `SELECT ST_Equals(ST_GeomFromText('LINESTRING (0 0, 1 1)',true),ST_GeomFromText('LINESTRING (1 0, 0 1)',true))` | `false` |
| `SELECT ST_Equals(ST_GeomFromText('LINESTRING (0 0, 3 3)',true),ST_GeomFromText('LINESTRING (3 3, 2 2, 1 1, 0 0)',true))` | `true` |
## (45) ST_ExteriorRing

### Definition
Returns the exterior ring of a polygon as a linestring.

### Syntax
<code>ST_ExteriorRing(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_ExteriorRing(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 1))',true)))` | `'LINESTRING (1 1, 4 1, 1 4, 1 1)'` |
| `SELECT ST_AsText(ST_ExteriorRing(ST_GeomFromText('POLYGON ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true)))` | `'LINESTRING (0 0, 8 0, 0 8, 0 0)'` |
## (46) ST_Generalize

### Definition
Simplifies geometries using the Douglas-Peucker algorithm. *maxDeviation* is the maximum allowed deviation from the generalized geometry to the original geometry.  When *removeDegenerateParts* is true, the degenerate parts of the geometry will be removed from the output.

### Syntax
<code>ST_Generalize(binary <em>geometry</em>, number <em>maxDeviation</em>, boolean <em>removeDegenerateParts</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Generalize(ST_GeomFromText('POLYGON ((0 0, 1 1, 2 0, 3 2, 4 1, 5 0, 5 10, 0 10))',true), 2, true))` | `'POLYGON ((0 0, 5 0, 5 10, 0 10, 0 0))'` |
## (47) ST_GeodesicAreaWGS84

### Definition
Returns the area in square meters of a geometry on the Earth's surface using spherical model. Requires the geometry to be in the WGS84 spatial reference.

### Syntax
<code>ST_GeodesicAreaWGS84(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_GeodesicAreaWGS84(ST_GeomFromText('POLYGON ((-114.04702599994988 39.90609700007656, -114.0500520000997 37.0001909997149, -109.04517199998776 36.99897700038832, -109.05002599989996 41.000691000389395, -111.04681499981234 40.997875000031286, -111.04671399965133 42.00170200004732, -114.04147700036322 41.99387299963928, -114.04702599994988 39.90609700007656))',true))/4047 AS utah_acreage` | `5.416484897473004E7` |
## (48) ST_GeodesicLengthWGS84

### Definition
Returns distance along line on WGS84 spheroid, in meters, for geographic coordinates. Requires the geometry to be in the WGS84 spatial reference.

### Syntax
<code>ST_GeodesicLengthWGS84(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_GeodesicLengthWGS84(ST_GeomFromText('MultiLineString((0.0 80.0, 0.3 80.4))', true, 4326))` | `45026.96274781222` |
## (49) ST_GeometryN

### Definition
Takes a geometry collection and an integer index (1-based index) and returns the nth geometry object in the collection.

### Syntax
<code>ST_GeometryN(binary <em>geometry</em>, number <em>index</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_GeometryN(ST_GeomFromText('MULTIPOINT (10 40, 40 30, 20 20, 30 10)',true), 3))` | `'POINT (20 20)'` |
| `SELECT ST_AsText(ST_GeometryN(ST_GeomFromText('MULTILINESTRING ((2 4, 10 10), (20 20, 7 8))',true), 2))` | `'LINESTRING (20 20, 7 8)'` |
## (50) ST_GeometryType

### Definition
Takes a geometry object and returns its geometry type (for example, Point, Line, Polygon, MultiPoint) as a string.

### Syntax
<code>ST_GeometryType(binary <em>geometry</em>)</code>

### Return Type
`string`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_GeometryType(ST_Point(1.5, 2.5))` | `'ST_POINT'` |
| `SELECT ST_GeometryType(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true))` | `'ST_LINESTRING'` |
| `SELECT ST_GeometryType(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true))` | `'ST_POLYGON'` |
## (51) ST_GeomFromEWKB

### Definition
Converts a Hex encoded binary string from Postgres/PostGIS geometry to native geometry including embedded SRID.

### Syntax
<code>ST_GeomFromEWKB(string <em>hexEncodedGeometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_GeomFromEWKB(the_geom)) FROM table("postgis".external_query('SELECT ST_GeomFromText(''POINT(-71.064544 42.28787)'',4326) AS the_geom'))` | `'POINT (-71.064544 42.28787)'` |
## (52) ST_GeomFromGeoJSON

### Definition
Constructs a geometry from GeoJSON.

### Syntax
<code>ST_GeomFromGeoJSON(string <em>geoJsonString</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_GeomFromGeoJSON('{"type":"Point", "coordinates":[1.2, 2.4]}'))` | `'POINT (1.2 2.4)'` |
| `SELECT ST_AsText(ST_GeomFromGeoJSON('{"type":"LineString", "coordinates":[[1,2], [3,4]]}'))` | `'LINESTRING (1 2, 3 4)'` |
## (53) ST_GeomFromText

### Definition
Takes a well-known text representation and returns a geometry object. Set *ignoreErrors* to *true* to ignore bad data or *false* to fail and show the bad WKT value.

### Syntax
<code>ST_GeomFromText(string <em>wktString</em>, boolean <em>ignoreErrors</em>)</code>

### Return Type
`binary`

-------------

## (54) ST_GeomFromText

### Definition
Takes a well-known text representation and a spatial reference ID and returns a geometry object. Set *ignoreErrors* to *true* to ignore bad data or *false* to fail and show the bad WKT value.

### Syntax
<code>ST_GeomFromText(string <em>wktString</em>, boolean <em>ignoreErrors</em>, number <em>SRID</em>)</code>

### Return Type
`binary`

-------------

## (55) ST_GeomFromWKB

### Definition
Takes a well-known binary (WKB) representation and returns a geometry object.

### Syntax
<code>ST_GeomFromWKB(binary <em>wkbValue</em>)</code>

### Return Type
`binary`

-------------

## (56) ST_GeomFromWKB

### Definition
Takes a well-known binary (WKB) representation and a spatial reference ID and returns a geometry object.

### Syntax
<code>ST_GeomFromWKB(binary <em>wkbValue</em>, number <em>SRID</em>)</code>

### Return Type
`binary`

-------------

## (57) ST_GeoSize

### Definition
Takes a geometry object and returns its size in bytes.

### Syntax
<code>ST_GeoSize(binary <em>geometry</em>)</code>

### Return Type
`number`

-------------

## (58) ST_InteriorRingN

### Definition
Returns a LineString which is the nth interior ring of the input Polygon (1-based index)

### Syntax
<code>ST_InteriorRingN(binary <em>geometry</em>, number <em>index</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_InteriorRingN(ST_GeomFromText('polygon ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true), 1))` | `'LINESTRING (1 1, 1 5, 5 1, 1 1)'` |
## (59) ST_Intersection

### Definition
Returns a geometry object that is the geometric intersection of the source objects.

### Syntax
<code>ST_Intersection(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Intersection(ST_Point(1,1), ST_Point(1,1)))` | `'POINT (1 1)'` |
| `SELECT ST_AsText(ST_Intersection(ST_GeomFromText('LINESTRING(0 2, 0 0, 2 0)',true), ST_GeomFromText('LINESTRING(0 3, 0 1, 1 0, 3 0)',true)))` | `'MULTILINESTRING ((1 0, 2 0), (0 2, 0 1))'` |
| `SELECT ST_AsText(ST_Intersection(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true), ST_GeomFromText('POLYGON ((1 1, 4 1, 4 4, 1 4))',true)))` | `'POLYGON ((2 1, 2.666666666666667 1, 2 3, 2 1))'` |
## (60) ST_Intersects

### Definition
Returns true if *geometry1* intersects with *geometry2*, otherwise returns false.

### Syntax
<code>ST_Intersects(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Intersects(ST_GeomFromText('LINESTRING (2 0, 2 3)',true), ST_GeomFromText('POLYGON ((1 1, 4 1, 4 4, 1 4))',true))` | `true` |
| `SELECT ST_Intersects(ST_GeomFromText('LINESTRING (8 7, 7 8)',true), ST_GeomFromText('POLYGON ((1 1, 4 1, 4 4, 1 4))',true))` | `false` |
## (61) ST_Is3D

### Definition
Returns true if the geometry object is three-dimensional including height 'Z', otherwise returns false.

### Syntax
<code>ST_Is3D(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Is3D(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `false` |
| `SELECT ST_Is3D(ST_GeomFromText('LINESTRING (0 0, 3 4, 0 4, 0 0)',true))` | `false` |
| `SELECT ST_Is3D(ST_Point(3, 4))` | `false` |
| `SELECT ST_Is3D(ST_PointZ(3, 4, 2))` | `true` |
## (62) ST_IsClosed

### Definition
Return true if the linestring or multi-line has start and end points that are coincident.

### Syntax
<code>ST_IsClosed(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_IsClosed(ST_GeomFromText('LINESTRING(0 0, 3 4, 0 4, 0 0)',true))` | `true` |
| `SELECT ST_IsClosed(ST_GeomFromText('LINESTRING(0 0, 3 4)',true))` | `false` |
## (63) ST_IsEmpty

### Definition
Return true if the geometry object is empty of geometric information.

### Syntax
<code>ST_IsEmpty(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_IsEmpty(ST_Point(1.5, 2.5))` | `false` |
| `SELECT ST_IsEmpty(ST_GeomFromText('POINT EMPTY',true))` | `true` |
## (64) ST_IsMeasured

### Definition
Returns true if the geometry object is measured including an additional dimension 'M', otherwise returns false.

### Syntax
<code>ST_IsMeasured(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_IsMeasured(ST_PointZ(3, 4, 2))` | `false` |
| `SELECT ST_IsMeasured(ST_GeomFromText('POINT M (1 1 80)',true))` | `true` |
| `SELECT ST_IsMeasured(ST_GeomFromText('POINT ZM (1 1 5 60)',true))` | `true` |
## (65) ST_IsRing

### Definition
Returns true if the geometry is a linestring and the linestring is closed and simple.

### Syntax
<code>ST_IsRing(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 3 4, 0 4, 0 0)',true))` | `true` |
| `SELECT ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 1 1, 1 2, 2 1, 1 1, 0 0)',true))` | `false` |
| `SELECT ST_IsRing(ST_GeomFromText('LINESTRING (0 0, 3 4)',true))` | `false` |
## (66) ST_IsSimple

### Definition
Returns true if the geometry object is simple as defined by the Open Geospatial Consortium (OGC), otherwise, it returns false

### Syntax
<code>ST_IsSimple(binary <em>geometry</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_IsSimple(ST_Point(1.5, 2.5))` | `true` |
| `SELECT ST_IsSimple(ST_GeomFromText('LINESTRING (0 0, 1 1, 0 1, 1 0)',true))` | `false` |
## (67) ST_JSONPath

### Definition
Extract a portion of *jsonData* as a string by following the specified path in the JSON Object from *jsonPath*.
**Example JSON Path Syntax (similar to XPath for XML):**
'/data[Array]'
'/data[Array][1]/id[String]'
'/data[Array][1]/likes[Object]'
'/data[Array][1]/likes[Object]/summary[Object]/total_count[String]'
'/data[Array][3]'
'/data[Array][id=131272076894593_1420960724592382]/likes[Object]/summary[Object]/total_count'
'/fbids[String]'
'/quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/enterpriseValue[Object]/fmt[String]'
'/quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/forwardPE[Object]/raw[Double]'
'quoteSummary[6]/result[4][0]/defaultKeyStatistics[6]/sharesOutstanding[6]/raw[1]'
'quoteSummary[6]/result[Array]'
'quoteSummary[6]/result[Array][0]'
'quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/lastSplitDate[Object]/raw1[Long]'
'quoteSummary[Object]/result[Array][0]/defaultKeyStatistics[Object]/sharesOutstanding[Object]/raw[Integer]'

### Syntax
<code>ST_JSONPath(string jsonPath, string jsonData)</code>

### Return Type
`string`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_JSONPath('/coordinates[Array][0]',ST_AsGeoJSON(ST_Envelope(ST_GeomFromText('POLYGON ((-114.04702599994988 39.90609700007656, -114.0500520000997 37.0001909997149, -109.04517199998776 36.99897700038832, -109.05002599989996 41.000691000389395, -111.04681499981234 40.997875000031286, -111.04671399965133 42.00170200004732, -114.04147700036322 41.99387299963928, -114.04702599994988 39.90609700007656))',true))))` | `'[[-114.0500520000997,36.99897700038832],[-109.04517199998776,36.99897700038832],[-109.04517199998776,42.00170200004732],[-114.0500520000997,42.00170200004732],[-114.0500520000997,36.99897700038832]]'` |
## (68) ST_Length

### Definition
Returns the length of a line string or multiline string.

### Syntax
<code>ST_Length(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Length(ST_GeomFromText('LINESTRING (0 0, 3 4)',true))` | `5.0` |
| `SELECT ST_Length(ST_GeomFromText('MULTILINESTRING ((1 0, 2 0), (0 2, 0 1))',true))` | `2.0` |
## (69) ST_M

### Definition
Takes a Point as an input parameter and returns its measure m-coordinate.

### Syntax
<code>ST_M(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_M(ST_GeomFromText('POINT M (1 1 80)',true))` | `80.0` |
| `SELECT ST_M(ST_GeomFromText('POINT ZM (1 1 5 60)',true))` | `60.0` |
## (70) ST_MaxM

### Definition
Takes a geometry as an input parameter and returns its maximum measure m-coordinate.

### Syntax
<code>ST_MaxM(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MaxM(ST_GeomFromText('LINESTRING M (1.5 2.5 2, 3.0 2.2 1)',true))` | `2.0` |
| `SELECT ST_MaxM(ST_GeomFromText('POINT M (1.5 2.5 3)',true))` | `3.0` |
## (71) ST_MaxX

### Definition
Takes a geometry as an input parameter and returns its maximum x-coordinate.

### Syntax
<code>ST_MaxX(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MaxX(ST_GeomFromText('LINESTRING M (1.5 2.5 2, 3.0 2.2 1)',true))` | `3.0` |
| `SELECT ST_MaxX(ST_GeomFromText('POINT M (1.5 2.5 3)',true))` | `1.5` |
## (72) ST_MaxY

### Definition
Takes a geometry as an input parameter and returns its maximum y-coordinate.

### Syntax
<code>ST_MaxY(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MaxY(ST_GeomFromText('LINESTRING M (1.5 2.5 2, 3.0 2.2 1)',true))` | `2.5` |
| `SELECT ST_MaxY(ST_GeomFromText('POINT M (1.5 2.5 3)',true))` | `2.5` |
## (73) ST_MaxZ

### Definition
Takes a geometry as an input parameter and returns its maximum z-coordinate.

### Syntax
<code>ST_MaxZ(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MaxZ(ST_GeomFromText('LINESTRING ZM (1.5 2.5 2 60, 3.0 2.2 1 80)',true))` | `2.0` |
| `SELECT ST_MaxZ(ST_GeomFromText('LINESTRING Z (1.5 2.5 3, 3.0 2.2 4)',true))` | `4.0` |
## (74) ST_MinM

### Definition
Takes a geometry as an input parameter and returns its minimum m-coordinate.

### Syntax
<code>ST_MinM(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MinM(ST_GeomFromText('LINESTRING M (1.5 2.5 2, 3.0 2.2 1)',true))` | `1.0` |
| `SELECT ST_MinM(ST_GeomFromText('POINT M (1.5 2.5 3)',true))` | `3.0` |
## (75) ST_MinX

### Definition
Takes a geometry as an input parameter and returns its minimum x-coordinate.

### Syntax
<code>ST_MinX(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MinX(ST_GeomFromText('LINESTRING M (1.25 2.5 2, 3.0 2.2 1)',true))` | `1.25` |
| `SELECT ST_MinX(ST_GeomFromText('POINT M (1.75 2.5 3)',true))` | `1.75` |
## (76) ST_MinY

### Definition
Takes a geometry as an input parameter and returns its minimum y-coordinate.

### Syntax
<code>ST_MinY(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MinY(ST_GeomFromText('LINESTRING M (1.5 2.5 2, 3.0 2.2 1)',true))` | `2.2` |
| `SELECT ST_MinY(ST_GeomFromText('POINT M (1.5 2.25 3)',true))` | `2.25` |
## (77) ST_MinZ

### Definition
Takes a geometry as an input parameter and returns its minimum z-coordinate.

### Syntax
<code>ST_MinZ(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_MinZ(ST_GeomFromText('LINESTRING ZM (1.5 2.5 2 60, 3.0 2.2 1 80)',true))` | `1.0` |
| `SELECT ST_MinZ(ST_GeomFromText('LINESTRING Z (1.5 2.5 3, 3.0 2.2 4)',true))` | `3.0` |
## (78) ST_NumGeometries

### Definition
Returns the number of geometries in the geometry collection.

### Syntax
<code>ST_NumGeometries(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_NumGeometries(ST_GeomFromText('MULTIPOINT ((10 40), (40 30), (20 20), (30 10))',true))` | `4` |
| `SELECT ST_NumGeometries(ST_GeomFromText('MULTILINESTRING ((2 4, 10 10), (20 20, 7 8))',true))` | `2` |
## (79) ST_NumInteriorRing

### Definition
Returns the number of interior rings in the polygon geometry.

### Syntax
<code>ST_NumInteriorRing(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_NumInteriorRing(ST_GeomFromText('POLYGON ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))',true))` | `1` |
## (80) ST_NumPoints

### Definition
Returns the number of points (vertices) in the geometry. For polygons, both the starting and ending vertices are counted, even though they occupy the same location.

### Syntax
<code>ST_NumPoints(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_NumPoints(ST_Point(1.5, 2.5))` | `1` |
| `SELECT ST_NumPoints(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true))` | `2` |
| `SELECT ST_NumPoints((ST_GeomFromText('POLYGON ((0 0, 10 0, 0 10, 0 0))',true)))` | `4` |
## (81) ST_Overlaps

### Definition
Returns true if *geometry1* overlaps *geometry2*.

### Syntax
<code>ST_Overlaps(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Overlaps(ST_GeomFromText('POLYGON ((2 0, 2 3, 3 0))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `true` |
| `SELECT ST_Overlaps(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `false` |
## (82) ST_Point

### Definition
Returns a 2D point geometry from the provided lon (x) and lat (y) values.

### Syntax
<code>ST_Point(number <em>lon</em>, number <em>lat</em>)</code>

### Return Type
`binary`

-------------

## (83) ST_PointN

### Definition
Returns the point that is the nth vertex in an LineString or MultiPoint (1-based index)

### Syntax
<code>ST_PointN(binary <em>geometry</em>, number <em>index</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_PointN(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true), 2))` | `'POINT (3 2.2)'` |
## (84) ST_PointZ

### Definition
Returns a 3D point geometry from the provided lon (x), lat (y), and elev (z) values.

### Syntax
<code>ST_PointZnumber <em>lon</em>, number <em>lat</em>, number <em>elev</em></code>

### Return Type
`binary`

-------------

## (85) ST_Relate

### Definition
Compares the two geometries and returns true if the geometries meet the conditions specified by the DE-9IM pattern matrix string, otherwise, false is returned.

### Syntax
<code>ST_Relate(binary <em>geometry1</em>, binary <em>geometry2</em>, string <em>relation</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Relate(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), '****T****')` | `true` |
| `SELECT ST_Relate(ST_GeomFromText('POLYGON ((2 0, 2 1, 3 1))',true), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), 'T********')` | `false` |
| `SELECT ST_Relate(ST_GeomFromText('LINESTRING (0 0, 3 3)',true), ST_GeomFromText('LINESTRING (1 1, 4 4)',true), 'T********')` | `true` |
| `SELECT ST_Relate(ST_GeomFromText('LINESTRING (0 0, 3 3)',true), ST_GeomFromText('LINESTRING (1 1, 4 4)',true), '****T****')` | `false` |
## (86) ST_SetSRID

### Definition
Sets the Spatial Reference ID of *SRID* of the geometry.

### Syntax
<code>ST_SetSRID(binary <em>geometry</em>, number <em>SRID</em>)</code>

### Return Type
`binary`

-------------

## (87) ST_Simplify

### Definition
Simplifies the geometry or determines if the geometry is simple. The goal is to produce a geometry that is valid to store without additional processing.

### Syntax
<code>ST_Simplify(binary <em>geometry</em>)</code>

### Return Type
`binary`

-------------

## (88) ST_StartPoint

### Definition
Returns the first point of a Linestring.

### Syntax
<code>ST_StartPoint(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_StartPoint(ST_GeomFromText('LINESTRING (1.5 2.5, 3.0 2.2)',true)))` | `'POINT(1.5 2.5)'` |
## (89) ST_SymmetricDiff

### Definition
Returns a geometry object that is the symmetric difference of the source objects.

### Syntax
<code>ST_SymmetricDiff(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_SymmetricDiff(ST_GeomFromText('LINESTRING (0 2, 2 2)',true), ST_GeomFromText('LINESTRING (1 2, 3 2)',true)))` | `'MULTILINESTRING ((0 2, 1 2), (2 2, 3 2))'` |
| `SELECT ST_AsText(ST_SymmetricDiff(ST_GeomFromText('POLYGON ((0 0, 2 0, 2 2, 0 2, 0 0))',true), ST_GeomFromText('POLYGON ((1 1, 3 1, 3 3, 1 3, 1 1))',true)))` | `'MULTIPOLYGON (((0 0, 2 0, 2 1, 1 1, 1 2, 0 2, 0 0)), ((2 1, 3 1, 3 3, 1 3, 1 2, 2 2, 2 1)))'` |
## (90) ST_Touches

### Definition
Returns true if none of the points common to both geometries intersect the interiors of both geometries, otherwise, it returns false. At least one geometry must be a LineString, Polygon, MultiLineString, or MultiPolygon.

### Syntax
<code>ST_Touches(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Touches(ST_Point(1, 2), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `true` |
| `SELECT ST_Touches(ST_Point(8, 8), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `false` |
## (91) ST_Transform

### Definition
Takes the two-dimensional geometry as input and returns values converted from the spatial source reference specified by *sourceSRID* to the one specified by *targetSRID*.

### Syntax
<code>ST_Transform(binary <em>geometry</em>, number <em>sourceSRID</em>, number <em>targetSRID</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Transform(ST_GeomFromText('POLYGON ((-114.04702599994988 39.90609700007656, -114.0500520000997 37.0001909997149, -109.04517199998776 36.99897700038832, -109.05002599989996 41.000691000389395, -111.04681499981234 40.997875000031286, -111.04671399965133 42.00170200004732, -114.04147700036322 41.99387299963928, -114.04702599994988 39.90609700007656))', true), 4326, 3857))` | `'POLYGON ((-12695656.860801652 4852305.919673687, -12695993.71359747 4439133.410181124, -12138853.020503571 4438964.195256694, -12139393.365302108 5012443.58678148, -12361674.899993964 5012028.231889712, -12361663.65670747 5161234.398812287, -12695039.148993252 5160061.69329091, -12695656.860801652 4852305.919673687))'` |
## (92) ST_Union

### Definition
Returns a geometry as the union of the two supplied geometries.

### Syntax
<code>ST_Union(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Union(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), ST_GeomFromText('POLYGON ((4 1, 4 4, 4 8, 8 1))',true)))` | `'POLYGON ((1 1, 4 1, 8 1, 4 8, 4 4, 1 4, 1 1))'` |
## (93) ST_Union

### Definition
Returns a geometry as the union of the supplied geometry.

### Syntax
<code>ST_Union(binary <em>geometry</em>)</code>

### Return Type
`binary`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_AsText(ST_Union(ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true), ST_GeomFromText('POLYGON ((4 1, 4 4, 4 8, 8 1))',true)))` | `'POLYGON ((1 1, 4 1, 8 1, 4 8, 4 4, 1 4, 1 1))'` |
## (94) ST_Within

### Definition
Returns true if *geometry1* is completely inside *geometry2*.

### Syntax
<code>ST_Within(binary <em>geometry1</em>, binary <em>geometry2</em>)</code>

### Return Type
`boolean`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Within(ST_Point(2, 3), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `true` |
| `SELECT ST_Within(ST_Point(8, 8), ST_GeomFromText('POLYGON ((1 1, 1 4, 4 4, 4 1))',true))` | `false` |
## (95) ST_X

### Definition
Takes a Point as an input parameter and returns its longitude (x) coordinate.

### Syntax
<code>ST_X(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_X(ST_Point(5, 7))` | `5.0` |
## (96) ST_Y

### Definition
Takes a Point as an input parameter and returns its latitude (y) coordinate.

### Syntax
<code>ST_Y(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Y(ST_GeomFromText('POINT (5 7)',true))` | `7.0` |
## (97) ST_Z

### Definition
Takes a Point as an input parameter and returns its elevation (z) coordinate.

### Syntax
<code>ST_Z(binary <em>geometry</em>)</code>

### Return Type
`number`

### Examples
| Query      | Result |
| ----------- | ----------- |
| `SELECT ST_Z(ST_GeomFromText('POINT Z (5 7 9)',true))` | `9.0` |
