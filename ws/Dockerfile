# Base image
FROM openjdk:17-jdk-slim

# Application directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar /app/app.jar

# Copy the .env file
COPY .env /app/.env

# Run the application
CMD ["java", "-jar", "app.jar"]
