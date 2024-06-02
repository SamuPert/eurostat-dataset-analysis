# Builder
FROM gradle:jdk17 AS BUILDER

WORKDIR /src
COPY . /src
RUN gradle build --no-daemon


# Runtime
FROM amazoncorretto:17-alpine-jdk

COPY --from=BUILDER /src/build/libs/eurostat-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]