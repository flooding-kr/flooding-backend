FROM gradle:jdk21-alpine

COPY build.gradle.kts settings.gradle.kts gradlew .editorconfig ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x ./gradlew

RUN ./gradlew clean build

COPY /build/libs/flooding-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar" ]
