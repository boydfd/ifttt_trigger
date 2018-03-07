#!/usr/bin/env bash
imageTag=$1
version=$2
docker push ${imageTag}
docker tag ${imageTag} ${imageTag}:${version}
docker push ${imageTag}:${version}
