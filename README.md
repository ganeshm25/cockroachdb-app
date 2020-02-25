# cockroachdb-app
Sample Spring Data over REST using CockroachDB



# Getting Started

This project requires CockroachDB to be installed locally. To simplify database operations, it uses Spring Data Rest with Spring Data JPA.



## Running the application

1. Start the app in IntelliJ or on the command line: `./gradlew bootRun`
2. Visit: <http://localhost:8080>
3. To see all users: <http://localhost:8080/users>
4. To find user by username: <http://localhost:8080/users/search/findByUsername?username=testuser>
5. To create user:
```
curl -i -X POST -H "Content-Type:application/json" -d '{"username": "cockroachdbuser", "firstName": "CockroachDB", "lastName": "User"}' http://localhost:8080/users
```



## How the application was created

### Spring Initializr

This project is generated from Spring <https://start.spring.io>

* Select Project: <b>Gradle Project</b>
* Select Language: <b>Java</b>
* Select latest Spring Boot version: <b>2.2.4</b>
* Set Project Metadata: 
   * Group=<b>com.corelogic.cockroachdb</b>
   * Artifact=<b>demo</b>
   * Packaging=<b>Jar</b>
   * Java=<b>8</b>

* Select Dependencies: 
	1. <b>Spring Boot DevTools</b>
	2. <b>Lombok</b>
	3. <b>Rest Repositories</b>
	4. <b>Spring Data JPA</b>
	5. <b>Flyway Migration</b>
	6. <b>PostgreSQL Driver</b>


### After Project Generation

* Add `application.properties` to `src/main/resources`

```
### Set to true to use TestContainer with Docker instead of installing CockroachDB 
demo.test-container.enabled=false

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.password=
spring.datasource.type=com.zaxxer.hikari.HikariDataSource

### CockroachDB is running with start-single-node --insecure --listen-addr=localhost
### Database name is defaultdb
spring.datasource.url=jdbc:postgresql://localhost:26257/defaultdb?sslmode=disable
spring.datasource.username=root

### Set baseline-on-migrate in case this is not a new database with no existing tables
spring.flyway.baseline-on-migrate=true

### Set baseline-version for Flyway to execute migrations at version 1 or more
spring.flyway.baseline-version=0

### The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL94Dialect
``` 

* Create Flyway migration files in `src/main/resources/db/migration`

* Create an Entity class such as `ClauthUser` to represent the table created in the Flyway migration

* Create a Repository interface such as `ClauthUserRepository` with the annotation `@RepositoryRestResource`. That is all it is needed to expose Spring Data repositories over REST with Spring Data REST.

* To override some operations of Spring Data REST such as `POST`, create a `@RepositoryRestController` such as `ClauthUserController` to override create user operation.


### Manually deploying to PCF

* Create a `manifests` folder at the root of the project and a file such as `manifest.sb.yml`
The path to the `jar` will be relative to the `manifest.sb.yml` file location
 
```
---
applications:
- name: cockroachdb-app-sb
  memory: 1G
  instances: 1
  buildpack: java_buildpack_offline
  path: ../build/libs/cockroachdb-app-0.0.1-SNAPSHOT.jar
  env:
    SPRING_PROFILES_ACTIVE: 'sb'
```

* Use the terminal to cd to your project directory
* Login to your PCF environment using: `cf login -a [API Endpoint]`
* Select an org
* Select a space
* Build the jar file with this command: `./gradlew clean build`
* Enter this command to push the app: `cf push -f manifests/manifests.sb.yml`
* To push the app with a different name: `cf push cockroachdb-app-1-sb -f manifests/manifest.sb.yml`


### Using TestContainer in Unit Tests

This project also shows how to use TestContainer to run Unit Tests without installing CockroachDB.

1. Install Docker and make sure Docker is running

2. Include `testCompile "org.testcontainers:cockroachdb:1.12.5"` in `build.gradle`

3. Set `demo.test-container.enabled` in `application.properties` to `true`

4. The class `IntegrationTest` will start up CockroachDB once and override `spring.datasource.url` with the correct URL for CockroachDB on Docker



# Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)



# Guides
The following guides illustrate how to use some features concretely:

* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)



# Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
