# Emergency Resource Network

A complete Java Spring Boot + H2 + HTML/CSS/JavaScript full-stack emergency coordination project.

## Features
- Emergency help request creation
- Resource inventory and filtering
- Blood, medicine, food, water, shelter, ambulance and equipment categories
- Volunteer registration and availability
- Coordinator/admin login
- Admin request dashboard
- Resource creation from admin panel
- Emergency alert broadcasting
- Resource-to-request matching API
- Volunteer-to-request assignment API
- Request status workflow
- Persistent file-based H2 database
- H2 console
- Responsive frontend

## Requirements
- Java 17+
- Maven 3.9+ (or a working Maven wrapper if you add one)

## Run
```bash
mvn clean spring-boot:run
```

Open:
http://localhost:8080

H2 console:
http://localhost:8080/h2-console

JDBC URL:
jdbc:h2:file:./data/emergencydb
User: sa
Password: (empty)

## Demo admin
Email: admin@emergency.local
Password: admin123

## Main API
GET    /api/dashboard
GET    /api/resources
POST   /api/resources
PUT    /api/resources/{id}
DELETE /api/resources/{id}
GET    /api/requests
POST   /api/requests
PATCH  /api/requests/{id}/status?status=FULFILLED
POST   /api/requests/{requestId}/reserve/{resourceId}
POST   /api/requests/{requestId}/assign/{volunteerId}
GET    /api/volunteers
POST   /api/volunteers
PATCH  /api/volunteers/{id}/availability?available=true
GET    /api/alerts
POST   /api/alerts
PATCH  /api/alerts/{id}/close
POST   /api/login

## Important
This is an academic/demo emergency coordination application. It is not a replacement for official emergency services. For a real deployment, add authentication/security, audit logging, encryption, HTTPS, rate limiting, verified provider workflows, geospatial services, SMS/push notifications, and production database backups.
