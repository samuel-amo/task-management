# Task Management Application

A robust task management system built with Spring Boot that provides user authentication and comprehensive task management capabilities. The application features JWT-based authentication and uses MySQL for data persistence.

## Features

### User Management
- **User Registration**: Supports email validation.
- **Secure Login**: Implements JWT-based authentication.
- **Password Encryption**: Ensures secure password storage.

### Task Management
- **CRUD Operations**: Create, read, update, and delete tasks.
- **Advanced Filtering**: Filter tasks by status, priority, and deadline.
- **Task Prioritization**: Priority levels include LOW, MEDIUM, and HIGH.
- **Task Status Tracking**: Track task statuses as PENDING, IN_PROGRESS, or COMPLETED.

## Technologies
- **Programming Language**: Java 21
- **Framework**: Spring Boot
- **Security**: Spring Security with JWT
- **ORM**: JPA & Hibernate
- **Database**: MySQL
- **Build Tool**: Maven

## Prerequisites
Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 21
- **Maven**: Version 3.6 or higher
- **MySQL**: Version 8.0 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code
- **Postman** (optional, for API testing)

## Setup and Installation

### 1. Clone the Repository
```bash
# Clone the repository
https://github.com/your-repository/task-management.git
cd task-management
```

### 2. Configure the Database
1. Create a MySQL database.
2. Update the `application.properties` or `application.yml` file with your database details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run the Application

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

### 4. Access the Application
- The application runs on `http://localhost:8080` by default.
- Use Postman or any other API testing tool to test the endpoints.

## API Endpoints

### Authentication
- **POST** `/api/v1/auth/register`: Register a new user.
- **POST** `/api/v1/auth/login`: Authenticate a user and receive a JWT token.

### Tasks
- **GET** `/api/v1/tasks`: Retrieve all tasks.
- **POST** `/api/v1/tasks`: Create a new task.
- **PUT** `/api/v1/tasks/{id}`: Update an existing task.
- **DELETE** `/api/v1/tasks/{id}`: Delete a task.
- **GET** `/api/v1/tasks/filter`: Filter tasks by status, priority, or deadline.

## Additional Notes
- Ensure that the JWT token is included in the `Authorization` header for all secured endpoints.
- Example header:

```http
Authorization: Bearer your_jwt_token
```

