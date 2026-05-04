# Build script to create Docker images

@echo off

echo ===================================
echo Building NextStay Microservices
echo ===================================
echo.

REM Build parent POM
echo [1] Building parent POM...
mvn clean install -DskipTests -f pom-parent.xml

REM Build each service and create Docker image
echo [2] Building service-discovery...
cd service-discovery
mvn clean package -DskipTests
docker build -t nextstay/service-discovery:latest .
cd ..

echo [3] Building api-gateway...
cd api-gateway
mvn clean package -DskipTests
docker build -t nextstay/api-gateway:latest .
cd ..

echo [4] Building user-service...
cd user-service
mvn clean package -DskipTests
docker build -t nextstay/user-service:latest .
cd ..

echo [5] Building property-service...
cd property-service
mvn clean package -DskipTests
docker build -t nextstay/property-service:latest .
cd ..

echo [6] Building booking-service...
cd booking-service
mvn clean package -DskipTests
docker build -t nextstay/booking-service:latest .
cd ..

echo [7] Building payment-service...
cd payment-service
mvn clean package -DskipTests
docker build -t nextstay/payment-service:latest .
cd ..

echo [8] Building notification-service...
cd notification-service
mvn clean package -DskipTests
docker build -t nextstay/notification-service:latest .
cd ..

echo.
echo ===================================
echo Build Complete!
echo ===================================
echo.
echo Docker images created:
echo - nextstay/service-discovery:latest
echo - nextstay/api-gateway:latest
echo - nextstay/user-service:latest
echo - nextstay/property-service:latest
echo - nextstay/booking-service:latest
echo - nextstay/payment-service:latest
echo - nextstay/notification-service:latest
echo.
echo Run: docker-compose up -d
echo.
pause
