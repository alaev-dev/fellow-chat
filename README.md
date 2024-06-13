# Fellow-chat

![Fellow-chat Logo](images/logo.webp)

## Описание

Fellow-chat - это бэкенд для чата, реализованный с использованием WebSocket. Проект демонстрирует, насколько просто
можно писать приложения на WebSocket.

## Демонстрация

Проект уже развернут и доступен по ссылке: https://chat.vsezol.com/user

---

## Технологии

- Spring
- Kotlin
- PostgreSQL

---

## Инструкции по установке

1. Сборка проекта с помощью Gradle:

   ```bash
   gradle build # Важно выполнить этот шаг для уменьшения итогового размера Docker-образа.
   docker build -t svalka.cr.cloud.ru/giga-fellow-chat:latest . # Сборка Docker-образа:
   docker-compose up # Запуск с помощью Docker Compose (можно добавить флаг `-d` для запуска в фоновом режиме)
   ```
2. Или же простой запуск одного файла:

    ```shell
   chmod +x /build_and_run.sh # Даем права на запуск
   ./build_and_run.sh 
   ```

---

## Авторы

Если у вас есть вопросы или предложения, пожалуйста, свяжитесь с нами, мы открыты к предложениям

- Константин Алаев (Backend)
    - [Telegram](https://t.me/alaev_dev)
    - [Email](mailto:alaevdev@gmail.com?subject=Fellow-chat)
- Всеволод Золотов (Frontend)
    - [Telegram](https://t.me/vsezold)
