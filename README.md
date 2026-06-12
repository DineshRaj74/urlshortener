# URL Shortener + Analytics API

A Spring Boot REST API to create short URLs, redirect users, and track click analytics.

## Tech Stack

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- MySQL
- H2 for local quick testing
- Docker

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | Check app status |
| POST | `/api/shorten` | Create a short URL |
| GET | `/{shortCode}` | Redirect to original URL |
| GET | `/api/stats/{shortCode}` | Get analytics |
| GET | `/api/urls` | List all URLs |

## Create Short URL

```json
POST /api/shorten
Content-Type: application/json

{
  "originalUrl": "https://example.com"
}
```

## Local Run

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080/api/health
```

## MySQL Environment Variables

For Render or production, add these environment variables:

```text
DB_URL=jdbc:mysql://HOST:PORT/DATABASE_NAME?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
APP_BASE_URL=https://your-render-app.onrender.com
```

## Docker Build

```bash
docker build -t url-shortener .
docker run -p 8080:8080 url-shortener
```
