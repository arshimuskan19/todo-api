# Todo API

A RESTful Todo application backend built with Spring Boot and PostgreSQL.

## Features
- User registration and login with JWT authentication
- Password hashing with BCrypt
- Secure todo management (users can only access their own todos)
- Input validation and custom exception handling
- Protected endpoints with Spring Security

## Tech Stack
- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- PostgreSQL
- JPA/Hibernate
- Maven

## API Endpoints

### Auth (Public)
- POST /api/auth/register — Register new user
- POST /api/auth/login — Login and get JWT token

### Todos (Requires JWT token)
- GET /api/todos — Get all my todos
- POST /api/todos — Create a todo
- PUT /api/todos/{id} — Update a todo
- DELETE /api/todos/{id} — Delete a todo

## Running Locally
1. Clone the repository
2. Create a PostgreSQL database called `todo_db`
3. Set environment variables: DB_URL, DB_USERNAME, DB_PASSWORD
4. Run `mvn spring-boot:run`
