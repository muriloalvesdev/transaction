#!/bin/bash
export MAVEN_OPTS="-Xmx1G -Xms128m"
echo "--- Starting unit tests now ---"
mvn package -Dgroups="unit-test"