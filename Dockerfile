FROM maven:3.9.0-eclipse-temurin-17-focal

WORKDIR /app

RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y dos2unix

COPY mvnw pom.xml ./
RUN mvn -N -e io.takari:maven:wrapper
RUN mvn clean package
COPY . /app

CMD ./mvnw spring-boot:run
