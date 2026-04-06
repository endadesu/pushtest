# Mem App

A simple todo application with a React frontend and Spring Boot backend, persisted with PostgreSQL.

## Tech Stack

- **Frontend**: React 19, Vite
- **Backend**: Spring Boot 3.4, Spring Data JPA
- **Database**: PostgreSQL 16 (Docker)

## Getting Started

```bash
# Start PostgreSQL
docker compose up -d

# Start the backend (port 5000)
cd backend && ./mvnw spring-boot:run

# Start the frontend (in another terminal)
cd client && npm run dev
```
