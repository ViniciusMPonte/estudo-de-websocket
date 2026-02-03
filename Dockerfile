FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY application/ .

RUN chmod +x ./gradlew && \
    ./gradlew clean bootJar

FROM eclipse-temurin:17-jre

EXPOSE 8080

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]