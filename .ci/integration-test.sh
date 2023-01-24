#!/bin/bash
export MAVEN_OPTS="-Xmx1G -Xms128m"
echo "--- Starting integrated tests now ---"
mvn package -Dgroups="integration-test"