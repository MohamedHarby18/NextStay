# NextStay Microservices Architecture

## Overview
This document provides a comprehensive guide to the NextStay microservices architecture transformation from a monolithic Spring Boot application.

## Architecture Components

### 1. **Service Discovery (Eureka) - Port 8761**
- Central registry for all microservices
- Enables automatic service registration and discovery
- URL: `http://localhost:8761`

### 2. **API Gateway - Port 8080**
- Single entry point for all client requests
- Routes requests to appropriate microservices
- Handles cross-cutting concerns like rate limiting, logging
- Routes:
  - `/api/users/**` → User Service
  - `/api/properties/**` → Property Service
  - `/api/bookings/**` → Booking Service
  - `/api/payments/**` → Payment Service
  - `/api/notifications/**` → Notification Service

### 3. **User Service - Port 8081**
**Responsibilities:**
- User registration and authentication
- User profile management
- User data persistence

**Database:** `nextstay_user_db`

**Key Endpoints:**
- `POST /api/users/register` - Register new user
- `GET /api/users/{id}` - Get user details
- `GET /api/users/email/{email}` - Get user by email
- `PUT /api/users/{id}` - Update user profile
- `DELETE /api/users/{id}` - Delete user

### 4. **Property Service - Port 8082**
**Responsibilities:**
- Property listing management
- Property availability tracking
- Search properties by location, filters

**Database:** `nextstay_property_db`

**Key Endpoints:**
- `POST /api/properties` - Create property
- `GET /api/properties/{id}` - Get property details
- `GET /api/properties/host/{hostId}` - Get host's properties
- `GET /api/properties/available` - Get available properties
- `GET /api/properties/city/{city}` - Search by city
- `PUT /api/properties/{id}` - Update property
- `DELETE /api/properties/{id}` - Delete property

### 5. **Booking Service - Port 8083**
**Responsibilities:**
- Manage booking requests
- Track booking status (PENDING, CONFIRMED, CANCELLED, COMPLETED)
- Calculate booking costs

**Database:** `nextstay_booking_db`

**Key Endpoints:**
- `POST /api/bookings` - Create booking
- `GET /api/bookings/{id}` - Get booking details
- `GET /api/bookings/guest/{guestId}` - Get guest's bookings
- `GET /api/bookings/property/{propertyId}` - Get property's bookings
- `PUT /api/bookings/{id}/confirm` - Confirm booking
- `PUT /api/bookings/{id}/cancel` - Cancel booking

### 6. **Payment Service - Port 8084**
**Responsibilities:**
- Process payments
- Track payment status
- Support multiple payment methods

**Database:** `nextstay_payment_db`

**Key Endpoints:**
- `POST /api/payments` - Process payment
- `GET /api/payments/{id}` - Get payment status

### 7. **Notification Service - Port 8085**
**Responsibilities:**
- Send email notifications
- Send SMS notifications
- Event-driven communication

**Key Endpoints:**
- `POST /api/notifications/email` - Send email
- `POST /api/notifications/sms` - Send SMS

### 8. **Common Library**
Shared utilities across all microservices:
- DTOs (UserDTO, ApiResponse)
- Common constants
- Utility functions

## Prerequisites

1. **Java 17+**
2. **MySQL 8.0+**
3. **Maven 3.8+**

## Database Setup

Create these databases in MySQL:

```sql
CREATE DATABASE nextstay_user_db;
CREATE DATABASE nextstay_property_db;
CREATE DATABASE nextstay_booking_db;
CREATE DATABASE nextstay_payment_db;
```

## Configuration

### Update application.yml in each service

Replace `your_mysql_password` in each service's `application.yml`:

```yaml
spring:
  datasource:
    username: root
    password: your_mysql_password
```

## Running the Microservices

### Option 1: Run All Services

1. **Start Service Discovery (Eureka)**
   ```bash
   cd service-discovery
   mvn spring-boot:run
   ```
   Access: `http://localhost:8761`

2. **Start API Gateway**
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```
   Access: `http://localhost:8080`

3. **Start User Service**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```

4. **Start Property Service**
   ```bash
   cd property-service
   mvn spring-boot:run
   ```

5. **Start Booking Service**
   ```bash
   cd booking-service
   mvn spring-boot:run
   ```

6. **Start Payment Service**
   ```bash
   cd payment-service
   mvn spring-boot:run
   ```

7. **Start Notification Service**
   ```bash
   cd notification-service
   mvn spring-boot:run
   ```

### Option 2: Run from Parent Directory
```bash
mvn clean install
mvn -pl service-discovery spring-boot:run
mvn -pl api-gateway spring-boot:run
mvn -pl user-service spring-boot:run
# ... and so on
```

## Service Communication

### Inter-Service Communication with Feign

Services communicate using Spring Cloud OpenFeign:

```java
@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
```

## API Gateway Routing

The API Gateway uses Spring Cloud Gateway to route requests:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=2
```

- `lb://` uses load balancing from Eureka
- `StripPrefix=2` removes `/api/users` from the path before forwarding

## Testing the APIs

### Using cURL

**Register User:**
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "1234567890",
    "userType": "GUEST"
  }'
```

**Create Property:**
```bash
curl -X POST http://localhost:8080/api/properties \
  -H "Content-Type: application/json" \
  -d '{
    "hostId": 1,
    "title": "Beautiful Villa",
    "description": "A beautiful villa in the countryside",
    "address": "123 Main St",
    "city": "Miami",
    "country": "USA",
    "pricePerNight": 150.00,
    "maxGuests": 6,
    "bedrooms": 3,
    "bathrooms": 2,
    "propertyType": "VILLA"
  }'
```

**Create Booking:**
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "guestId": 1,
    "propertyId": 1,
    "checkInDate": "2026-06-01",
    "checkOutDate": "2026-06-07",
    "numberOfGuests": 4,
    "totalPrice": 900.00,
    "specialRequests": "Early check-in requested"
  }'
```

## Project Structure

```
nextstaymain/
├── pom-parent.xml                 # Parent POM with dependency management
├── common-lib/                    # Shared DTOs and utilities
│   ├── pom.xml
│   └── src/main/java/com/nextstay/common/
│       └── dto/
├── service-discovery/             # Eureka Server
│   ├── pom.xml
│   └── src/
├── api-gateway/                   # Spring Cloud Gateway
│   ├── pom.xml
│   └── src/
├── user-service/                  # User Management
│   ├── pom.xml
│   └── src/main/java/com/nextstay/userservice/
├── property-service/              # Property Management
│   ├── pom.xml
│   └── src/main/java/com/nextstay/propertyservice/
├── booking-service/               # Booking Management
│   ├── pom.xml
│   └── src/main/java/com/nextstay/bookingservice/
├── payment-service/               # Payment Processing
│   ├── pom.xml
│   └── src/main/java/com/nextstay/paymentservice/
└── notification-service/          # Notifications
    ├── pom.xml
    └── src/main/java/com/nextstay/notificationservice/
```

## Best Practices Implemented

1. **Service Independence** - Each service has its own database
2. **API Gateway Pattern** - Single entry point for clients
3. **Service Discovery** - Dynamic service registration with Eureka
4. **Resilience** - OpenFeign with fallback patterns
5. **Standardized Responses** - ApiResponse wrapper for consistency
6. **Common Utilities** - Shared code in common-lib
7. **Port Segregation** - Each service on unique port

## Future Enhancements

1. **Circuit Breaker Pattern** - Add Hystrix for fault tolerance
2. **Distributed Tracing** - Implement Sleuth & Zipkin
3. **Config Server** - Spring Cloud Config for centralized configuration
4. **Message Queue** - RabbitMQ/Kafka for async communication
5. **Authentication** - JWT & Spring Security
6. **Containerization** - Docker & Docker Compose
7. **Monitoring** - Prometheus & Grafana
8. **API Documentation** - Swagger/Springdoc OpenAPI

## Troubleshooting

**Services not registering with Eureka:**
- Ensure `@EnableDiscoveryClient` is present in main class
- Check Eureka server is running on port 8761
- Verify network connectivity

**API Gateway not routing requests:**
- Check service names match in routes configuration
- Verify services are registered in Eureka
- Check API Gateway is running on port 8080

**Database connection errors:**
- Verify MySQL is running
- Check database credentials in application.yml
- Ensure databases exist

## Support & Documentation

- Spring Cloud: https://spring.io/projects/spring-cloud
- Spring Boot: https://spring.io/projects/spring-boot
- Eureka: https://cloud.spring.io/spring-cloud-netflix/
- API Gateway: https://spring.io/projects/spring-cloud-gateway
