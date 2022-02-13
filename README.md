# Dremio GIS Extensions

| Tested Dremio Versions | Author                            |
|------------------------|-----------------------------------|
| 19.1.0 to 20.1.0       | Brian Holman <bholman@dezota.com> |

The Dremio GIS Extensions allows Dremio to perform standard GIS functions within Dremio SQL.

## Recent Updates

1. *ST_GeomFromEWKB* - Converts from the raw geometry returned from PostGIS/PostgreSQL known as EWKB
2. *ST_GeomFromWKB* - Converts from the standard WKB format with an optional SRID specified
3. *ST_AsGeoJSON* - Converts from the GIS extensions native format to GeoJSON

## Usage and Available Functions

Look at the [Implementation Status of Proposed GIS Functions](./functions.md)

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

## Testing

Look at the [tests](./tests/TESTS.md)

## Inspiration

* https://github.com/christyharagan/dremio-gis
* https://github.com/k255/drill-gis
* https://github.com/Esri/spatial-framework-for-hadoop

## Dependencies

The ```com.esri.geometry:esri-geometry-api``` and ```org.osgeo:proj4j``` libraries are required and not installed in
Dremio. They are automatically included as shade jars in the final jar with the maven build process and don't need to be
included separately in `/opt/dremio/jars/3rdparty`.


