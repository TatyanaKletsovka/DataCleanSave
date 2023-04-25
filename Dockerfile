FROM maven:3.9.0-eclipse-temurin-17-focal

WORKDIR /app

RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y dos2unix

COPY mvnw pom.xml ./
RUN mvn -N io.takari:maven:wrapper
RUN mvn clean package
COPY . /app
RUN chmod +x ./mvnw

ENTRYPOINT ["java", "-jar", "/app/target/poc-0.0.1-SNAPSHOT.jar", "com.syberry.poc.DataPocApplication"]
