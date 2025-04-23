FROM openjdk:21-ea-1-jdk

COPY out/artifacts/BeeNice_API_jar/BeeNice_API.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]