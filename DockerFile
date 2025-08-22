# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file from the build stage
COPY --from=build /app/target/EmpayITUssd-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8081

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
