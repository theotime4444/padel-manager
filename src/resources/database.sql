-- ====================================================
-- Base de données : padelManager
-- Description : Structure + données de démonstration
-- ====================================================

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


DROP DATABASE IF EXISTS padelManager;
CREATE DATABASE padelManager DEFAULT CHARACTER SET utf8mb4;
USE padelManager;


-- ========== TABLE: Locality ==========
CREATE TABLE Locality (
  localityId INT NOT NULL AUTO_INCREMENT,
  country VARCHAR(45) NOT NULL,
  region VARCHAR(45) NOT NULL,
  city VARCHAR(45) NOT NULL,
  PRIMARY KEY (localityId)
);

-- ========== TABLE: Player ==========
CREATE TABLE Player (
  playerId INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(20) NOT NULL,
  lastName VARCHAR(20) NOT NULL,
  birthdayDate DATE NOT NULL,
  gender CHAR(1) NOT NULL,
  eloPoints INT NOT NULL,
  phoneNumber VARCHAR(20),
  email VARCHAR(45) NOT NULL,
  localityId INT NOT NULL,
  isPro TINYINT NOT NULL,
  instagramProfile VARCHAR(50),
  PRIMARY KEY (playerId),
  FOREIGN KEY (localityId) REFERENCES Locality(localityId)
);

-- ========== TABLE: Club ==========
CREATE TABLE Club (
  clubId INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  streetAddress VARCHAR(45) NOT NULL,
  localityId INT NOT NULL,
  phoneNumber VARCHAR(20) NOT NULL,
  creationDate DATETIME NOT NULL,
  website VARCHAR(45),
  isBeginnersFriendly TINYINT NOT NULL,
  instagramProfile VARCHAR(50),
  PRIMARY KEY (clubId),
  FOREIGN KEY (localityId) REFERENCES Locality(localityId)
);

-- ========== TABLE: Court ==========
CREATE TABLE Court (
  courtId INT NOT NULL AUTO_INCREMENT,
  state VARCHAR(20) NOT NULL,
  isOutdoor TINYINT NOT NULL,
  clubId INT NOT NULL,
  PRIMARY KEY (courtId),
  FOREIGN KEY (clubId) REFERENCES Club(clubId) ON DELETE CASCADE
);

-- ========== TABLE: Tournament ==========
CREATE TABLE Tournament (
  tournamentId INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  prize VARCHAR(45) NOT NULL,
  startingDateHour DATETIME NOT NULL,
  endingDateHour DATETIME NOT NULL,
  clubId INT NOT NULL,
  PRIMARY KEY (tournamentId),
  FOREIGN KEY (clubId) REFERENCES Club(clubId) ON DELETE CASCADE
);

-- ========== TABLE: Game ==========
CREATE TABLE Game (
  gameId INT NOT NULL AUTO_INCREMENT,
  startingDateHour DATETIME NOT NULL,
  endingDateHour DATETIME NOT NULL,
  courtId INT NOT NULL,
  tournamentId INT,
  PRIMARY KEY (gameId),
  FOREIGN KEY (courtId) REFERENCES Court(courtId),
  FOREIGN KEY (tournamentId) REFERENCES Tournament(tournamentId) ON DELETE CASCADE
);

-- ========== TABLE: Membership ==========
CREATE TABLE Membership (
  registrationDate DATETIME NOT NULL,
  clubId INT NOT NULL,
  playerId INT NOT NULL,
  PRIMARY KEY (registrationDate, clubId, playerId),
  FOREIGN KEY (clubId) REFERENCES Club(clubId) ON DELETE CASCADE,
  FOREIGN KEY (playerId) REFERENCES Player(playerId) ON DELETE CASCADE
);

-- ========== TABLE: Participation ==========
CREATE TABLE Participation (
  score INT NOT NULL,
  gameId INT NOT NULL,
  playerId INT NOT NULL,
  teamNbr INT NOT NULL,
  PRIMARY KEY (gameId, playerId),
  FOREIGN KEY (gameId) REFERENCES Game(gameId),
  FOREIGN KEY (playerId) REFERENCES Player(playerId) ON DELETE CASCADE
);

-- ========== Données de démonstration ==========


-- Insertion des données : Locality
INSERT INTO Locality (localityId, country, region, city) VALUES (1, 'Belgique', 'Wallonie', 'Namur');
INSERT INTO Locality (localityId, country, region, city) VALUES (2, 'Belgique', 'Wallonie', 'Liège');
INSERT INTO Locality (localityId, country, region, city) VALUES (3, 'Belgique', 'Wallonie', 'Charleroi');
INSERT INTO Locality (localityId, country, region, city) VALUES (4, 'Belgique', 'Flandre', 'Gand');
INSERT INTO Locality (localityId, country, region, city) VALUES (5, 'Belgique', 'Flandre', 'Bruges');
INSERT INTO Locality (localityId, country, region, city) VALUES (6, 'Belgique', 'Bruxelles-Capitale', 'Bruxelles');
INSERT INTO Locality (localityId, country, region, city) VALUES (7, 'Belgique', 'Wallonie', 'Mons');
INSERT INTO Locality (localityId, country, region, city) VALUES (8, 'Belgique', 'Wallonie', 'Arlon');

-- Insertion des données : Player
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (1, 'Cécile', 'Dubois', '2002-09-23', 'F', 2474, '+33 (0)8 02 74 67 13', 'tlesage@faure.net', 8, 1, 'cécile_dubois');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (2, 'Olivier', 'Barthelemy', '2004-02-27', 'M', 1203, '06 37 50 72 63', 'manon30@tiscali.fr', 7, 0, 'olivier_barthelemy');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (3, 'Marianne', 'Dos Santos', '1997-10-01', 'F', 1746, '+33 1 59 99 29 06', 'rgautier@yahoo.fr', 8, 1, 'marianne_dos santos');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (4, 'Noël', 'Texier', '1999-10-27', 'M', 1628, '+33 5 03 04 76 68', 'lucasfouquet@gmail.com', 2, 1, 'noël_texier');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (5, 'Michel', 'Seguin', '1985-02-27', 'M', 2494, '0395248677', 'astridcousin@rocher.net', 8, 1, 'michel_seguin');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (6, 'Inès', 'Masse', '1996-09-11', 'F', 1443, '06 78 05 66 61', 'collinelisabeth@brun.com', 2, 0, 'inès_masse');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (7, 'Vincent', 'Bernard', '1997-01-21', 'M', 2205, '03 46 90 83 24', 'olivier53@camus.net', 2, 1, 'vincent_bernard');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (8, 'Anouk', 'Renault', '1995-06-30', 'F', 1597, '03 77 10 96 96', 'sylviemorin@voila.fr', 6, 0, 'anouk_renault');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (9, 'Patrick', 'Couturier', '2003-06-22', 'M', 2175, '01 47 50 51 15', 'alfred20@moulin.com', 8, 1, 'patrick_couturier');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (10, 'Amélie', 'Fournier', '1998-07-03', 'F', 1732, '05 27 20 70 23', 'pgregoire@gmail.com', 1, 1, 'amélie_fournier');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (11, 'Théophile', 'Louis', '2001-10-16', 'M', 1626, '01 43 78 78 56', 'rfabre@albert.fr', 1, 1, 'théophile_louis');
INSERT INTO Player (playerId, firstName, lastName, birthdayDate, gender, eloPoints, phoneNumber, email, localityId, isPro, instagramProfile) VALUES (12, 'Henriette', 'Perrot', '1986-07-27', 'F', 1110, '+33 4 72 51 21 76', 'renee16@bouygtel.fr', 6, 0, 'henriette_perrot');

-- Insertion des données : Club
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (1, 'Padel Club Lacroix-sur-Barre', '91, boulevard Valette', 7, '0104109210', '2021-05-21 00:00:00', 'https://padelclublacroix-sur-barre.be', 0, 'padel_club_lacroix-sur-barre');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (2, 'Padel Club Masson', 'chemin de Rossi', 4, '0170464247', '2021-07-08 00:00:00', 'https://padelclubmasson.be', 0, 'padel_club_masson');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (3, 'Padel Club Schneider', '224, rue Albert', 3, '06 77 24 01 56', '2020-08-15 00:00:00', 'https://padelclubschneider.be', 1, 'padel_club_schneider');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (4, 'Padel Club Hoareau', 'chemin Nicolas Marques', 2, '0808479996', '2020-04-24 00:00:00', 'https://padelclubhoareau.be', 1, 'padel_club_hoareau');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (5, 'Padel Club Samson', 'avenue Marguerite Fleury', 2, '0248160465', '2019-08-27 00:00:00', 'https://padelclubsamson.be', 1, 'padel_club_samson');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (6, 'Padel Club Marchand-sur-Pinto', '34, rue Blanchard', 2, '06 75 05 03 05', '2018-04-12 00:00:00', 'https://padelclubmarchand-sur-pinto.be', 0, 'padel_club_marchand-sur-pinto');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (7, 'Padel Club RegnierVille', '3, rue de Maillot', 7, '04 51 35 58 53', '2019-04-11 00:00:00', 'https://padelclubregnierville.be', 1, 'padel_club_regnierville');
INSERT INTO Club (clubId, name, streetAddress, localityId, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) VALUES (8, 'Padel Club Bouvetboeuf', '90, rue Zacharie Hebert', 1, '+33 (0)2 60 29 23 08', '2017-07-13 00:00:00', 'https://padelclubbouvetboeuf.be', 1, 'padel_club_bouvetboeuf');

-- Insertion des données : Court
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (1, 'Bon', 1, 1);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (2, 'Moyen', 1, 2);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (3, 'Moyen', 1, 3);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (4, 'Bon', 1, 4);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (5, 'Excellent', 1, 5);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (6, 'Moyen', 0, 6);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (7, 'Excellent', 1, 7);
INSERT INTO Court (courtId, state, isOutdoor, clubId) VALUES (8, 'Moyen', 0, 8);

-- Insertion des données : Tournament
INSERT INTO Tournament (tournamentId, name, prize, startingDateHour, endingDateHour, clubId) VALUES (1, 'Tournoi1', 'Prix1€', '2024-01-31 00:00:00', '2024-02-02 00:00:00', 1);
INSERT INTO Tournament (tournamentId, name, prize, startingDateHour, endingDateHour, clubId) VALUES (2, 'Tournoi2', 'Prix2€', '2024-03-01 00:00:00', '2024-03-03 00:00:00', 2);
INSERT INTO Tournament (tournamentId, name, prize, startingDateHour, endingDateHour, clubId) VALUES (3, 'Tournoi3', 'Prix3€', '2024-03-31 00:00:00', '2024-04-02 00:00:00', 5);
INSERT INTO Tournament (tournamentId, name, prize, startingDateHour, endingDateHour, clubId) VALUES (4, 'Tournoi4', 'Prix4€', '2024-04-30 00:00:00', '2024-05-02 00:00:00', 4);

-- Insertion des données : Game
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (1, '2025-01-02 00:00:00', '2025-01-02 02:00:00', 4, 2);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (2, '2025-01-03 00:00:00', '2025-01-03 02:00:00', 2, 3);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (3, '2025-01-04 00:00:00', '2025-01-04 02:00:00', 3, 4);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (4, '2025-01-05 00:00:00', '2025-01-05 02:00:00', 1, 3);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (5, '2025-01-06 00:00:00', '2025-01-06 02:00:00', 5, 4);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (6, '2025-01-07 00:00:00', '2025-01-07 02:00:00', 5, 4);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (7, '2025-01-08 00:00:00', '2025-01-08 02:00:00', 1, 4);
INSERT INTO Game (gameId, startingDateHour, endingDateHour, courtId, tournamentId) VALUES (8, '2025-01-09 00:00:00', '2025-01-09 02:00:00', 3, 2);

-- Insertion des données : Membership
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2023-01-28 00:00:00', 7, 1);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-09-20 00:00:00', 8, 2);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-08-20 00:00:00', 5, 3);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-04-05 00:00:00', 3, 4);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-07-08 00:00:00', 6, 5);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-05-13 00:00:00', 2, 6);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-08-11 00:00:00', 5, 7);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-06-17 00:00:00', 4, 8);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-09-07 00:00:00', 2, 9);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-04-29 00:00:00', 1, 10);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2023-01-05 00:00:00', 6, 11);
INSERT INTO Membership (registrationDate, clubId, playerId) VALUES ('2022-03-10 00:00:00', 4, 12);

-- Insertion des données : Participation
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (3, 3, 8, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (3, 8, 8, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (3, 3, 7, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (3, 1, 6, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (1, 3, 5, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (5, 5, 1, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (6, 6, 8, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (6, 5, 10, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (1, 2, 11, 1);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (1, 7, 1, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (1, 4, 9, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (2, 8, 3, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (0, 2, 2, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (3, 4, 1, 2);
INSERT INTO Participation (score, gameId, playerId, teamNbr) VALUES (6, 4, 11, 1);