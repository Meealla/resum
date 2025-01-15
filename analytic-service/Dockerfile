FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy necessary files: gradle wrapper and the application source code
COPY gradlew gradlew
COPY gradle gradle
COPY ../analytics-message ./analytics-message1
COPY . .

## Устанавливаем права на выполнение Gradle Wrapper
#RUN chmod +x gradlew
#
## Собираем приложение с использованием Gradle
#RUN ./gradlew build -x test --no-daemon

RUN chmod +x gradlew && ./gradlew build

# Expose the application port
EXPOSE 8084

# Command to run the application
CMD ["java", "-jar", "build/libs/analytic-service-0.0.1-SNAPSHOT.jar"]