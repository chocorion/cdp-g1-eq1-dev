#!/bin/sh

./build/build-back.sh && ./build/build-front.sh;
docker-compose up --build --force-recreate --renew-anon-volumes;
docker-compose down --remove-orphans;