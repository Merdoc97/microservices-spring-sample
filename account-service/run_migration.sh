#!/bin/sh

echo 'Run migration'
ls -l
ls ./BOOT-INF
ls ./BOOT-INF/classes
ls ./BOOT-INF/classes/db/migration
java -cp "BOOT-INF/lib/*:BOOT-INF/classes" org.flywaydb.commandline.Main \
      -driver=org.postgresql.Driver \
      -locations=filesystem:./BOOT-INF/classes/db/migration \
      -url=${SPRING_DATASOURCE_URL}  \
      -user=${SPRING_DATASOURCE_USERNAME} \
      -password=${SPRING_DATASOURCE_PASSWORD} \
      migrate

