# Stage 1 — Build the fat jar with Maven
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy POM first so dependency layer is cached unless pom.xml changes
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and package (skip tests — run them in CI, not image build)
COPY src src
RUN mvn clean package -DskipTests -B

# Stage 2 — Slim runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
