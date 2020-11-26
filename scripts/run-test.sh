#!/bin/sh

./build/build-back.sh;

cd .. && docker-compose -f docker-compose.test.yml up --build --force-recreate --renew-anon-volumes db&

i=0
until [ $i -ge 10 ]
do
  nc -z localhost 3307 && break
 
  i=$(( i + 1 ))
 
  echo "$i: Waiting for DB 1 second ..."
  sleep 1
done
 
if [ $i -eq 10 ]
then
  echo "DB connection refused, terminating ..."
  exit 1
fi

cd .. && docker-compose -f docker-compose.test.yml up --abort-on-container-exit back tests
docker-compose down --remove-orphans;