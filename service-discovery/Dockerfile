FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/service-discovery-1.0.0.jar service-discovery.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-discovery.jar"]
