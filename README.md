# Publicis Sapient Coding Challenge

<!-- TABLE OF CONTENTS -->

## Table of Contents

<details open="open">
   <ul>
      <li>
          <a href="#requirement">Requirement</a>
      </li>
      <li>
         <a href="#technology-stack-&-other-open---source-libraries">Technology stack &amp; other Open-source libraries</a>
         <ul>
            <li><a href="#data">Data</a></li>
            <li><a href="#server---backend">Server - Backend</a></li>
            <li><a href="#libraries-and-plugins">Libraries and Plugins</a></li>
            <li><a href="#others">Others</a></li>
         </ul>
      </li>
      <li>
         <a href="#installing">Installing</a>
         <ul>
            <li><a href="#running-the-application-with-ide">Running the application with IDE</a></li>
            <li><a href="#running-the-application-with-maven">Running the application with Maven</a></li>
            <li>
               <a href="#creating-executable-jar-and-then-running-the-application">Creating executable JAR and then running the application</a>
               <ul>
                  <li>
                     <a href="#accessing-data-in-h2-database">Accessing Data in H2 Database</a>
                     <ul>
                        <li><a href="#h2-console">H2 Console</a></li>
                     </ul>
                  </li>
               </ul>
            </li>
         </ul>
      </li>
      <li>
         <a href="#code-coverage">Code Coverage</a>
         <ul>
            <li><a href="#jacoco">Jacoco</a></li>
         </ul>
      </li>
      <li>
         <a href="#testing-api">Testing API</a>
         <ul>
            <li><a href="#testing-with-postman-runner">Testing with Postman Runner</a></li>
            <li><a href="#testing-with-maven">Testing with Maven</a></li>
         </ul>
      </li>
      <li>
          <a href="#using-application">Using Application</a>
      </li>
      <li><a href="#documentation">Documentation</a></li>
      <li><a href="#contact">Contact</a></li>
   </ul>
</details>

### Requirement
Requirement document is available at path: 
`\artifacts\Credit Card Processing Task`

### Technology stack & other Open-source libraries

### Data

<details open="open">
   <ul>
      <li><a href="https://www.h2database.com/html/main.html">H2 Database Engine</a> - Java SQL database. Embedded and server modes; in-memory databases</li>
   </ul>
</details>

### Server - Backend

<details open="open">
   <ul>
      <li><a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK</a> - Java™ Platform, Standard Edition Development Kit</li>
      <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a> - Framework to ease the bootstrapping and development of new Spring Applications</li>
      <li><a href="https://maven.apache.org/">Maven</a> - Dependency Management</li>
   </ul>
</details>

### Libraries and Plugins

<details open="open">
   <ul>
      <li><a href="https://swagger.io/">Swagger</a> - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.</li>
   </ul>
</details>

### Others

<details open="open">
   <ul>
      <li><a href="https://git-scm.com/">git</a> - Free and Open-Source distributed version control system</li>
   </ul>
</details>

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

## Installing

#### Running the application with IDE

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.uk.org.ps.publicissapienttask.PublicisSapientTaskApplication` class from your IDE.

* Download the zip or clone the Git repository.
* Unzip the zip file (if you downloaded one)
* Open Command Prompt and Change directory (cd) to folder containing pom.xml
* Open Intellij
    * File -> Open -> Navigate to the folder where you unzipped the zip
    * Select the project
* Choose the Spring Boot Application file (search for @SpringBootApplication)
* Right Click on the file and Run as Java Application

#### Running the application with Maven

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so (Maven should be installed in the system and mvn command is accessible):

```shell
$ git clone https://github.com/ppagote/publicis-sapient-task.git
$ cd publicis-sapient-task
$ mvn spring-boot:run
```

#### Creating executable JAR and then running the application

The code can also be built into a jar and then executed/run. Once the jar is built, run the jar by double-clicking on it
or by using the command

```shell
$ git clone https://github.com/ppagote/publicis-sapient-task.git
$ cd publicis-sapient-task
$ mvn package -DskipTests
$ java -jar target/publicis-sapient-task-0.0.1-SNAPSHOT.jar
```

To shut down the jar, follow the below mentioned steps on a Windows machine.

* In command prompt execute the **jcmd** command to print a list of all running Java processes
* **Taskkill /PID PROCESS_ID_OF_RUNNING_APP /F** execute this command by replacing the **PROCESS_ID_OF_RUNNING_APP**
  with the actual process id of the running jar found out from executing the previous command
* Press Ctrl+C
##### Accessing Data in H2 Database

###### H2 Console

URL to access H2 console: **http://localhost:8080/h2-console/login.jsp**
or **https://192.168.99.102:8080/h2-console/login.jsp** if **SSL** is enabled.

Fill the login form as follows and click on Connect:

* Saved Settings: **Generic H2 (Embedded)**
* Setting Name: **Generic H2 (Embedded)**
* Driver class: **org.h2.Driver**
* JDBC URL: **jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE**
* User Name: **sa**
* Password:

## Code Coverage

### Jacoco

Generating code coverage reports

```shell
$ mvn test
```

This will create a detailed HTML style report showing code coverage statistics gathered via code instrumentation.

**publicis-sapient-task\target\site\jacoco\index.html**

## Testing API

### Testing with Postman Runner

Import the `\artifacts\PublicisSapientTask.postman_collection` file into postman and run the API tests (Spring Boot service should be running).

### Testing with Maven

* Run only unit tests:

```shell
$ mvn clean test
```
### Using Application

Following below steps user can use the credit card application:
* User needs to register themselves by passing the details as seen in Swagger documentation
</br>  
<img src="artifacts/registerUser.PNG" alt="register" />
  </br>
* After successful user registration, use the credentials to login
  </br>  
  <img src="artifacts/loginUser.PNG" alt="login" />
  </br>
  * After successful login, JWT token will be received which needs to be passed to every request in credit card application.
 </br>  
    <img src="artifacts/authDetails.PNG" alt="auth" />
    </br>
## Documentation

* [Swagger](http://localhost:8080/swagger-ui/) - `http://localhost:8080/swagger-ui/`- Documentation & Testing
</br>  
NOTE:: Spring security provides "/login" method which accepts username & password in the body, so "/login" details are not available in Swagger.

* [Swagger](http://localhost:8080/v2/api-docs)
    - `http://localhost:8080/v2/api-docs`- Documentation & Testing
<!-- CONTACT -->

## Contact

Pranav Pagote - pranav1990.pagote@gmail.com

Project Link: [https://github.com/ppagote/publicis-sapient-task](https://github.com/ppagote/publicis-sapient-task)
