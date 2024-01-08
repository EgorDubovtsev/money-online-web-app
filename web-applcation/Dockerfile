FROM openjdk:11-jre-slim
WORKDIR /app/jar
ENV TRANSACTION_SERVICE_HOST='http://localhost:1111', WEB_SERVICE_PROFILE='local'
COPY ./target/web-applcation-1.0.jar web-applcation-1.0.jar
ENTRYPOINT ["java", "-jar","web-applcation-1.0.jar"]
EXPOSE 8080

