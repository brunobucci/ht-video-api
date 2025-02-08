# Usa uma imagem base do OpenJDK 21
FROM eclipse-temurin:21-jdk AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Baixa as dependências e compila o projeto
RUN ./mvnw clean package -DskipTests

# Criando uma segunda etapa para a imagem final
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/ht-video-api-*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
