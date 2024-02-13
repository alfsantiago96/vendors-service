


## vendors-service
##### 

Here you will find:

1. [Objective](###Objective)
2. [Runing the application](###Runing the application)
3. [Assessment Evaluation Hint](###Assessment Evaluation Hint)
___

### Objective
Be able to look for suitable vendors for the provided region, once choosen,
you can create a hire intention, providing the vendor information.

* Potential vendors: /v1/vendors/jobs
* Hire a Job: /v1/jobs

In addition, you que get statistics about de total vendors available for
the given job.

* Vendors statistics: /v1/vendors/statistics

___
### Runing the application

* If you want to run it from a docker Image

repository: https://hub.docker.com/r/alfsantiago96/vendors-service

command: docker pull alfsantiago96/vendors-service

command:  docker run -p 8080:8080 alfsantiago96/vendors-service

The application should be running at:
* Public Page: http://localhost:8080
* Swagger: http://localhost:8080/swagger-ui/index.html

___
##### Basic auth:

###### Create Vendor (v1/vendors)
* username: vendor
* password: 123

###### Hire a Job (v1/jobs)
* username: job
* password: 123

###### Other resources
* username: andre
* password: 123

###### Master
* username: vs_tech_challenge
* password: SuperSecurePassword123@

___
### Assessment Evaluation Hints

The application already starts with the exact Vendors examples given in the assessment description
, if you want so, you can use the fallowing API resource to delete all the vendors stubs, and create them by your own.

Create/Delete stubs: localhost:8080/database
___

### Alternative ways to run the application
* If you want to run it from terminal

Pre requirements on your machine
* Maven
* Java 17

Get to the application folder, and execute the fallowing commands.

command: install -f pom.xml

command: mvn spring-boot:run

___
* If you want to generate a new docker image.

Pre requirements on your machine
* Maven
* Java 17
* Docker

Get to the application folder, and execute the fallowing commands.

install -f pom.xml

mvn dependency:resolve

mvn package -f pom.xml

docker build -t vendors-service .

docker run -p 8080:8080 vendors-service
___