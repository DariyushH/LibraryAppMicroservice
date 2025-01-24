FROM gradle:8.0.0-jdk17 AS build
WORKDIR /app
COPY image-service/build.gradle.kts image-service/settings.gradle.kts ./
COPY image-service/gradlew ./
COPY image-service/gradle gradle
COPY image-service/src ./src
RUN ./gradlew --version
RUN ./gradlew bootJar --no-daemon --info


FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/image-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java",  "-jar", "app.jar"]