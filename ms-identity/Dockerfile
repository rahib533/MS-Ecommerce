FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN ./mvnw -B -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /ms-identity
COPY --from=build /app/target/*.jar /ms-identity/ms-identity.jar
ENTRYPOINT ["java","-jar","ms-identity.jar"]