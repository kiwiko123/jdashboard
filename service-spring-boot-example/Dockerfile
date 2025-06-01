#FROM azul/zulu-openjdk-alpine:17.0.13-jdk
FROM eclipse-temurin:23

#RUN addgroup -S jdashboard-service-users
#RUN adduser -S example-service-user -G jdashboard-service-users
#USER example-service-user:jdashboard-service-users

ARG JAR_FILE=app/build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]