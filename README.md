# Swagger_Petstore_Java  ![Coverage](.github/badges/jacoco.svg)

## Scenarios Covered

1. Do a GET request to the /pet/findByStatus endpoint
2. Validate the response code to be 200
3. Validate the response time to be less than 200 ms
4. Iterate over all elements of the json response body and print out all pets that are Lions.
5. Validate content-type header
6. Validate Json Schema

### **additionally**

7. continuous integration using GitHub Actions Work Flow
8. Code coverage report using JaCoCo. code coverage badge is added in this README.md file. code coverage report- **/test-output/code-coverage/index.html**
9. code Documentation using javadoc. javadoc file - **/doc/index.html**
10. Logging using Apache Log4j. Sample file in **/logs/**
11. Test reporting using Extent Reporting. Sample file in **/test-output/reports/**

## Technology Stack
1. java 11.0.11
2. Apache Maven 3.8.1
3. TestNG 7.0.0

## How to Run
The test class is **/src/main/java/pet/tests/FindPetsTest.java**. A _testng.xml_ file is created in project path. This file can be executed using maven by simply running command **mvn test** 
1. open command prompt
2. navigate to project
3. run mvn test

java and maven must be installed and added to PATH

## CI Integration
CI integrations is done using GitHub Actions Work Flow. Every time a pull request is raised or code is merged to master the tests will be triggered and code coverage report will be generated. JoCoCo plugin is used to generate code coverage report. 

## Results
Logs can be seen in folde **/logs/**
Test Report in **/test-output/reports/**
