FROM ubuntu:latest

# Instalação do Java e Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean

# Configuração do diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY . .

# Compila o aplicativo
RUN mvn package -DskipTests

# Expondo a porta 4000
EXPOSE 4000

# Executando o aplicativo Spring Boot
ENTRYPOINT ["java", "-jar", "target/apod-backend-0.0.1-SNAPSHOT.jar"]