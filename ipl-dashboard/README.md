# IPL Dashboard
### Full-Stack Application | Java | Spring Boot | React.js | Spring Batch

## 1. Project Overview

The **IPL Dashboard** is a full-stack web application designed to explore historical Indian Premier League (IPL) cricket statistics and match information.

The application allows users to browse IPL teams, examine their historical performance, explore matches from different tournament seasons, and analyze team wins and losses.

The backend is developed using Java and Spring Boot, while the frontend uses React.js to provide an interactive user interface.

The application also demonstrates how historical data can be imported, processed, stored, and retrieved using Spring Batch, Spring Data JPA, and a relational database.

This project is based on the educational course and original implementation created by **Koushik Kothagal (Java Brains)**.

We studied and followed his educational material to improve our understanding of Java backend development, Spring Boot, batch processing, database integration, REST APIs, and full-stack application development.

## 2. Original Creator and Learning Resources

- **Original Creator:** Koushik Kothagal
- **Educational Platform:** Java Brains
- **Original GitHub Repository:** https://github.com/koushikkothagal/ipl-dashboard
- **Course:** Building an IPL Dashboard
- **Course Website:** https://javabrains.io/courses/building-an-ipl-dashboard
- **YouTube Playlist:** https://youtube.com/playlist?list=PLqq-6Pq4lTTa8V613TZhGq4o8hSgkMGQ0
- **Purpose:** Educational and learning purposes

A special thanks to Koushik Kothagal and Java Brains for providing valuable educational resources and demonstrating practical full-stack application development.

## 3. Project Objectives

The main objectives of this project are:

- Understand full-stack application development.
- Develop backend applications using Java and Spring Boot.
- Build RESTful APIs.
- Understand how frontend and backend applications communicate.
- Process large datasets using Spring Batch.
- Integrate relational databases using Spring Data JPA.
- Perform database queries using JPQL.
- Develop interactive frontend components using React.js.
- Understand application deployment using AWS Elastic Beanstalk.
- Learn how different technologies work together in a complete software application.

## 4. Main Features

### 4.1 IPL Team Dashboard

The application allows users to browse IPL teams and access their historical performance.

Users can explore the available teams and navigate to their respective team pages.

### 4.2 Historical Match Information

Users can explore historical IPL matches involving a selected team.

The application provides information about previous matches and allows users to examine their team's performance across different seasons.

### 4.3 Team Performance Analysis

The application presents historical team performance, including:

- Number of matches played.
- Matches won.
- Matches lost.
- Win and loss ratios.
- Historical performance across tournament seasons.

### 4.4 Season-Based Statistics

Users can explore team performance across different IPL tournament years.

This allows historical comparisons and makes it easier to understand how teams performed over time.

## 5. Technology Stack

### Backend

| Technology | Purpose |
|---|---|
| Java 11 | Backend programming language |
| Spring Boot 2.4.4 | Backend application framework |
| Spring Web | REST API development |
| Spring Batch | Historical data processing |
| Spring Data JPA | Database access and persistence |
| Hibernate | Object-relational mapping |
| JPQL | Database queries |
| HSQLDB | Relational database |
| Maven | Dependency and build management |

### Frontend

| Technology | Purpose |
|---|---|
| React.js | Frontend development |
| JavaScript | Frontend programming |
| HTML | Application structure |
| CSS | Styling and layout |
| REST APIs | Communication with the backend |

### Cloud and Deployment

| Technology | Purpose |
|---|---|
| AWS Elastic Beanstalk | Application deployment |

## 6. Application Architecture

The IPL Dashboard follows a full-stack architecture in which the frontend and backend have separate responsibilities.

The React frontend communicates with the Spring Boot backend through REST APIs.

The backend processes historical cricket data, manages database persistence, and provides information requested by the frontend.

### High-Level Architecture

```text
                 USER
                   |
                   v
           REACT FRONTEND
                   |
                   | HTTP Requests
                   | REST API
                   v
          SPRING BOOT BACKEND
                   |
          +--------+--------+
          |                 |
          v                 v
      REST API          JPA REPOSITORIES
          |                 |
          |                 v
          |               HSQLDB
          |
          v
      JSON RESPONSES
          |
          v
     REACT COMPONENTS
          |
          v
      DASHBOARD UI
```

### Historical Data Processing Architecture

```text
      HISTORICAL IPL DATASET
                |
                v
          SPRING BATCH
                |
          +-----+------+
          |            |
          v            v
      ITEM READER  ITEM PROCESSOR
                       |
                       v
                  ITEM WRITER
                       |
                       v
                    HSQLDB
```

The batch processing pipeline is responsible for importing and processing historical match information before it can be queried by the application.

## 7. Spring Batch and Data Processing

One of the important technologies used in this project is Spring Batch.

Spring Batch is a framework designed for processing large amounts of data in a structured and reliable way.

The application uses historical IPL match data obtained from a CSV dataset.

The general processing workflow includes:

1. Reading historical match records from CSV files.
2. Mapping records into Java objects.
3. Processing the records.
4. Persisting the processed information into the database.
5. Making the stored information available through backend services.

### Important Spring Batch Concepts

| Concept | Description |
|---|---|
| Job | Represents the complete batch process |
| Step | Represents an individual stage of a job |
| ItemReader | Reads input records |
| ItemProcessor | Processes and transforms records |
| ItemWriter | Writes processed records |
| JobRepository | Maintains job execution information |

This project provides practical exposure to data ingestion and batch-processing workflows.

## 8. Database Integration

The backend uses HSQLDB as its relational database.

Spring Data JPA provides repository abstractions for interacting with the database.

Hibernate handles object-relational mapping, allowing Java objects to be persisted as relational database records.

JPQL is used to express database queries in terms of entity objects and their properties.

This approach reduces the need to write database-specific SQL for common data-access operations.

## 9. REST API and Frontend Communication

The application demonstrates communication between a Spring Boot backend and a React frontend.

The general workflow is:

1. The user interacts with a React component.
2. The frontend sends an HTTP request.
3. Spring Boot receives the request through a REST endpoint.
4. The backend retrieves information from the database.
5. The backend returns the response in JSON format.
6. React processes the response and updates the interface.

This architecture separates presentation logic from backend business and data-access responsibilities.

## 10. React.js Frontend

React.js is used to build the interactive user interface.

The frontend allows users to navigate between the team dashboard and historical match pages.

It communicates with backend REST APIs to retrieve the information required to display the dashboard.

The frontend demonstrates important concepts such as:

- React components.
- Component-based UI development.
- State management.
- HTTP requests.
- API integration.
- Dynamic rendering.
- Frontend and backend separation.

## 11. Historical Dataset

The application uses an IPL historical match dataset from Kaggle.

Dataset:

https://www.kaggle.com/patrickb1912/ipl-complete-dataset-20082020/metadata

The dataset contains historical cricket match information covering the 2008–2020 IPL seasons.

It serves as the source for the application's historical match information and team performance analysis.

## 12. Deployment

The original project includes AWS Elastic Beanstalk as part of its technology stack and learning objectives.

AWS Elastic Beanstalk is a Platform as a Service (PaaS) offering that simplifies application deployment and management.

It allows developers to deploy applications without manually managing every underlying infrastructure component.

The Java Brains course also covers preparing the application for deployment and deploying it to AWS.

## 13. Running the Backend

### Prerequisites

- Java JDK 11 or compatible newer JDK.
- Maven or the included Maven Wrapper.
- An appropriate development environment, such as IntelliJ IDEA or Eclipse.

### Steps

1. Clone or download the repository.
2. Open the project in your preferred Java IDE.
3. Allow Maven to download the required dependencies.
4. Check the application configuration.
5. Run the Spring Boot application.

Alternatively, use the Maven Wrapper from the project root.

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

The exact frontend startup procedure depends on the React client files included in the project.

## 14. Learning Outcomes

Through this project, we developed practical knowledge of:

### Java and Spring Boot

- Spring Boot application development.
- REST API implementation.
- Backend application architecture.
- Dependency management using Maven.

### Database and Persistence

- Relational database concepts.
- Spring Data JPA.
- Hibernate ORM.
- Repository abstractions.
- JPQL queries.

### Data Engineering

- Spring Batch.
- CSV data processing.
- Data ingestion.
- Data transformation.
- Batch execution concepts.

### Full-Stack Development

- React.js frontend development.
- Backend and frontend integration.
- HTTP communication.
- JSON data exchange.
- Dynamic user interfaces.

### Cloud and Deployment

- AWS Elastic Beanstalk.
- Application deployment concepts.
- Cloud-based application hosting.

## 15. Acknowledgements

This project is based on the original IPL Dashboard application and educational materials created by **Koushik Kothagal (Java Brains)**.

We sincerely appreciate his work and the educational resources that helped us understand how to develop a full-stack application using Java, Spring Boot, React.js, Spring Batch, and database technologies.

**Original Creator:** Koushik Kothagal  
**Educational Platform:** Java Brains  
**Application:** IPL Dashboard  
**Source Repository:** https://github.com/koushikkothagal/ipl-dashboard

---

*This repository is maintained as part of our personal learning journey in Java backend and full-stack application development. Credit for the original project and educational materials belongs to their respective creator.*
