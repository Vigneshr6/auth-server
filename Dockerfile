FROM eclipse-temurin:18-jdk

EXPOSE 8080

COPY ./build/libs/spring-backend-app-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "spring-backend-app-1.0-SNAPSHOT.jar"]