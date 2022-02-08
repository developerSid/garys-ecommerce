#!/usr/bin/env bash

cd /opt/high-touch-sign

./gradlew --no-daemon dbmigrator:clean \
  && ./gradlew --no-daemon dbmigrator:shadowJar \
  && java -jar dbmigrator/build/libs/dbmigrator-all.jar -d ecomdevdb -H ecomdevdb -P 5432 -u ecomadmin -p password