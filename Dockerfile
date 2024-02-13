FROM openjdk

WORKDIR /app

COPY target/vendors-service.jar /app/vendors-service.jar

ENTRYPOINT ["java", "-jar", "vendors-service.jar"]
EXPOSE 8080