# NextStay Microservices - Quick Start Guide

## 🚀 Quick Start

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 2. Database Setup
```sql
CREATE DATABASE nextstay_user_db;
CREATE DATABASE nextstay_property_db;
CREATE DATABASE nextstay_booking_db;
CREATE DATABASE nextstay_payment_db;
```

### 3. Update Configuration
Edit each service's `application.yml` and replace:
```yaml
password: your_mysql_password
```

### 4. Build All Services
```bash
mvn clean install
```

### 5. Start Services

**Option A: Using Batch Script (Windows)**
```bash
./start-services.bat
```

**Option B: Using Shell Script (Linux/Mac)**
```bash
chmod +x start-services.sh
./start-services.sh
```

**Option C: Docker Compose**
```bash
docker-compose up -d
```

**Option D: Manual Start** (start each in separate terminal)
```bash
cd service-discovery && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd property-service && mvn spring-boot:run
cd booking-service && mvn spring-boot:run
cd payment-service && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
```

## 📊 Service Health Check

Visit Eureka Dashboard to verify all services are registered:
```
http://localhost:8761
```

## 🔗 API Endpoints (via API Gateway)

### User Service
```
POST   http://localhost:8080/api/users/register
GET    http://localhost:8080/api/users/{id}
GET    http://localhost:8080/api/users
PUT    http://localhost:8080/api/users/{id}
DELETE http://localhost:8080/api/users/{id}
```

### Property Service
```
POST   http://localhost:8080/api/properties
GET    http://localhost:8080/api/properties/{id}
GET    http://localhost:8080/api/properties/available
GET    http://localhost:8080/api/properties/city/{city}
PUT    http://localhost:8080/api/properties/{id}
DELETE http://localhost:8080/api/properties/{id}
```

### Booking Service
```
POST   http://localhost:8080/api/bookings
GET    http://localhost:8080/api/bookings/{id}
GET    http://localhost:8080/api/bookings/guest/{guestId}
PUT    http://localhost:8080/api/bookings/{id}/confirm
PUT    http://localhost:8080/api/bookings/{id}/cancel
```

### Payment Service
```
POST   http://localhost:8080/api/payments
GET    http://localhost:8080/api/payments/{id}
```

### Notification Service
```
POST   http://localhost:8080/api/notifications/email
POST   http://localhost:8080/api/notifications/sms
```

## 📝 Example API Calls

### Register User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "1234567890",
    "userType": "GUEST"
  }'
```

### Create Property
```bash
curl -X POST http://localhost:8080/api/properties \
  -H "Content-Type: application/json" \
  -d '{
    "hostId": 1,
    "title": "Luxury Beach Villa",
    "description": "Beautiful villa with ocean view",
    "address": "123 Beach St",
    "city": "Miami",
    "country": "USA",
    "pricePerNight": 200.00,
    "maxGuests": 8,
    "bedrooms": 4,
    "bathrooms": 3,
    "propertyType": "VILLA"
  }'
```

### Create Booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "guestId": 1,
    "propertyId": 1,
    "checkInDate": "2026-06-15",
    "checkOutDate": "2026-06-22",
    "numberOfGuests": 4,
    "totalPrice": 1400.00,
    "specialRequests": "Late check-in needed"
  }'
```

## 🛑 Stop Services

### Stop All (Ctrl+C in each terminal)
or

### Using Docker
```bash
docker-compose down
```

## 📚 Project Structure

```
nextstaymain/
├── pom-parent.xml                      # Parent POM
├── common-lib/                         # Shared utilities & DTOs
├── service-discovery/                  # Eureka Server (8761)
├── api-gateway/                        # API Gateway (8080)
├── user-service/                       # User Service (8081)
├── property-service/                   # Property Service (8082)
├── booking-service/                    # Booking Service (8083)
├── payment-service/                    # Payment Service (8084)
└── notification-service/               # Notification Service (8085)
```

## 🐛 Troubleshooting

**Services not registering?**
- Check Eureka is running on port 8761
- Verify `@EnableDiscoveryClient` in app classes

**Database connection errors?**
- Verify MySQL is running
- Check credentials in application.yml
- Ensure databases are created

**API Gateway not routing?**
- Verify all services are registered in Eureka
- Check service names in gateway routes

## 📖 Detailed Documentation

See `MICROSERVICES_ARCHITECTURE.md` for comprehensive architecture details.

## 🔧 Technologies Used

- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Spring Cloud Gateway
- Netflix Eureka
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## ✨ Features

✅ Service Discovery with Eureka
✅ API Gateway with routing
✅ Separate databases per service
✅ RESTful APIs
✅ Standardized API responses
✅ Docker & Docker Compose support
✅ Easy startup scripts
✅ Comprehensive documentation
