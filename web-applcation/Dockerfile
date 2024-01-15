FROM openjdk:11-jre-slim
LABEL authors="EgorDubovtsev"
COPY ./target/web-applcation-1.0.jar /app/web-applcation-1.0.jar
ENTRYPOINT ["java", "-jar","/app/web-applcation-1.0.jar"]
EXPOSE 8080:8080