#!/bin/bash
set -e
lsof -n -i:8080 | grep LISTEN | awk '{ print $2 }' | uniq | xargs -r kill -9
lsof -n -i:4444 | grep LISTEN | awk '{ print $2 }' | uniq | xargs -r kill -9
karma start
rm -rf target
lein.sh do clean, midje, install
./selenium/start &
lein.sh ring server-headless 8080  &
sleep 10
lein.sh shell protractor resources/protractor_conf.js