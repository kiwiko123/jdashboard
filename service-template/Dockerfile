FROM gradle:7.5.1-jdk18 AS BUILD

# => Replace "service-template" with your module's name
ARG BUILD_WORKSPACE=/build/service-template

# Copy common modules into the build workspace.
COPY ./platforms /build/platforms
COPY ./build-conventions /build/build-conventions
COPY ./library-framework /build/library-framework
COPY ./library-lang /build/library-lang

# => Replace "service-template" with your module's name
COPY ./service-template $BUILD_WORKSPACE
WORKDIR $BUILD_WORKSPACE
RUN ["./gradlew", "clean", "build"]

FROM openjdk:18-jdk-slim
VOLUME /tmp

# => Replace "service-template" with your module's name
ARG APP_WORKSPACE=/app/service-template

# => Replace "service-template" with your module's name (from above)
COPY --from=BUILD /build/service-template/build/libs/*.jar $APP_WORKSPACE/app.jar
WORKDIR $APP_WORKSPACE
EXPOSE 8080

# => Replace "service-template" with your module's name
ENTRYPOINT ["java", "-jar", "/app/service-template/app.jar"]