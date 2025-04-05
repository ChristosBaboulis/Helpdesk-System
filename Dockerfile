FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Αντιγραφή μόνο των απαραίτητων αρχείων
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests -Dquarkus.package.type=uber-jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/helpdesk-2.0.0-SNAPSHOT-runner.jar app.jar
CMD ["java", "-jar", "app.jar"]
