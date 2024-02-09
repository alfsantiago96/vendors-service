


## vendors-service
##### This is an assessment for https://gist.github.com/mvsouza/5eca9515b180a04293b62bd11819cc96


___
* If you want to run it from a docker Image

command: docker run -p 8080:8080 vendors-service

The application should be up at http://localhost:8080/app/swagger-ui/index.html

___
* If you want to run it from terminal

Pre requirements on your machine
* Maven
* Java 17

Get to the application folder, and execute the fallowing commands.

command: mvn spring-boot:run

___
* If you want to generate a new docker image.

Pre requirements on your machine
* Maven
* Java 17
* Docker

Get to the application folder, and execute the fallowing commands.

mvn package -f pom.xml

docker build -t vendors-service .

docker run -p 8080:8080 vendors-service
___