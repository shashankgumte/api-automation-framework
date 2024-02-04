**API Testing Framework**

**Description**
This is a Java-based API testing framework designed to automate testing of RESTful APIs. It utilizes TestNG for test execution, Rest Assured for API requests, and Allure for reporting.

**Features** 
* Supports various HTTP methods (GET, POST, PUT, DELETE).
* Provides request/response validation.
* Enables data-driven testing.
* Generates comprehensive test reports using Allure.
* Integrates seamlessly with CI/CD pipelines.
* Highly customizable and extensible.

**Setup Instructions**
1. Prerequisites:
* Java JDK 8 or higher installed.
* Maven installed.
* IDE (e.g., IntelliJ IDEA, Eclipse).

2. Installation:
* Clone the repository to your local machine.
`git clone https://github.com/shashankgumte/api-automation-framework.git`

3. Configuration:
* Modify the config.properties file to specify base URLs, authentication details, or any other configuration parameters.

4. Dependencies:
* Add required dependencies in the pom.xml file.

5. Build:
* Build the project using Maven.
`mvn clean install`

**Usage** 
1. Writing Tests:
* First Add helper method(GET,POST) e.g like findPetsByStatus() in com/example/api/helpers/petstore/PetServiceHelpers.java
* Add assertion methods in related file like e.g. assertStatusCode() in com/example/api/helpers/petstore/PetServiceHelpers.java
* Add test cases at src/test/java by calling api and assertion methods present in related helper file e.g. com/example/api/tests/petstore/TestPetStoreAPIs.java

2. Executing Tests:
Run tests using Maven or your IDE.
`mvn test`

3. Generating Reports:
Generate Allure reports after test execution.
`allure generate --clean && allure open`

**Best Practices**
* Write clear and descriptive test cases.
* Use meaningful test data and avoid hardcoding values.
* Maintain a consistent project structure.
* Handle errors gracefully and provide informative error messages.
* Regularly review and refactor test code for readability and maintainability.

**Contributing**
* Report any bugs or issues through GitHub's issue tracker.
* Submit feature requests or enhancements.
* Contribute code improvements or fixes via pull requests.

**Acknowledgements**
* TestNG: https://testng.org/
* Rest Assured: https://rest-assured.io/
* Allure Framework: https://docs.qameta.io/allure/
* Java Development Kit (JDK): https://www.oracle.com/java/

API_Testing_Framework Tree Structure:
│
├───src
│   ├───main
│   │   └───java
│   │       └───com
│   │           └───example
│   │               └───api
│   │                   ├───constants
│   │                   ├───utils
│   │                   ├───helpers
│   │                   └───model
│   │                       ├───Category.java
│   │                       ├───Pet.java
│   │                       └───Tag.java
│   │                    
│   └───test
│       ├───java
│           └───com
│               └───example
│                   └───api
│                       ├───tests
│                              └───PetStoreAPITests.java
│       
├───resource
│  ├───config.properties
├───pom.xml
├───allure-results
└───README.md