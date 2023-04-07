#!/bin/bash
dos2unix mvnw
mvn -N io.takari:maven:wrapper && ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &
while true; do
  inotifywait -e modify,create,delete,move -r ./src/main/java/ -t 30 && ./mvnw compile
done