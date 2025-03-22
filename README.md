# Library_Management_Application

Система управления библиотекой с использованием Java, Spring Boot, Spring Security, PostgreSQL, JWT, MapStruct, Docker. Обеспечивает регистрацию, авторизацию, добавление, редактирование, удаление и просмотр книг с фильтрацией.

---

## ✨ Основные возможности

* Регистрация и авторизация пользователей
* Создание книги
* Просмотр списка всех книг
* Получение книги по идентификатору (ID)
* Обновление информации о книге
* Удаление книги
* Поиск книг по автору, названию или жанру
* Аутентификация с использованием JWT
* Документирование API с помощью Swagger UI
* Централизованная обработка исключений
* Логирование действий пользователей и системных событий

---

## ⚙️ Используемые технологии

* [Java 17](https://www.java.com/) — язык программирования
* [Spring Boot 3](https://spring.io/projects/spring-boot) — фреймворк для REST API
* [Spring Security](https://spring.io/projects/spring-security) — аутентификация и авторизация (JWT)
* [Hibernate ORM](https://hibernate.org/orm/) — ORM-слой
* [MapStruct](https://mapstruct.org/) — преобразование DTO <-> Entity
* [PostgreSQL](https://www.postgresql.org/) — реляционная БД
* [JUnit 5](https://junit.org/junit5/) и [Mockito](https://site.mockito.org/) — модульное тестирование
* [Swagger UI](https://swagger.io/tools/swagger-ui/) — документация API
* [Docker Compose](https://docs.docker.com/compose/) — контейнеризация БД
* [Gradle](https://gradle.org/) — система сборки

---

## 🔄 Архитектура проекта

* **Controller** — обработка HTTP-запросов (REST)
* **Service** — бизнес-логика
* **Repository** — работа с базой данных (JPA/Hibernate)
* **Entity** — сущности базы данных
* **DTO** — объекты передачи данных
* **Mapper** — MapStruct-мэпперы DTO <-> Entity
* **Security** — фильтры и конфигурация Spring Security (JWT)
* **Exception Handling** — глобальный обработчик ошибок

---

## 🚀 Как запустить проект локально

### 1. Установка необходимого ПО

```shell
- Docker Desktop
- Java 17 (Amazon Corretto)
- IntelliJ IDEA (или другая IDE)
```

### 2. Клонирование репозитория

```shell
git clone https://github.com/<your_username>/libraryManagement.git
```

### 3. Запуск PostgreSQL через Docker

```shell
cd libraryManagement
docker-compose up -d
```

Контейнер с БД поднимется на `localhost:5430`.

### 4. Сборка и запуск приложения

* Открой проект в IntelliJ IDEA
* Дождитесь загрузки зависимостей Gradle
* Найдите класс:

```text
com.example.libraryManagement.LibraryManagementApplication
```

* ПКМ → **Run 'LibraryManagementApplication'**

### 5. Проверка работы API

Перейдите в браузере по адресу:

```text
http://localhost:8080/swagger-ui.html
```

---

## 🔬 Тестирование

В проекте реализованы модульные тесты:

* Unit-тесты сервисов и контроллеров
* Использованы JUnit 5 и Mockito

Запуск возможен через:

```text
Gradle → Tasks → verification → test
```
или через IntelliJ IDEA (ПКМ → Run Tests)

---

## 📁 Конфигурация приложения

Все параметры — в `src/main/resources/application.yml`:

```yaml
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
```

---

## 📄 Документация API

Swagger UI доступен по адресу:

```text
http://localhost:8080/swagger-ui.html
```

Позволяет тестировать endpoints без использования Postman или других инструментов.

---

✅ Готово! Проект запускается локально, документирован, покрыт тестами и легко разворачивается благодаря Docker.
