Library Management Application

Это веб-приложение представляет собой систему управления библиотекой, реализованную на Java с использованием фреймворка
Spring Boot, PostgreSQL, JWT-аутентификации, MapStruct для маппинга сущностей и DTO, а также Docker-контейнеризации.

Основные возможности приложения

Регистрация и авторизация пользователей

Управление книгами:

Создание книги

Просмотр списка всех книг

Получение книги по идентификатору (ID)

Обновление информации о книге

Удаление книги

Поиск книг по автору, названию или жанру

Аутентификация с использованием JWT

Документирование API с помощью Swagger UI

Централизованная обработка исключений

Подробное логирование действий пользователей и системных событий

Использованные технологии

Java 17 — основной язык разработки

Spring Boot 3 — фреймворк для создания приложения и REST API

Spring Security — для защиты приложения (JWT-аутентификация)

Hibernate ORM — для работы с базой данных PostgreSQL

MapStruct — для маппинга между сущностями и DTO

PostgreSQL — основная база данных

JUnit 5 и Mockito — для написания модульных (unit) тестов

Swagger UI — документация REST API

Docker Compose — для контейнеризации и быстрого развертывания БД

Gradle — система сборки проекта

Архитектура приложения

Контроллеры (Controllers): Обрабатывают HTTP-запросы и возвращают ответы.

Сервисы (Services): Реализуют бизнес-логику приложения.

Репозитории (Repositories): Отвечают за взаимодействие с базой данных с использованием EntityManager и JPQL.

Сущности (Entities): Представляют объекты базы данных.

DTO: Объекты передачи данных для запросов и ответов.

Mapper-ы (MapStruct): Автоматическое преобразование между сущностями и DTO.

Security: JWT-фильтры и классы для реализации аутентификации и авторизации.

Exception Handling: Глобальная обработка ошибок и исключений.

Как скачать и запустить проект локально

 Шаг 1: Установка необходимого ПО

Установите Docker Desktop (для запуска PostgreSQL в контейнере).

Установите Java 17 (Amazon Corretto).

Установите IntelliJ IDEA (рекомендуемая IDE).

 Шаг 2: Клонирование репозитория

git clone https://github.com/<your_username>/libraryManagement.git

 Шаг 3: Запуск Docker-контейнера PostgreSQL

Перейдите в директорию проекта и запустите команду:

cd libraryManagement
docker-compose up -d

Эта команда скачает образ PostgreSQL и запустит его в Docker-контейнере.

 Шаг 4: Сборка и запуск приложения

Откройте проект в IntelliJ IDEA.

Подождите, пока Gradle загрузит все зависимости.

Перейдите в основной класс:

com.example.libraryManagement.LibraryManagementApplication

Запустите приложение:

ПКМ -> Run 'LibraryManagementApplication'

 Шаг 5: Проверка работоспособности приложения

После запуска приложения перейдите в браузере по адресу:

http://localhost:8080/swagger-ui.html

Теперь вы можете тестировать API.

 Тестирование

Приложение содержит модульные (unit) тесты на сервисы и контроллеры. Тесты написаны с использованием:

JUnit 5

Mockito

Запустить тесты можно из IntelliJ IDEA через панель Gradle или стандартным способом (ПКМ -> Run Tests).

 Конфигурация приложения

Настройки подключения к базе данных и JWT находятся в файле:

src/main/resources/application.yml

Пример текущих конфигураций:

spring:
datasource:
url: jdbc:postgresql://localhost:5430/library
username: admin
password: admin
driver-class-name: org.postgresql.Driver

jpa:
properties:
hibernate:
dialect: org.hibernate.dialect.PostgreSQLDialect
hibernate:
ddl-auto: update
show-sql: true

jwt:
secret: your_secret_key_here_very_secure_and_long

 Документация API

Документация по API доступна по адресу:

http://localhost:8080/swagger-ui.html

Swagger позволяет отправлять тестовые запросы и изучать все endpoints приложения.