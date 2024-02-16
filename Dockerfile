FROM ubuntu:latest
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

WORKDIR /app
COPY . .
EXPOSE 4000

ENTRYPOINT ["java", "-jar", "target/apod-backend-0.0.1-SNAPSHOT.jar"]