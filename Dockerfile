# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Build the app
RUN mvn clean package -DskipTests=false -Dquarkus.package.type=fast-jar

# Stage 2: Run the app with a lightweight JRE image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built app from build stage
COPY --from=build /app/target/quarkus-app /app/

# Expose Quarkus default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
