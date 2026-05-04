FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/booking-service-1.0.0.jar booking-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "booking-service.jar"]
