FROM gradle:6.8.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
COPY --from=build /home/gradle/src/application.yml /app/application.yml

ENTRYPOINT ["java","-Dspring.config.location=file:/app/application.yml", "-jar","/app/spring-boot-application.jar"]