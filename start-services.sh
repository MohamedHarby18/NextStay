#!/bin/bash

# Start all NextStay Microservices

echo "==================================="
echo "NextStay Microservices Startup"
echo "==================================="
echo ""

# Start Service Discovery
echo "[1/7] Starting Service Discovery (Eureka) on port 8761..."
cd service-discovery
mvn spring-boot:run &
EUREKA_PID=$!
sleep 5

# Start API Gateway
echo "[2/7] Starting API Gateway on port 8080..."
cd ../api-gateway
mvn spring-boot:run &
GATEWAY_PID=$!
sleep 3

# Start User Service
echo "[3/7] Starting User Service on port 8081..."
cd ../user-service
mvn spring-boot:run &
USER_PID=$!
sleep 3

# Start Property Service
echo "[4/7] Starting Property Service on port 8082..."
cd ../property-service
mvn spring-boot:run &
PROPERTY_PID=$!
sleep 3

# Start Booking Service
echo "[5/7] Starting Booking Service on port 8083..."
cd ../booking-service
mvn spring-boot:run &
BOOKING_PID=$!
sleep 3

# Start Payment Service
echo "[6/7] Starting Payment Service on port 8084..."
cd ../payment-service
mvn spring-boot:run &
PAYMENT_PID=$!
sleep 3

# Start Notification Service
echo "[7/7] Starting Notification Service on port 8085..."
cd ../notification-service
mvn spring-boot:run &
NOTIFICATION_PID=$!

echo ""
echo "==================================="
echo "All services started!"
echo "==================================="
echo ""
echo "Service URLs:"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- User Service: http://localhost:8081"
echo "- Property Service: http://localhost:8082"
echo "- Booking Service: http://localhost:8083"
echo "- Payment Service: http://localhost:8084"
echo "- Notification Service: http://localhost:8085"
echo ""

# Wait for all processes
wait
