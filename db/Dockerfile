# Stage 1: Build the application using Maven and JDK 23
FROM maven:3.9.3-openjdk-23 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Run tests online
RUN mvn -B test

# Package the application
RUN mvn -B clean package

# Rename the generated jar to a consistent name (fatjar.jar)
RUN mv target/*.jar target/fatjar.jar

# Stage 2: Create the runtime image using a lightweight JDK 23 image
FROM eclipse-temurin:23-jre
WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=builder /app/target/fatjar.jar app.jar

# Expose the default port (adjust if your app uses a different port)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
