FROM maven:3.9.0 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package

FROM openjdk:17
ENV TZ="Europe/Minsk"
RUN date && mkdir /opt/app/
WORKDIR /opt/app
COPY --from=MAVEN_BUILD /build/target/poc-0.0.1-SNAPSHOT.jar /opt/app/app.jar
CMD ls /opt/app && java -jar /opt/app/app.jar
