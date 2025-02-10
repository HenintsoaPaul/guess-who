# Application de Gestion de Portefeuille Crypto

## Contexte
Une personne, investisseur en cryptomonnaies, utilise cette application pour gérer son portefeuille digital. Elle souhaite consulter ses transactions d'achat, surveiller l'évolution des prix en temps réel et gérer ses actifs en toute simplicité.

## Objectifs
- Accéder à son portefeuille et voir son solde total.
- Consulter la liste de ses transactions d'achat.
- Filtrer ses achats par date et par crypto-monnaie.
- Suivre les prix des cryptos en direct.
- Modifier ses informations de profil.
- Recevoir des mises à jour en temps réel sur ses actifs.

## Flux d'utilisation

### 1. Connexion à l'application
- La personne ouvre l'application et se connecte.
- L'application charge les informations de son compte et affiche son solde total et la répartition de son portefeuille.

### 2. Consultation du portefeuille
- La personne accède à l'onglet "Portefeuille".
- L'application récupère en temps réel ses actifs grâce à Firebase Firestore.
- Elle voit la liste de ses cryptos avec les quantités détenues et leur valeur actuelle.
- Elle consulte l'évolution de son portefeuille sur une période donnée.

### 3. Consultation des achats
- La personne accède à l'onglet "Achats & Ventes".
- Elle voit la liste de ses transactions récentes, incluant la crypto-monnaie, la quantité, la date et le prix d’achat.

### 4. Filtrage des achats
- La personne veut retrouver ses transactions de Bitcoin effectuées ce mois-ci.
- Elle appuie sur "Rechercher" et renseigne :
  - Crypto-monnaie : Bitcoin
  - Date de début : 1er du mois
  - Date de fin : Aujourd’hui
- Elle valide et obtient uniquement les achats correspondants.
  - Si aucun résultat ne correspond, elle peut réinitialiser les filtres.

### 5. Suivi des cryptos en direct
- La personne consulte l'onglet "Cours des cryptos".
- Elle voit les prix actuels des cryptos suivis.
- Elle tape "Ethereum" dans la barre de recherche pour voir son cours actuel.

### 6. Mise à jour du profil
- La personne veut modifier ses informations.
- Elle accède à l'onglet "Modifier Profil".
- Elle saisit un nouveau pseudo et un nouveau mot de passe.
- Elle valide et reçoit une confirmation.

### 7. Mise à jour automatique du portefeuille
- L’application écoute en temps réel les mises à jour de Firebase Firestore.
- Si une nouvelle transaction est détectée, le portefeuille de la personne est mis à jour automatiquement.
- Une notification peut être envoyée pour l’alerter d’un changement.
- 
## Conclusion
Grâce à l'application, la personne peut facilement suivre son portefeuille, rechercher ses transactions et surveiller les prix des cryptos. Elle bénéficie aussi d'une mise à jour en temps réel de ses actifs, garantissant une gestion fluide et réactive de ses investissements.
