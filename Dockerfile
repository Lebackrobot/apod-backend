FROM ubuntu:latest

RUN apt-get update && apt-get install -y openjdk-17-jdk gradle
COPY . .
RUN ./gradlew build -x test
EXPOSE 4000

RUN ls
ENTRYPOINT ["./gradlew", "bootRun"]
