# springboot-testcontainers-demo
Testcontainers demo based on testing different layers of dummy micro-service build on top of the Spring Boot 2 framework.

## About
The following project shows how to use Testcontainers library in order to provide external dependencies (such as database, caches, external webservices etc) using Docker containers. Different layers of the dummy application are tested in Maven's test goal. Also there is a Spock specification supposed to act as an full-blown integration test.

## Prerequisites
In order to run the test suite there has to be Docker installed on a host machine.

## Technology stack
* Java 8
* Spring Boot 2
* Maven 3
* Testcontainers
* JUnit 5
* Spock

## How to run
Standard Maven test goal should run the whole test suite.
```
mvn clean test
```
