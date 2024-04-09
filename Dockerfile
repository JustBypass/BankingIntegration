FROM openjdk:17-jdk-alpine
MAINTAINER nikita

COPY target/task-spring-boot.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]