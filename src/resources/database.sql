-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydbPaddle
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydbPaddle
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydbPaddle` DEFAULT CHARACTER SET utf8 ;
USE `mydbPaddle` ;

-- -----------------------------------------------------
-- Table `mydbPaddle`.`Locality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Locality` (
  `idLocality` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `region` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idLocality`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Club`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Club` (
  `idClub` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `streetAddress` VARCHAR(45) NOT NULL,
  `clubLocality` INT NOT NULL,
  `phoneNumber` INT NOT NULL,
  `creationDate` DATETIME NOT NULL,
  `website` VARCHAR(45) NULL,
  `isBeginnersFriendly` TINYINT NOT NULL,
  `instagramProfile` VARCHAR(20) NULL,
  PRIMARY KEY (`idClub`),
  INDEX `Locality_idx` (`clubLocality` ASC) VISIBLE,
  CONSTRAINT `ClubLocality`
    FOREIGN KEY (`clubLocality`)
    REFERENCES `mydbPaddle`.`Locality` (`idLocality`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Player` (
  `idPlayer` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(20) NOT NULL,
  `lastName` VARCHAR(20) NOT NULL,
  `birthdayDate` DATE NOT NULL,
  `gender` CHAR NOT NULL,
  `eloPoints` INT NOT NULL,
  `phoneNumber` INT NULL,
  `email` VARCHAR(45) NOT NULL,
  `playerLocality` INT NOT NULL,
  `Club` INT NOT NULL,
  `isPro` TINYINT NOT NULL,
  `instagramProfile` VARCHAR(20) NULL,
  PRIMARY KEY (`idPlayer`),
  INDEX `Locality_idx` (`playerLocality` ASC) VISIBLE,
  CONSTRAINT `playerLocality`
    FOREIGN KEY (`playerLocality`)
    REFERENCES `mydbPaddle`.`Locality` (`idLocality`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Membership`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Membership` (
  `registrationDate` DATETIME NOT NULL,
  `Club` INT NOT NULL,
  `Player` INT NOT NULL,
  PRIMARY KEY (`registrationDate`, `Club`, `Player`),
  INDEX `Club_idx` (`Club` ASC) VISIBLE,
  INDEX `Player_idx` (`Player` ASC) VISIBLE,
  CONSTRAINT `MembershipClub`
    FOREIGN KEY (`Club`)
    REFERENCES `mydbPaddle`.`Club` (`idClub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `MembershipPlayer`
    FOREIGN KEY (`Player`)
    REFERENCES `mydbPaddle`.`Player` (`idPlayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Court`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Court` (
  `idCourt` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(20) NOT NULL,
  `isOutdoor` TINYINT NOT NULL,
  `Club` INT NOT NULL,
  PRIMARY KEY (`idCourt`),
  INDEX `Club_idx` (`Club` ASC) VISIBLE,
  CONSTRAINT `CourtClub`
    FOREIGN KEY (`Club`)
    REFERENCES `mydbPaddle`.`Club` (`idClub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Tournement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Tournement` (
  `idTournement` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `prize` VARCHAR(45) NOT NULL,
  `startingDateHour` DATETIME NOT NULL,
  `endingDateHour` DATETIME NOT NULL,
  `Club` INT NOT NULL,
  PRIMARY KEY (`idTournement`),
  INDEX `Club_idx` (`Club` ASC) VISIBLE,
  CONSTRAINT `tournementClub`
    FOREIGN KEY (`Club`)
    REFERENCES `mydbPaddle`.`Club` (`idClub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Game` (
  `idGame` INT NOT NULL AUTO_INCREMENT,
  `startingDateHour` DATETIME NOT NULL,
  `endingDateHour` DATETIME NOT NULL,
  `Court` INT NOT NULL,
  `Tournement` INT NULL,
  PRIMARY KEY (`idGame`),
  INDEX `Court_idx` (`Court` ASC) VISIBLE,
  INDEX `Tournement_idx` (`Tournement` ASC) VISIBLE,
  CONSTRAINT `Court`
    FOREIGN KEY (`Court`)
    REFERENCES `mydbPaddle`.`Court` (`idCourt`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Tournement`
    FOREIGN KEY (`Tournement`)
    REFERENCES `mydbPaddle`.`Tournement` (`idTournement`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydbPaddle`.`Participation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydbPaddle`.`Participation` (
  `score` INT NOT NULL,
  `Game` INT NOT NULL,
  `Player` INT NOT NULL,
  PRIMARY KEY (`Game`, `Player`),
  INDEX `Player_idx` (`Player` ASC) VISIBLE,
  CONSTRAINT `ParticipationPlayer`
    FOREIGN KEY (`Player`)
    REFERENCES `mydbPaddle`.`Player` (`idPlayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ParticipationGame`
    FOREIGN KEY (`Game`)
    REFERENCES `mydbPaddle`.`Game` (`idGame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
