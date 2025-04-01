FROM maven:3.9-eclipse-temurin-22-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar ./server.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "server.jar"]