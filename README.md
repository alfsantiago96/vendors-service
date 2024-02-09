


## vendors-service
##### 

Here you will find:

* Objective
* Running documentation
* Assessment evaluation hints
___

### Objective
Be able to look for suitable vendors in the provided region, once choosen,
you can send a hire intention, providing its information.

* Potential vendors: /v1/vendors/jobs
* Hire a Job: /v1/jobs

In addition, you que get statistics about de total vendors available for
the given job.

* Vendors statistics: /v1/vendors/statistics

___
### Runing the application

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

### Assessment Evaluation Hints

To evaluate the assessment with the same examples given in its description
, you can use the fallowing api resource to create the vendors stubs.

Create stubs: /database