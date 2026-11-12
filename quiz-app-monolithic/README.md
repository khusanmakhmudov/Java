# Quiz Application – Monolithic Architecture

## About This Project

This repository contains a Quiz Application developed using Java and Spring Boot, following a **Monolithic Architecture**.

The original project was created by **Navin Reddy (Telusko)**. We are learning from his tutorials and using this project to improve our understanding of Java, Spring Boot, REST APIs, database integration, and backend application development.

A special thanks to **Navin Reddy** for sharing his knowledge and providing valuable educational content for developers.

## Original Creator and Source

- **Original Creator:** Navin Reddy (Telusko)
- **Original Repository:** https://github.com/navinreddy20/quiz-app-spring
- **Purpose:** Educational and learning purposes

## Project Overview

The application provides functionality for managing questions and creating quizzes.

It allows users to:

- Retrieve all available questions.
- Retrieve questions based on categories.
- Add new questions to the database.
- Create quizzes with a specified category and number of questions.
- Retrieve questions belonging to a particular quiz.
- Submit answers and calculate quiz results.

## Architecture

This project follows a **Monolithic Architecture**, meaning that the application's main functionalities are implemented within a single Spring Boot application.

The application uses a layered backend structure:

1. **Controller Layer:** Handles incoming HTTP requests and exposes REST API endpoints.
2. **Service Layer:** Contains the application's business logic.
3. **Repository Layer:** Handles database operations using Spring Data JPA.
4. **Model Layer:** Represents application entities and data objects.

The application uses PostgreSQL as its relational database.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.1.0 | Backend application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| PostgreSQL | Relational database |
| Lombok | Reducing boilerplate code |
| Maven | Build and dependency management |

## Main Functionalities

### 1. Question Management

The application provides functionality to:

- Retrieve all questions.
- Filter questions by category.
- Add new questions.

Each question contains a title, four answer options, the correct answer, difficulty level, and category.

### 2. Quiz Management

The application provides functionality to:

- Create a quiz by category.
- Specify the number of questions.
- Assign a title to the quiz.
- Retrieve quiz questions using a quiz ID.
- Submit answers and calculate the final score.

## REST API Endpoints

### Question APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/question/allQuestions` | Retrieve all questions |
| GET | `/question/category/{category}` | Retrieve questions by category |
| POST | `/question/add` | Add a new question |

### Quiz APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/quiz/create` | Create a new quiz |
| GET | `/quiz/get/{id}` | Retrieve quiz questions |
| POST | `/quiz/submit/{id}` | Submit answers and calculate the score |

### Creating a Quiz

The quiz creation endpoint accepts the following request parameters:

- `category` – The category of questions.
- `numQ` – The number of questions.
- `title` – The quiz title.

Example:

`POST http://localhost:8080/quiz/create?category=Java&numQ=5&title=Java Quiz`

## Database

The project uses PostgreSQL for persistent data storage.

The original application configuration uses a PostgreSQL database named `questiondb`.

Before running the application, configure your local database connection in:

`src/main/resources/application.properties`

Update the database username and password to match your local PostgreSQL configuration. Never commit personal database credentials to GitHub.

## Running the Application

### Prerequisites

- Java JDK 17
- PostgreSQL
- Maven (optional, because the project includes Maven Wrapper)
- IntelliJ IDEA, Eclipse, or another Java IDE

### Steps

1. Clone or download the repository.
2. Create a PostgreSQL database named `questiondb`.
3. Configure your database connection in `application.properties`.
4. Open the project in your preferred IDE.
5. Run the Spring Boot application.

Alternatively, use Maven Wrapper:

**Windows:**

`mvnw.cmd spring-boot:run`

**Linux / macOS:**

`./mvnw spring-boot:run`

The application runs on the default Spring Boot port, `8080`, unless configured otherwise.

## Learning Objectives

Through this project, we aim to understand:

- Monolithic application architecture.
- Spring Boot application development.
- RESTful API design and implementation.
- Controller, Service, and Repository layers.
- Spring Data JPA and Hibernate.
- PostgreSQL database integration.
- Entity relationships and data persistence.
- Building backend applications with Maven.

## Acknowledgements

All credit for the original project and tutorial content belongs to **Navin Reddy (Telusko)**.

We are grateful for his educational resources and the opportunity to learn practical Java and Spring Boot development through his work.

**Original Creator:** Navin Reddy  
**Learning Source:** [Telusko – Quiz App Spring](https://github.com/navinreddy20/quiz-app-spring)

---

*This repository is maintained as part of our personal learning journey in Java backend development. The original implementation and educational material belong to their respective creator.*
