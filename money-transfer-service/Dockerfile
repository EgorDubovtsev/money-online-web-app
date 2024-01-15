FROM openjdk:11-jre-slim
LABEL authors="EgorDubovtsev"
COPY ./target/money-transfer-service-1.0.jar /app/app.jar
ENTRYPOINT ["java", "-jar","/app/app.jar"]
EXPOSE 8081:8081
