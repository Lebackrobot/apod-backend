FROM ubuntu:latest

RUN apt-get update && apt-get install -y openjdk-17-jdk gradle
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
EXPOSE 3000

RUN ls
ENTRYPOINT ["./gradlew", "bootRun"]
