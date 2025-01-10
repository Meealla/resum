# Базовый образ Java
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файлы Gradle Wrapper и проект в контейнер
COPY gradlew gradlew
COPY gradle gradle
COPY . .

# Устанавливаем права на выполнение Gradle Wrapper
RUN chmod +x gradlew

# Собираем приложение с использованием Gradle
RUN ./gradlew build -x test --no-daemon

# Экспонируем порт, на котором работает приложение
EXPOSE 8083

# Команда для запуска JAR-файла
CMD ["java", "-jar", "build/libs/resume-analyzer-0.0.1-SNAPSHOT.jar"]
