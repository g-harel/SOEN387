#!/bin/bash

# load variables written in secret .config
source .config

# tunnel to remote and run shema.sql
export SSHPASS="$DB_PASSWORD"
sshpass -e ssh $DB_USERNAME@dumbledore.encs.concordia.ca << ENDCOMMANDS
echo
mysql -u$DB_USERNAME -p$DB_PASSWORD $DB_USERNAME << ENDSCHEMA
    $(cat "$1.sql")
ENDSCHEMA
ENDCOMMANDS
