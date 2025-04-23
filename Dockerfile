FROM openjdk:21-ea-1-jdk

COPY target/BeeNice_API-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]