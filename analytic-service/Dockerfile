FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем необходимые файлы
COPY gradlew gradlew
COPY gradle gradle
COPY ./analytic-service .

ADD ../analytics-message /analytics-message1

RUN chmod +x gradlew && ./gradlew build

# Порт приложения
EXPOSE 8084

# Команды для запуска приложения
CMD ["java", "-jar", "./analytic-service/build/libs/analytic-service-0.0.1-SNAPSHOT.jar"]

