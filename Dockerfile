FROM eclipse-temurin:21.0.2_13-jdk-alpine
LABEL authors="nicol"
COPY target/linea1-service-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java","-jar","/java-app.jar"]