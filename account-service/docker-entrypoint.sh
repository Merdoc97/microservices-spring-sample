#!/bin/sh
echo "DB_MIGRATION = $DB_MIGRATION"
if [ "$DB_MIGRATION" = "true" ]
then
    echo "run migration"
    . ./run_migration.sh
else
       echo "run application"
    . ./run_application.sh
fi
