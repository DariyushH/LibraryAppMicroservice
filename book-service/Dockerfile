FROM gradle:8.0.0-jdk17 AS build
WORKDIR /app
COPY book-service/build.gradle.kts book-service/settings.gradle.kts ./
COPY book-service/gradlew ./
COPY book-service/gradle gradle
COPY book-service/src ./src
RUN ./gradlew --version
RUN ./gradlew bootJar --no-daemon --info


FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/book-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java",  "-jar", "app.jar"]