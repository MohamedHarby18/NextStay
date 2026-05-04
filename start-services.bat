@echo off
REM Start all NextStay Microservices

echo ===================================
echo NextStay Microservices Startup
echo ===================================
echo.

REM Start Service Discovery
echo [1/7] Starting Service Discovery (Eureka) on port 8761...
start cmd /k "cd service-discovery && mvn spring-boot:run"
timeout /t 5 /nobreak

REM Start API Gateway
echo [2/7] Starting API Gateway on port 8080...
start cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 3 /nobreak

REM Start User Service
echo [3/7] Starting User Service on port 8081...
start cmd /k "cd user-service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM Start Property Service
echo [4/7] Starting Property Service on port 8082...
start cmd /k "cd property-service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM Start Booking Service
echo [5/7] Starting Booking Service on port 8083...
start cmd /k "cd booking-service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM Start Payment Service
echo [6/7] Starting Payment Service on port 8084...
start cmd /k "cd payment-service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM Start Notification Service
echo [7/7] Starting Notification Service on port 8085...
start cmd /k "cd notification-service && mvn spring-boot:run"

echo.
echo ===================================
echo All services started!
echo ===================================
echo.
echo Service URLs:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - User Service: http://localhost:8081
echo - Property Service: http://localhost:8082
echo - Booking Service: http://localhost:8083
echo - Payment Service: http://localhost:8084
echo - Notification Service: http://localhost:8085
echo.
echo Press any key to continue...
pause
