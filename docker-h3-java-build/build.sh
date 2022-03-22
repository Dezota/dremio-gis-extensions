#!/bin/bash
rm -rf target
docker build -t dezota/build-h3-java:1.0.0 .
docker-compose up -d
export DOCKER_ID=`docker ps | grep "dezota/build-h3-java:1.0.0" | cut -f 1 -d ' '`
echo $DOCKER_ID
docker cp $DOCKER_ID:/build/h3-java/target ./target
cp target/h3-3.7.2.jar ../docker/build/h3-3.7.2.jar
docker-compose down

