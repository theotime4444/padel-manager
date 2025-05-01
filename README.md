# Padel Manager 🎾

## **Présentation du Projet**

**Projet Java – Examen 2025**

📌 **Objectif** : Développer une application Java permettant la gestion de joueurs, de matchs et de tournois de padel. L'application repose sur une **architecture 3-tiers** avec une base de données **MySQL**.

👨‍💻 **Équipe** :
- Nathan Feltesse
- Théotime Allard
- Antoine de Vinck

---

## **1. Contexte et Contraintes**

### **Dates Clés**
- 📅 **Fin mars** → Validation obligatoire de l'énoncé par le professeur
- 📅 **2ᵉ jour de la session de juin** → Dépôt du projet sur Moodle (⚠️ Pénalités en cas de retard)
- 📅 **Session d'examen** → Défense orale + évaluation des compétences Java

### **Livrables obligatoires**
✅ **Dossier validé de l'énoncé**
✅ **Lien vers le code sur GitHub**
✅ **Scripts SQL** (création + remplissage de la BDD)

### **Contraintes techniques**
✔️ Développement en **Java** sous **IntelliJ IDEA**
✔️ Architecture **MVC – 3 tiers** (View, Business, DataAccess)
✔️ Utilisation des **Design Patterns** (DAO + Singleton)
✔️ Base de données **MySQL sécurisée**
✔️ Implémentation des **tests unitaires JUnit**
✔️ Validation des entrées et gestion des exceptions
✔️ Ajout d'un **thread supplémentaire** (hors base de données)

---

## **2. Fonctionnalités de l'Application**

🎯 **Gestion des Joueurs** :
- Ajouter, modifier, supprimer et consulter un joueur
- Stockage des informations personnelles et du classement

🎯 **Gestion des Matchs** :
- Création de matchs entre joueurs
- Saisie et mise à jour des scores
- Association des matchs à un tournoi

🎯 **Gestion des Tournois** :
- Création et organisation des tournois
- Suivi des phases et progression des joueurs

🎯 **Statistiques et Classement** :
- Historique des victoires et défaites
- Classement dynamique des joueurs en fonction des performances

---

## **3. Architecture du Projet**

📂 **Structure des fichiers** :
```
PadelManager/
│── src/
│   ├── main/
│   │   ├── businessPackage/   # Gestion des règles métier (PlayerManager, ClubManager...)
│   │   ├── controllerPackage/ # Contrôleurs MVC (PlayerController...)
│   │   ├── dataAccessPackage/ # Interfaces et implémentations DAO (PlayerDataAccess, PlayerDBAccess...)
│   │   ├── exceptionPackage/  # Exceptions personnalisées (PlayerCreationException, ClubDeletionException...)
│   │   ├── modelPackage/      # Modèles de données (PlayerModel, ClubModel, GameModel...)
│   │   ├── mainPackage/       # Point d'entrée de l'application
│   ├── resources/             # Fichiers de configuration et assets
│   │   ├── assets/
│   │   ├── database.sql       # Fichier de configuration de la base de donnée MySQL
│   ├── test/                  # Tests unitaires
│── .gitignore
│── padel-manager.iml
│── README.md                  # Documentation du projet
```

📌 **Technologies utilisées** :
- Java (JDK 17)
- MySQL
- JDBC (ou Hibernate)
- JavaFX (ou API REST avec Spring Boot si applicable)
- JUnit pour les tests

📌 **Design Patterns** :
- DAO (Data Access Object) pour l'interaction avec la base de données
- Singleton pour la gestion des connexions à la base

📌 **Sécurité et Gestion des Exceptions** :
- Validation des entrées utilisateurs
- Protection contre les injections SQL
- Gestion des erreurs et logs

---

## **4. Instructions d'Installation et d'Utilisation**

### **Prérequis**
- Installer **Java JDK 17**
- Installer **IntelliJ IDEA**
- Installer **MySQL**

### **Installation**
1. **Cloner le projet**
   ```bash
   git clone https://github.com/TonNomUtilisateur/padel-manager.git
   ```
2. **Importer le projet dans IntelliJ**
3. **Configurer la base de données** avec le script SQL fourni (`database.sql`)
4. **Lancer l'application** via IntelliJ

### **Base de Données**
Exemple de connexion MySQL :
```properties
jdbc.url=jdbc:mysql://localhost:3306/mydbPaddle
jdbc.user=root
jdbc.password=mot_de_passe
```

---

## **5. Gestion des Contributions et Développement**

📌 **Workflow Git** :
La branche principale est `main`

📌 **Répartition des tâches** :
- Joueurs → Théotime Allard
- Club → Nathan Feltesse
- Tournois → Antoine de Vinck

---

## **6. Évaluation et Pénalités**

🚨 **Pénalités si non-respect des contraintes** :
- Retard dans les livrables 📅
- Absence d'un **thread supplémentaire** 🔄
- Mauvaise gestion des données (sécurité, SQL) ⚠️
- Non-application de l'architecture **MVC et Design Patterns** ❌

---

## **7. Contacts et Support**
Pour toute question ou contribution, contactez-nous via :
📧 Email : theotime4444@gmail.com
📌 GitHub Issues : https://github.com/theotime4444/padel-manager

🚀 **Projet réalisé dans le cadre de l'examen de Développement d'Applications Java à l'Henallux - Session de juin 2025**

