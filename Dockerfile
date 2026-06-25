FROM eclipse-temurin:17-jdk-alpine
LABEL authors="crist"
ARG JAR_FILE=target/Ejercicio08-Reservaciones-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} reservaciones.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "reservaciones.jar"]
