# GIS Extensions for Dremio

| Tested Dremio Versions | Author                            |
|------------------------|-----------------------------------|
| 19.1.0 to 20.1.0       | Brian Holman <bholman@dezota.com> |

*This independent project is not affiliated with, sponsored, or endorsed by Dremio Corporation. Dremio is a registered trademark of Dremio Corporation and they retain all trademark and other intellectual property rights.  Dremio is used here by reference to integrating with their published [User-Defined Functions Specification](https://www.dremio.com/hub-additional/) for advanced users to develop their own custom functions for use in SQL queries.*

![DAC with GIS extensions](./docs/dremio_dac_with_gis.jpg)

The **GIS Extensions** allow Dremio to perform standard GIS functions within Dremio SQL with 71 industry-standard GIS functions. These extensions use the [*Esri Java Geometry Library*](https://github.com/Esri/geometry-api-java/wiki/) for the underlying implementation of the core geometry functions. The author made heavy use of Esri's [*Spatial Framework for Hadoop*](https://github.com/Esri/spatial-framework-for-hadoop) as a reference for a similar implementation that also relies on the same library.

There were two significant gaps in the Geometry Library supplied by Esri that limited transforming geometries from `EPSG: 4326` to other coordinate systems and performing geodesic rather than 2D area and length calculations. Geodesic area function helpers backing the `ST_GeodesicAreaWGS84` function are copied almost exactly from the [*Trino Geospatial Library*](https://github.com/trinodb/trino/tree/master/plugin/trino-geospatial) as found in our `FunctionHelpers.stSphericalArea()` and `FunctionHelpers.computeSphericalExcess()`. Conversion to other coordinate systems in the `ST_Transform` function leverages the [Proj4J Library](https://trac.osgeo.org/proj4j/). All of the referenced works are also published under the *Apache 2.0 License*.

## Usage and Available Functions

View the [SQL Function Reference](./docs/sqlFunctions.md) for definitions, syntax, and examples of the 66 functions implemented or [download the PDF](./docs/DremioGISExtFuncRef.pdf).

## Building and Installation

1. In root directory with the pom.xml file run `mvn clean install`
2. Take the resulting `target/dremio-gis-extensions-*.jar` file put it in the `/opt/dremio/jars` folder of your Dremio
   installation.
3. Restart Dremio

## Docker Instructions

1. The `target/dremio-gis-extensions-*.jar.jar` is copied into the `docker/build` folder by Maven.
2. In the `docker` directory, run `docker-compose build` to take the base Dremio Docker Image and add the new
   extensions.
3. Run `docker-compose up` to start the new image.

## Docker Image (on Docker Hub or build yourself)
See [Dremio Docker Build with Dezota Extensions](https://github.com/Dezota/dremio-docker-with-extensions) for a complete solution that includes patches to Dremio to support Varchar and VarBinary fields large enough to accomodate GIS data.

## Inspiration

* https://github.com/christyharagan/dremio-gis
* https://github.com/k255/drill-gis
* https://github.com/Esri/spatial-framework-for-hadoop
* https://github.com/geographiclib/geographiclib/tree/main/java
* https://github.com/Esri/geometry-api-java
* [Algorithms for geodesics by Charles F. F. Karney](https://arxiv.org/pdf/1109.4448.pdf)
* [Trino Geospatial Toolkit](https://github.com/trinodb/trino/tree/master/plugin/trino-geospatial)
* [Geodesic intersection: proposed algorithm and error assessment of current software](https://cartosig.webs.upv.es/2021/07/27/geodesic-intersection-proposed-algorithm-and-error-assessment-of-current-software/)
  * [Geodesic Spatial Operators on the ellipsoid](https://github.com/jomarlla/geodesicSpatialOperators)
  * [Research Paper PDF](https://www.mdpi.com/2076-3417/11/11/5129/pdf)

## Dependencies

The ```com.esri.geometry:esri-geometry-api``` and ```org.osgeo:proj4j``` libraries are required and not installed in
Dremio. They are automatically included as shade jars in the final jar with the maven build process and don't need to be
included separately in `/opt/dremio/jars/3rdparty`.


