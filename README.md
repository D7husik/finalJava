# 🚗 SafeTravels - Travel Safety Backend System

A comprehensive Spring Boot backend application for ensuring traveler safety through real-time ride tracking, SOS alerts, and trusted contact notifications.

---

## 📋 Table of Contents

- [Project Description](#project-description)
- [Main Features](#main-features)
- [Tech Stack](#tech-stack)
- [API Documentation](#api-documentation)
- [How to Run Locally](#how-to-run-locally)
- [API Endpoints](#api-endpoints)
- [Architecture Overview](#architecture-overview)
- [Design Patterns Used](#design-patterns-used)
- [Known Limitations](#known-limitations)
- [Project Summary](#project-summary)

---

## 📖 Project Description

**SafeTravels** is a backend system designed to enhance personal safety during travel, particularly for ride-sharing scenarios. The application allows users to:

- Register trusted contacts who can be notified in emergencies
- Track ride journeys with real-time location updates
- Trigger SOS alerts that automatically notify trusted contacts
- Maintain a history of rides and safety events

### Problem It Solves

Many travelers, especially those using ride-sharing services, face safety concerns during their journeys. SafeTravels provides a safety net by enabling users to share their ride details with trusted contacts and quickly alert them in case of emergencies.

---

## ✨ Main Features

| Feature | Description |
|---------|-------------|
| 🔐 **User Authentication** | JWT-based secure authentication with registration and login |
| 👥 **Trusted Contacts** | Manage emergency contacts who receive SOS notifications |
| 🚗 **Ride Management** | Create, start, and complete ride tracking sessions |
| 📍 **Location Tracking** | Real-time GPS location updates during active rides |
| 🆘 **SOS Alerts** | Emergency button that notifies all trusted contacts |
| 🔔 **Notifications** | Multi-channel notification system (Console, SMS, In-App) |

---

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.0 |
| **Security** | Spring Security + JWT (jjwt 0.12.3) |
| **Database** | PostgreSQL |
| **ORM** | Spring Data JPA + Hibernate 6 |
| **Build Tool** | Maven |
| **Code Generation** | Lombok, MapStruct |
| **Validation** | Jakarta Validation |

---

## 📚 API Documentation

This project exposes a **RESTful API** for all operations.

### External APIs Used

| API | Purpose | Documentation |
|-----|---------|---------------|
| PostgreSQL JDBC | Database connectivity | [PostgreSQL Docs](https://jdbc.postgresql.org/documentation/) |
| JWT (JSON Web Tokens) | Authentication tokens | [JWT.io](https://jwt.io/introduction) |

---

## 🚀 How to Run Locally

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 14+**
- **IDE**: IntelliJ IDEA (recommended)

### Step 1: Clone the Repository

```bash
git clone https://github.com/your-username/safetravels-backend.git
cd safetravels-backend
```

### Step 2: Create PostgreSQL Database

Open pgAdmin or psql and run:

```sql
CREATE DATABASE safetravels;
```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/safetravels
    username: postgres
    password: YOUR_PASSWORD  # Change this!
```

### Step 4: Build and Run

```bash
# Using Maven
mvn clean install -DskipTests
mvn spring-boot:run

# Or using IDE
# Run SafeTravelsApplication.java
```

### Step 5: Verify

Open: `http://localhost:8080`

The server is running when you see:
```
Started SafeTravelsApplication in X.XXX seconds
Tomcat started on port(s): 8080
```

---

## 📡 API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### User Profile (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/me` | Get current user profile |
| PUT | `/api/users/me` | Update user profile |

### Trusted Contacts (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/me/trusted-contacts` | Add trusted contact |
| GET | `/api/users/me/trusted-contacts` | List all contacts |
| DELETE | `/api/users/me/trusted-contacts/{id}` | Remove contact |

### Rides (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/rides` | Create new ride |
| PUT | `/api/rides/{id}/start` | Start ride |
| PUT | `/api/rides/{id}/end` | End ride |
| GET | `/api/rides/{id}` | Get ride details |
| GET | `/api/rides` | List all user rides |
| POST | `/api/rides/{id}/location` | Update location |

### SOS (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/sos` | Trigger SOS alert |
| GET | `/api/sos` | List user's SOS events |
| GET | `/api/sos/{id}` | Get SOS event details |

### Notifications (Public - for testing)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/notifications/simulate` | Simulate notification |

---

## 🏗 Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                      SafeTravels Backend                     │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ Controllers │──│  Services   │──│    Repositories     │  │
│  │   (REST)    │  │  (Business) │  │      (JPA)          │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
│         │               │                    │              │
│         ▼               ▼                    ▼              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │    DTOs     │  │  Patterns   │  │      Entities       │  │
│  │ (Request/   │  │ (Strategy,  │  │   (User, Ride,      │  │
│  │  Response)  │  │  Observer,  │  │    Location, etc.)  │  │
│  └─────────────┘  │  Builder,   │  └─────────────────────┘  │
│                   │  Factory)   │            │              │
│                   └─────────────┘            ▼              │
│  ┌─────────────┐                   ┌─────────────────────┐  │
│  │  Security   │                   │     PostgreSQL      │  │
│  │ (JWT Auth)  │                   │     Database        │  │
│  └─────────────┘                   └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Main Modules

| Module | Responsibility |
|--------|---------------|
| `controller/` | REST API endpoints, request handling |
| `service/` | Business logic, transaction management |
| `repository/` | Data access layer, JPA queries |
| `entity/` | Database entities (User, Ride, Location, etc.) |
| `dto/` | Request/Response data transfer objects |
| `security/` | JWT authentication, filters, config |
| `patterns/` | Design pattern implementations |
| `exception/` | Global exception handling |
| `mapper/` | Entity-DTO mapping |

---

## 🎨 Design Patterns Used

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **Strategy** | `NotificationStrategy` | Multiple notification channels (SMS, Console, In-App) |
| **Observer** | `SosEventListener` | Notify trusted contacts on SOS events |
| **Builder** | `SosAlertBuilder` | Construct complex SOS alert objects |
| **Factory** | `NotificationFactory` | Create appropriate notification handlers |

### Example: Strategy Pattern

```java
public interface NotificationStrategy {
    void send(String recipient, String message);
}

// Implementations
- ConsoleNotificationStrategy
- MockSmsNotificationStrategy
- InAppNotificationStrategy
```

---

## ⚠️ Known Limitations

| Limitation | Description |
|------------|-------------|
| **SMS Integration** | Uses mock SMS (no real SMS gateway) |
| **Real-time Updates** | No WebSocket support (uses polling) |
| **File Uploads** | No image/file upload for profiles |
| **Email Verification** | No email verification on registration |
| **Password Reset** | No forgot password functionality |
| **Rate Limiting** | No API rate limiting implemented |
| **Pagination** | Limited pagination on list endpoints |

---

## 📄 Project Summary

### Problem Statement

Travelers, especially those using ride-sharing services, often face safety concerns during their journeys. There's a need for a reliable system that can:
- Track rides in real-time
- Alert emergency contacts quickly
- Maintain journey history for accountability

### Solution

SafeTravels provides a comprehensive backend system that enables:
1. **Ride Tracking**: Users can log ride details and track their journey
2. **Emergency Alerts**: One-tap SOS that notifies all trusted contacts
3. **Contact Management**: Easy management of emergency contacts
4. **Audit Trail**: Complete history of rides and SOS events

### Why This Architecture?

- **Spring Boot**: Rapid development with production-ready features
- **JWT Authentication**: Stateless, scalable security
- **PostgreSQL**: Reliable, ACID-compliant database
- **Layered Architecture**: Clean separation of concerns
- **Design Patterns**: Extensible, maintainable code

### Technical Challenges & Solutions

| Challenge | Solution |
|-----------|----------|
| **Secure Authentication** | Implemented JWT with Spring Security filters |
| **Multiple Notification Channels** | Used Strategy pattern for extensibility |
| **SOS Event Propagation** | Implemented Observer pattern for notifications |
| **Complex Object Creation** | Used Builder pattern for SOS alerts |
| **Database Relationships** | Proper JPA mappings with cascade operations |

---

## 👥 Team

- **Developer**: Alisher Dzhusuev
- **Course**: OOP with Java
- **University**: University of Central Asia

---

## 📜 License

This project is created for educational purposes.

---

## 🔗 Links

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JWT Introduction](https://jwt.io/introduction)
