FROM gradle:8.0.0-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew ./
COPY gradle gradle
COPY src ./src
RUN ./gradlew --version
RUN ./gradlew bootJar --no-daemon --info


FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/book-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java",  "-jar", "app.jar"]