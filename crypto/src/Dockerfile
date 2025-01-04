FROM ubuntu:latest
LABEL authors="tsoa"

# Étape 1 : Utilisation de Maven pour construire le projet
FROM maven:3.8.5-openjdk-17 AS build

# Définit le répertoire de travail
WORKDIR /app2

# Copie les fichiers du projet dans le conteneur
COPY . .

# Exécute la commande Maven pour construire le JAR
RUN mvn clean package -DskipTests

# Étape 2 : Utilisation d'une image légère pour exécuter l'application
FROM openjdk:17-jdk-slim

# Copie le fichier JAR depuis l'étape précédente
COPY --from=build /app2/target/mada_immo-unos.jar /app2/app.jar

# Expose le port 8080
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app2/app.jar"]