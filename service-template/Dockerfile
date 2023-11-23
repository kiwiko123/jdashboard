FROM gradle:7.5.1-jdk18 AS BUILD
ARG BUILD_WORKSPACE=/build/service-template
COPY . $BUILD_WORKSPACE
WORKDIR $BUILD_WORKSPACE
RUN ["./gradlew", "clean", "build"]

FROM openjdk:18-jdk-slim
VOLUME /tmp
ARG APP_WORKSPACE=/app/service-template
COPY --from=BUILD /build/service-template/build/libs/*.jar $APP_WORKSPACE/app.jar
WORKDIR $APP_WORKSPACE
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/service-template/app.jar"]