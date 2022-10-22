FROM eclipse-temurin:18-jdk
ARG releaseVersionArg=1.0-SNAPSHOT
ENV releaseVersionEnv=$releaseVersionArg

EXPOSE 8080

COPY ./build/libs/spring-backend-app-${releaseVersionArg}.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "spring-backend-app-$releaseVersionEnv.jar"]