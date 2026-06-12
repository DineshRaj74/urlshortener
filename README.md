# URL Shortener & Analytics API

A production-ready URL Shortener application built using Spring Boot that allows users to shorten long URLs, track click analytics, and manage links through a responsive frontend interface.

## Live Demo

https://url-shortener-55d8.onrender.com

---

## Features

* Shorten long URLs instantly
* Redirect users using generated short links
* Track URL click analytics
* RESTful API architecture
* Responsive frontend using HTML, CSS, and JavaScript
* Cloud deployment using Render
* Spring Data JPA integration
* H2 database support

---

## Tech Stack

### Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* Maven

### Frontend

* HTML5
* CSS3
* JavaScript

### Database

* H2 Database

### Deployment

* Render

---

## API Endpoints

### Health Check

GET

```http
/api/health
```

### Create Short URL

POST

```http
/api/shorten
```

Request Body:

```json
{
  "originalUrl": "https://example.com"
}
```

### Get URL Statistics

GET

```http
/api/stats/{shortCode}
```

### Get All URLs

GET

```http
/api/urls
```

---

## Project Structure

```text
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   └── dto
│   │
│   └── resources
│       ├── static
│       │   ├── index.html
│       │   ├── style.css
│       │   └── app.js
│       └── application.properties
```

---

## Screenshots

Add screenshots of:

* Homepage UI
* URL shortening feature
* Analytics feature

---

## Learning Outcomes

* REST API Development
* Spring Boot Application Design
* Frontend and Backend Integration
* Database Management
* Cloud Deployment
* Git & GitHub Workflow

---

## Author

Dinesh Raj S

GitHub:
https://github.com/DineshRaj74

Portfolio:
https://dineshraj74.github.io/Portfolio-/

LinkedIn:
Add your LinkedIn profile link here

---

⭐ If you found this project useful, consider giving it a star.
