FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/notification-service-1.0.0.jar notification-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "notification-service.jar"]
