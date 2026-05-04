FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/property-service-1.0.0.jar property-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "property-service.jar"]
