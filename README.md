# Person Service

Persons Service is Spring Boot based service which can be used for  storing, updating,
retrieving and deleting Person the details of a Person.This service can be easily deployed as docker Image


Tech Stack
------------

* Programming Langauge : Java 11
* FrameWork : Spring Boot 2.4.2
* Documentation : Swagger(Open Api)
* Testing Framework: JUnit
* Database : HSQL
* DataBase Versioning : Flyway
* Authentication Mechanism : Basic Auth
* Buld Framwork :Gradle
* PlatformTools: Docker,Linux Based Image

Build Steps
------------
Note: you should have Docker Installed in you Build Machine
::
Please find the simple steps build the Image and run the service in your Local Machine

    > git clone https://github.com/fazeem84/person_service.git
    > cd person_service
    > docker build -t ${Image_Name} . eg: docker build -t person-service .
    > docker run -p {HostPort}:8080 person-service . eg: docker build -t person-service .
    

Accessing API
-------------
Swagger Documentation is Available with the deployed Service.
Users Can Test the api through Swagger Portal .
Swagger portal can be access through browser by accessing urls: 
* http://{HostName}:{HostPort}/swagger-ui.html 
* eg: http://localhost:8080/swagger-ui.html

All the write operations are protected by BasicAuthentication, Basic Authentication Implemented by MemoryAuthentication

Limitations/TODO
----------------
* Unique constraints(combination of firstName,lastName and age)  Implemented in DB side but the proper handling of DuplicateConstraint Exception need to be added
* Build can be Refactored to support multi environment support so that  one image can be used for multiple environment
* Authentaication Mechanism can be Improved(can Implement OAuth)
* Testing coverage need to improve
* Static Analysis tolls can be Integrated with build
* Integration with System like Heroku Travis
