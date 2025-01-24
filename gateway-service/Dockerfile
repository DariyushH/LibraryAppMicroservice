FROM gradle:8.0.0-jdk17 AS build
WORKDIR /app
COPY gateway-service/build.gradle.kts gateway-service/settings.gradle.kts ./
COPY gateway-service/gradlew ./
COPY gateway-service/gradle gradle
COPY gateway-service/src ./src
RUN ./gradlew --version
RUN ./gradlew bootJar --no-daemon --info


FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java",  "-jar", "app.jar"]