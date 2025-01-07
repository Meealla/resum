FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application source code (assuming it's built elsewhere)
COPY . .

# Expose the port for the API Gateway
EXPOSE 8765

# Command to run the application (adjust if necessary, e.g., if using Spring Boot with JAR file)
CMD ["java", "-jar", "app.jar"]