# Build Stage
FROM gradle:jdk21-alpine AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew .editorconfig ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x ./gradlew

RUN ./gradlew clean build

#RUN Stage
FROM openjdk:21-slim

COPY --from=build /app/build/libs/flooding-backend-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar" ]
