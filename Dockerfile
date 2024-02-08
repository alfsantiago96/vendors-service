FROM openjdk

WORKDIR /app

COPY target/vendors-service-0.0.1-SNAPSHOT.jar /app/vendors-service.jar

ENTRYPOINT ["java", "-jar", "vendors-service.jar"]