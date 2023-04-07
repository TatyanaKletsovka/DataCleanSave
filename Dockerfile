FROM maven:3.9.0-eclipse-temurin-19-focal

WORKDIR /app

RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y dos2unix

COPY mvnw pom.xml ./
COPY . /app

CMD mvn -N io.takari:maven:wrapper && ./mvnw spring-boot:run
