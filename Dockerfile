FROM eclipse-temurin:18-jdk
ARG releaseVersion=1.0-SNAPSHOT
ENV releaseVersion=$releaseVersion

EXPOSE 8080

COPY ./build/libs/spring-backend-app-${releaseVersion}.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT java -jar spring-backend-app-$releaseVersion.jar