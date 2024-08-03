#!/bin/bash
BACKUP_FILE="/mnt/gcs/backup-$(date +\%Y-\%m-\%dT\%H:\%M:\%S).sql"

echo "Creating backup file $BACKUP_FILE"
pg_dump postgresql://$POSTGRES_USER:$POSTGRES_PASSWORD@$POSTGRES_HOST/$POSTGRES_DB > $BACKUP_FILE

echo "Backup complete"