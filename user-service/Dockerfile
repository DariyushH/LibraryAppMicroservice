FROM gradle:8.0.0-jdk17 AS build
WORKDIR /app
COPY user-service/build.gradle.kts user-service/settings.gradle.kts ./
COPY user-service/gradlew ./
COPY user-service/gradle gradle
COPY user-service/src ./src
RUN ./gradlew --version
RUN ./gradlew bootJar --no-daemon --info


FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/user-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java",  "-jar", "app.jar"]