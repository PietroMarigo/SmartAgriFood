FROM eclipse-temurin:21-jre-alpine
RUN apk update && apk add --no-cache sqlite
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
