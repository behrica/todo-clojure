#!/bin/bash
set -e
lsof -n -i:8080 | grep LISTEN | awk '{ print $2 }' | uniq | xargs -r kill -9
lsof -n -i:4444 | grep LISTEN | awk '{ print $2 }' | uniq | xargs -r kill -9
rm -rf target
lein karma
lein do clean, midje, install
./selenium/start &
lein ring server-headless 8080  &
sleep 10
lein shell protractor resources/protractor_conf.js