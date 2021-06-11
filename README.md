# Swagger_Petstore_Java  ![Coverage](.github/badges/jacoco.svg)
I have created this project cosidering the possibility of addition of more apis and validations. So I have taken a modular approch where the entire program is split into different classes and packages to make any addition of new code easier.
## Scenarios Covered

1. Do a GET request to the /pet/findByStatus endpoint. Query all valid status(available, pending, sold)
2. Validate the response code to be 200
3. Validate the response time to be less than 200 ms
4. Iterate over all elements of the json response body and print out all pets that are Lions.
5. Validate content-type header
6. Validate api response Schema. A Json Schema file is added - **/src/main/resources/findByStatus.json**

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

Three test will run each making GET call to findByStatus api with querying different status(available, pending, sold). 

## CI Integration
CI integrations is done using GitHub Actions Work Flow. Every time a pull request is raised or code is merged to master the tests will be triggered and code coverage report will be generated. JoCoCo plugin is used to generate code coverage report.

(/screen_shots_for_README_file/CI.PNG![CI](https://user-images.githubusercontent.com/76465501/121572559-f2324d80-ca41-11eb-8328-09b941472992.PNG)


/screen_shots_for_README_file/Built Success.PNG![Built Success](https://user-images.githubusercontent.com/76465501/121572834-34f42580-ca42-11eb-80c7-806b75072287.PNG)


## Results
Logs can be seen in folde **/logs/**.

Test Report in **/test-output/reports/**
