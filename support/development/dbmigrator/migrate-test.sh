#!/usr/bin/env bash

/opt/high-touch-sign

./gradlew --no-daemon database-migrator:clean \
  && ./gradlew --no-daemon database-migrator:shadowJar \
  && java -cp /opt/high-touch-sign/database-migrator/build/libs/database-migrator-all.jar com.hightouchinc.digital.signature.migrate.MigrateDbKt -H htsigntestdb -P 5432 -d htsigntestdb