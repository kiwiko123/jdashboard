## Run the service
### ...on the host machine
```shell
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=dev' 
```

### ...via Docker
```shell
./bin/build-service.sh
./bin/run-service.sh
```