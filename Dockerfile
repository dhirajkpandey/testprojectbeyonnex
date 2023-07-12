FROM maven:3.8.1-openjdk-17-slim

COPY src /home/beyonnex/src

COPY pom.xml /home/beyonnex

COPY testng.xml /home/beyonnex

RUN mvn -f /home/beyonnex/pom.xml clean test -DskipTests=true