FROM eclipse-temurin:17-jdk-alpine as builder
COPY . .
RUN apk --update add --no-cache netcat-openbsd
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app-root/src
COPY --from=builder /build/libs/backend-0.0.1-SNAPSHOT.jar /opt/app-root/src/app.jar
CMD java $JAVA_OPTS -jar app.jar
