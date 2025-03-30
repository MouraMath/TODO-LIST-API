# Build stage (Gradle com JDK 24)
FROM gradle:8.8.0-jdk24 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Run stage (JRE 24)
FROM eclipse-temurin:24-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]