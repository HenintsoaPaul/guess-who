FROM openjdk:17-jdk-slim

WORKDIR /app2

# Copie le fichier JAR depuis l'étape précédente
COPY ./target/crypto-unos.jar /app2/crypto_app.jar

# Expose le port 8080
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app2/crypto_app.jar"]