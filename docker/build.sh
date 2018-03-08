#!/usr/bin/env bash

dockerRegistry='192.168.42.10:5000'
imageName=ifttt-trigger
cd $(dirname $([ -L $0 ] && readlink -f $0 || echo $0))


set -x
docker build -t "$dockerRegistry/$imageName" .
docker push "$dockerRegistry/$imageName"

cd -
