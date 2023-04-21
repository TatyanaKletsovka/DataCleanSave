# Data POC

## Environment:

```
Language:   Java 17
DB:         MySQL 8.0
Deploy:     Docker 20.10.17
```

## Build application


1. Create `.env.local` based on `.env.dist` file and fill it with variables' values. Like in example:
```text
 DB_USER={{mysql user name}}
 DB_PASSWORD={{mysql password for user}}
```

2. Build the Docker image for your application by running the following command in project directory:
> `docker-compose up --build`

This will start the Spring Boot application and any other dependencies in the docker-compose.yml file. You can access the application at http://localhost:8080.

## StyleGuides

To run style check it is needed to execute `mvn checkstyle:checkstyle`
#### Dependencies

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
</plugin>
```

## Tests

To run tests it is needed to execute `mvn test`.  
To check test coverage it is needed to execute `mvn verify -Pjacoco-generate-report -DskipTests`.  
Coverage report can be found at ./target/site/jacoco/index.html

#### Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
</dependency>

<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
