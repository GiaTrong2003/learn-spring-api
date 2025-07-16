# Learn Spring API

A Spring Boot REST API project for learning purposes, demonstrating CRUD operations with MySQL database integration.

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)

## ✨ Features
- RESTful API with Spring Boot
- User management (CRUD operations)
- MySQL database integration with JPA/Hibernate
- Profile-based configuration (dev/prod)
- Lombok for reducing boilerplate code
- Maven build system

## 🛠 Tech Stack
- **Java**: 24
- **Spring Boot**: 3.5.3
- **Spring Data JPA**: For database operations
- **MySQL**: Database
- **Lombok**: Code generation
- **Maven**: Build tool

## 📋 Prerequisites
- Java JDK 24
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA recommended)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd learn-spring-api
   
## Configuration
1. Update src/main/resources/application-dev.yml
```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/your_database_name
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```