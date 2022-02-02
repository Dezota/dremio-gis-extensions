# Dremio GIS Extensions


| Supported Dremio Version | Author                            |
|--------------------------|-----------------------------------|
| 19.1.0                   | Brian Holman <bholman@dezota.com> |

The ClickHouse connector allows Dremio to connect to and query data in the high performance ClickHouse Columnar Database.

## Building and Installation

1. In root directory with the pom.xml file run `mvn clean install`
2. Take the resulting `target/dremio-gis-extensions-19.1.0-202111160130570172-0ee0045.jar` file put it in to the `/opt/dremio/jars` folder of your Dremio 19.1.0 installation.  The Clickhouse JDBC Driver is included in the compiled jar and doesn't need to be included seperately in `/opt/dremio/jars/3rdparty`.
3. Restart Dremio

## Docker Instructions
1. Copy `target/dremio-gis-extensions-19.1.0-202111160130570172-0ee0045.jar.jar` into the `docker` folder.
2. Run `docker-compose build` to take the base Dremio Docker Image and add the new connector.
3. Run `docker-compose up` to start the new image.

## Usage

To see example usage, look at the [tests](./tests/TESTS.md)

## Inspiration
*    https://github.com/christyharagan/dremio-gis
*    https://github.com/k255/drill-gis

## Dependencies

The ```com.esri.geometry:esri-geometry-api``` and ```org.osgeo:proj4j``` libraries are required and not installed in Dremio.
They are automatically included as shade jars in the final jar with the maven build process.


