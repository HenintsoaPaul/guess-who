# Projet d'Application Web - Gestion des Cryptomonnaies, Ventes, Transactions et Authentification

Ce projet est une application web qui permet aux utilisateurs de se connecter, de s'inscrire, de gérer leurs ventes, d'effectuer des transactions et d'interagir avec des cryptomonnaies. L'application est organisée en plusieurs contrôleurs, chacun traitant une fonctionnalité spécifique.

## Table des matières

1. [LoginController](#logincontroller)
2. [ChartController](#chartcontroller)
3. [RegisterController](#registercontroller)
4. [SaleController](#salecontroller)
5. [TransactionController](#transactioncontroller)

## LoginController

Le contrôleur `LoginController` gère les fonctionnalités liées à l'authentification des utilisateurs.

### Endpoints

- **GET `/login`** : Affiche le formulaire de connexion avec un objet `LoginRequest` vide.
- **POST `/login/auth`** : Envoie les informations de connexion (email) à un service de login (`loginService`). Si l'authentification est réussie, l'utilisateur est redirigé vers un formulaire de saisie de code PIN (`/login/pin`). Si l'authentification échoue, un message d'erreur est affiché et l'utilisateur est renvoyé vers le formulaire de connexion initial.
- **POST `/login/pin/auth`** : Vérifie le code PIN envoyé par l'utilisateur. Si le code est valide, l'utilisateur est authentifié et un token d'authentification est généré et enregistré dans la session. L'utilisateur est alors redirigé vers la page d'accueil.

## ChartController

Le contrôleur `ChartController` est dédié à la gestion des cryptomonnaies et de leurs cours.

### Endpoints

- **GET `/crypto/all`** : Récupère la liste de toutes les cryptomonnaies disponibles depuis la base de données via le `cryptoRepository`.
- **GET `/crypto/chart`** : Redirige vers la page de graphique des cours des cryptomonnaies.
- **GET `/crypto/cours/{idCrypto}`** : Récupère les cours pour une cryptomonnaie spécifique en utilisant l'ID de la crypto (via `coursRepository`).

## RegisterController

Le contrôleur `RegisterController` gère l'inscription des utilisateurs sur le site.

### Endpoints

- **GET `/register`** : Affiche le formulaire d'inscription avec un objet `RegisterRequest` vide.
- **POST `/register/auth`** : Envoie les informations d'inscription (email) à un service d'inscription (`registerService`). Si l'inscription est validée, l'utilisateur est redirigé vers un formulaire de saisie de code PIN. Si une erreur survient, un message d'erreur est affiché et l'utilisateur est renvoyé vers le formulaire d'inscription initial.
- **POST `/register/pin/auth`** : Vérifie le code PIN fourni par l'utilisateur. Si l'inscription est réussie, un token d'authentification est généré et ajouté à la session. L'utilisateur est ensuite redirigé vers la page d'accueil.

## SaleController

Le contrôleur `SaleController` est responsable de la gestion des ventes.

### Endpoints

- **GET `/sales`** : Affiche la liste des ventes de l'utilisateur connecté. L'ID de l'utilisateur est récupéré à partir de la session.
- **GET `/sales/{id}`** : Affiche les détails d'une vente spécifique, y compris les informations sur les produits et les quantités vendues associées à cette vente.

## TransactionController

Le contrôleur `TransactionController` gère les transactions financières des utilisateurs.

### Endpoints

- **GET `/transactions`** : Affiche la liste de toutes les transactions.
- **GET `/transactions/{id}`** : Affiche les détails d'une transaction spécifique, y compris les détails associés à cette transaction.
- **GET `/transactions/test`** : Un point d'accès sécurisé pour tester l'accès aux informations sensibles de l'utilisateur.

---

## Fonctionnalités

- **Authentification et Inscription** : Les utilisateurs peuvent se connecter et s'inscrire via des formulaires. Un système de vérification de code PIN est également mis en place pour renforcer la sécurité.
- **Gestion des Cryptomonnaies** : Les utilisateurs peuvent consulter la liste des cryptomonnaies et obtenir les cours actuels.
- **Gestion des Ventes** : Les utilisateurs peuvent consulter leurs ventes et les détails des ventes.
- **Transactions Financières** : Les utilisateurs peuvent consulter et détailler leurs transactions financières, avec la possibilité d'effectuer des dépôts et retraits.

## Technologies Utilisées

- Java Spring Boot pour le backend.
- Thymeleaf pour le rendu des pages HTML.
- JDBC pour la gestion des bases de données.
- Services API pour l'intégration des cryptomonnaies et des transactions.

---

## Installation

1. Clonez ce repository :
    ```bash
    git clone https://github.com/username/project.git
    ```

2. Configurez la base de données et les variables d'environnement selon les besoins.

3. Exécutez l'application Spring Boot :
    ```bash
    mvn spring-boot:run
    ```

4. Accédez à l'application dans votre navigateur à l'adresse suivante : `http://localhost:8080`.

---

## Contribuer

Les contributions sont les bienvenues ! Pour contribuer, veuillez suivre ces étapes :

1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité ou correction de bug.
3. Soumettez un pull request avec une description claire de vos modifications.

---

## Auteurs
-**ETU002443** 
-**ETU002434** 
-**ETU002562** 
-**ETU002381** 

---
