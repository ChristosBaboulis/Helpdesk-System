#STAGE 1: BUILD THE APP
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

#COPY PROJECT FILES
COPY pom.xml .
COPY .mvn .mvn
COPY src src

#BUILD THE APP
RUN mvn clean package -DskipTests=false -Dquarkus.package.type=fast-jar

#STAGE 2: RUN THE APP WITH A LIGHTWEIGHT JRE IMAGE
FROM eclipse-temurin:21-jre
WORKDIR /app

#COPY BUILT APP FROM BUILD STAGE
COPY --from=build /app/target/quarkus-app /app/

#EXPOSE QUARKUS DEFAULT PORT
EXPOSE 8080

#RUN THE APP
ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
