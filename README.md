# REST Assured API Automation Framework

A scalable API automation framework built using **Java 21, REST Assured, TestNG, Maven, Jackson and JSON Schema Validation**.

The project demonstrates API test automation practices including CRUD operations, reusable service layers, request/response specifications, POJO serialization and deserialization, data-driven testing, negative testing, authentication specifications, environment-based configuration, schema validation, smoke/regression suites and GitHub Actions CI.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| REST Assured | REST API automation |
| TestNG | Test execution and assertions |
| Maven | Dependency management and build execution |
| Jackson | JSON serialization/deserialization |
| Hamcrest | Response assertions |
| JSON Schema Validator | Contract/schema validation |
| SLF4J | Logging |
| Git | Version control |
| GitHub Actions | Continuous Integration |

---

## API Under Test

The framework currently uses **JSONPlaceholder** as the public practice REST API.

Base URL:

```text
https://jsonplaceholder.typicode.com
```

JSONPlaceholder simulates POST, PUT, PATCH and DELETE operations. Write operations return representative responses but are not permanently persisted.

---

## Framework Architecture

```text
Tests
  |
  v
Service Layer
  |
  v
Request / Authentication Specifications
  |
  v
REST Assured
  |
  v
REST API
  |
  v
Response
  |
  +--> Status Validation
  +--> Header Validation
  +--> Body Validation
  +--> JSONPath
  +--> Schema Validation
  +--> Deserialization
```

---

## Project Structure

```text
restassured-api-automation
|
+-- src
|   +-- main
|   |   +-- java
|   |       +-- com.Emad.automation
|   |           +-- config
|   |           |   +-- ConfigReader.java
|   |           +-- constants
|   |           |   +-- ApiEndpoints.java
|   |           +-- models
|   |           |   +-- User.java
|   |           +-- services
|   |           |   +-- UserService.java
|   |           +-- utils
|   |               +-- AuthSpecBuilder.java
|   |               +-- SpecBuilder.java
|   |
|   +-- test
|       +-- java
|       |   +-- com.Emad.automation
|       |       +-- data
|       |       |   +-- TestDataProvider.java
|       |       +-- tests
|       |           +-- BaseTest.java
|       |           +-- UserTest.java
|       |           +-- DataDrivenUserTest.java
|       |           +-- NegativeApiTest.java
|       |           +-- AdvancedValidationTest.java
|       |           +-- AuthenticationSpecTest.java
|       |
|       +-- resources
|           +-- schemas
|           |   +-- user-schema.json
|           +-- config.properties
|           +-- testng.xml
|           +-- smoke-testng.xml
|           +-- regression-testng.xml
|
+-- .github
|   +-- workflows
|       +-- api-tests.yml
|       +-- smoke-tests.yml
|
+-- .gitignore
+-- pom.xml
+-- README.md
```

---

## Automated Scenarios

The framework currently covers:

### CRUD API Testing

- GET all users
- GET user by ID
- POST/create user
- PUT/full user update
- PATCH/partial user update
- DELETE user

### Response Validation

- HTTP status codes
- JSON response body
- response headers
- Content-Type
- response structure
- response time
- JSON Schema

### Negative Testing

- Non-existing user
- HTTP 404 validation
- empty response validation

### Data-Driven Testing

TestNG `@DataProvider` executes API tests against multiple test-data combinations.

### Serialization

Java POJOs are automatically converted into JSON request payloads.

```text
Java POJO -> JSON
```

### Deserialization

JSON responses are converted into Java objects.

```text
JSON -> Java POJO
```

Unknown API response fields are safely handled using Jackson configuration on the model.

---

## Authentication Design

The framework contains reusable specifications for:

- Bearer Token authentication
- API Key authentication

No real credentials or secrets are committed to the repository.

The public API used by the project does not require authentication, so authentication-specification tests validate the reusable framework components rather than claiming to test a secured endpoint.

---

## Environment Management

Supported logical environments:

```text
dev
qa
staging
```

Environment selection can be overridden using a JVM system property.

Example:

```bash
mvn clean test -Denvironment=staging
```

---

## Test Suites

### Full Suite

```bash
mvn clean test
```

### Smoke Suite

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/smoke-testng.xml
```

### Regression Suite

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/
# mvn clean test "-DsuiteXmlFile=src/test/resources/testng.
# mvn clean test "-DsuiteXmlFile=src/test/resources/regression-testng.xml"
xml"regression-testng.xml
```

---

## Current Test Execution

Full framework execution:

```text
Tests run: 23
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The exact execution time depends on network conditions and the availability of the external public API.

---

## Logging

Request and response logging can be enabled from:

```properties
request.logging=true
```

Normal execution uses:

```properties
request.logging=false
```

REST Assured is also configured to log request/response details when validation fails.

### Performance logging

Response-time performance logs can be enabled from:

```properties
performance.logging=true
```

They can also be enabled for one Maven run:

```powershell
mvn clean test "-Dperformance.logging=true"
```

Each log includes the HTTP method, URI, status code, response time in milliseconds and response size in bytes.

Performance logs are printed in the Maven terminal. To save the complete
Maven output and performance entries to a text file:

```powershell
mvn clean test "-Dperformance.logging=true" | Tee-Object -FilePath performance-log.txt
```

Open the generated file from the project root:

```powershell
code performance-log.txt
```

Normal execution uses:

```properties
performance.logging=false
```

### Pass/fail report

After Maven finishes, open:

```text
target/pass-fail-report.html
```

The report includes the pass, fail and skipped totals, test names, classes,
duration and failure details.

---

## JSON Schema Validation

The project validates API responses against JSON Schema definitions stored under:

```text
src/test/resources/schemas
```

This helps verify the response contract in addition to individual field assertions.

---

## CI/CD

GitHub Actions automatically executes the API suite for repository pushes and pull requests.

The CI pipeline:

```text
Git Push / Pull Request
        |
        v
GitHub Actions
        |
        v
Java 21 Setup
        |
        v
Maven Build
        |
        v
REST Assured + TestNG
        |
        v
API Tests
        |
        v
Surefire Reports
```

A separate manually triggered workflow is available for smoke testing.

---

## Framework Design Principles

The framework follows several maintainability principles:

- separation of tests from API interaction logic
- reusable service layer
- centralized endpoint management
- reusable request and response specifications
- POJO-based payload management
- environment-based configuration
- data-driven test execution
- independent positive and negative scenarios
- secure authentication design
- CI-ready execution
- no hard-coded secrets

---

## Key Learning Outcomes

This project demonstrates practical understanding of:

- REST architecture
- HTTP methods
- HTTP status codes
- request and response handling
- REST Assured
- TestNG
- Maven
- POJO design
- serialization/deserialization
- JSONPath
- Hamcrest assertions
- JSON Schema validation
- negative API testing
- data-driven testing
- authentication specifications
- environment management
- smoke/regression testing
- GitHub Actions CI

---

## Author

**Emadendy**

QA Automation Engineer | Java | Selenium | Cypress | REST Assured