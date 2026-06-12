# todolist-api

A Spring Boot REST API for managing todo lists with user authentication. This application allows users to create accounts, authenticate, and manage their tasks efficiently.

## 🚀 Features

- **User Management**: User registration and authentication with bcrypt password hashing
- **Task Management**: Full CRUD operations for tasks with user association
- **Authentication Filter**: Request-level authentication for protected endpoints
- **Error Handling**: Centralized exception handling for consistent API responses
- **H2 Database**: In-memory database with web console for easy development and testing
- **Docker Support**: Multi-stage Docker build for containerized deployment

## 🛠 Tech Stack

- **Framework**: Spring Boot 3.4.9
- **Language**: Java 17
- **Database**: H2 (in-memory)
- **Build Tool**: Maven
- **Security**: BCrypt (at.favre.lib)
- **ORM**: Spring Data JPA & Hibernate
- **Additional Libraries**:
  - Lombok (code generation)
  - Spring Boot Actuator (monitoring)
  - Spring Boot DevTools (development)

## 📋 Project Structure

```
src/main/java/br/com/victorpacheco/todolist/
├── TodolistApplication.java              # Spring Boot entry point
├── task/                                  # Task management module
│   ├── TaskModel.java                    # Task JPA Entity
│   ├── TaskController.java               # Task REST endpoints
│   └── ITaskRepository.java              # Task data access layer
├── user/                                  # User management module
│   ├── UserModel.java                    # User JPA Entity
│   ├── UserController.java               # User REST endpoints
│   └── IUserRepository.java              # User data access layer
├── filter/                                # Security & Authentication
│   └── FilterTaskAuth.java               # Request authentication filter
├── errors/                                # Error handling
│   └── ExceptionHandlerController.java   # Global exception handler
└── utils/                                 # Utility functions
    └── Utils.java                        # Helper methods
```

## 🗄 Database Configuration

The application uses an H2 in-memory database configured as follows:

- **Database URL**: `jdbc:h2:mem:todolist`
- **Driver**: `org.h2.Driver`
- **Username**: `admin`
- **Password**: `admin`
- **H2 Console**: Enabled at `/h2-console`

## 📦 Installation & Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (optional, for containerized deployment)

### Running Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/vpachecooo/todolist-api.git
   cd todolist-api
   ```

2. **Build the project**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

   The API will be available at `http://localhost:8080`

### Running with Docker

1. **Build the Docker image**:
   ```bash
   docker build -t todolist-api:1.0.0 .
   ```

2. **Run the container**:
   ```bash
   docker run -p 8080:8080 todolist-api:1.0.0
   ```

   The API will be available at `http://localhost:8080`

## 🔌 API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/users` | Create a new user (register) |
| `GET` | `/users` | Get user information |

### Task Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/tasks` | Create a new task |
| `GET` | `/tasks` | List all tasks for authenticated user |
| `PUT` | `/tasks/{id}` | Update an existing task |
| `DELETE` | `/tasks/{id}` | Delete a task |

### Monitoring

| Endpoint | Description |
|----------|-------------|
| `/actuator` | Spring Boot Actuator endpoints |
| `/h2-console` | H2 Database console (development only) |

## 🔐 Authentication

The API uses HTTP Basic Authentication with request-level filtering:

- Protected endpoints are intercepted by `FilterTaskAuth`
- User credentials are validated against the database
- Passwords are hashed using BCrypt for security

Include your credentials in requests:
```bash
curl -u username:password http://localhost:8080/tasks
```

## 📝 Example Usage

### Register a User

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "secure_password"
  }'
```

### Create a Task

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -u john_doe:secure_password \
  -d '{
    "title": "Complete project",
    "description": "Finish the todolist-api project",
    "priority": "HIGH"
  }'
```

### Get All Tasks

```bash
curl -u john_doe:secure_password http://localhost:8080/tasks
```

## 📄 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

**Victor Pacheco** - [GitHub Profile](https://github.com/vpachecooo)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

**Created**: October 6, 2025  
**Last Updated**: June 12, 2026
