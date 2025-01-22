FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/Tafuserms.jar userms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "userms.jar"]