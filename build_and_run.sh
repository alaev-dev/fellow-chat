#!/bin/bash

# Запускаем Gradle build
echo "Running gradle build..."
gradle build

# Проверяем успешность выполнения команды
if [ $? -ne 0 ]; then
    echo "Gradle build failed"
    exit 1
fi

# Собираем Docker образ
echo "Building Docker image..."
docker build -t svalka.cr.cloud.ru/giga-fellow-chat:latest .

# Проверяем успешность выполнения команды
if [ $? -ne 0 ]; then
    echo "Docker build failed"
    exit 1
fi

# Запускаем Docker Compose
echo "Starting Docker Compose..."
docker-compose up
