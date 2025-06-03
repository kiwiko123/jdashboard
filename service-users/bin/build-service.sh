#!/bin/zsh
./gradlew build
docker buildx build -t jdashboard/example-service-dev .