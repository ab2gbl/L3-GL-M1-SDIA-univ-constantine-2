-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`class` (
  `classname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`classname`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`person` (
  `id` INT NOT NULL,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `date_of_birth` DATE NULL,
  `mail` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`teacher` (
  `idteacher` INT NOT NULL AUTO_INCREMENT,
  `idperson` INT NOT NULL,
  `module` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idteacher`),
  INDEX `fk_teacher_person1_idx` (`idperson` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_person1`
    FOREIGN KEY (`idperson`)
    REFERENCES `mydb`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`seance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`seance` (
  `idseance` INT NOT NULL AUTO_INCREMENT,
  `idteacher` INT NOT NULL,
  `classname` VARCHAR(45) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `time` TIME NULL DEFAULT NULL,
  `Endtime` TIME NULL,
  PRIMARY KEY (`idseance`, `idteacher`, `classname`),
  INDEX `fk_seance_teacher1_idx` (`idteacher` ASC) VISIBLE,
  INDEX `fk_seance_class1_idx` (`classname` ASC) VISIBLE,
  CONSTRAINT `fk_seance_class1`
    FOREIGN KEY (`classname`)
    REFERENCES `mydb`.`class` (`classname`),
  CONSTRAINT `fk_seance_teacher1`
    FOREIGN KEY (`idteacher`)
    REFERENCES `mydb`.`teacher` (`idteacher`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`parent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`parent` (
  `idparent` INT NOT NULL AUTO_INCREMENT,
  `idperson` INT NOT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idparent`),
  INDEX `fk_parent_person1_idx` (`idperson` ASC) VISIBLE,
  CONSTRAINT `fk_parent_person1`
    FOREIGN KEY (`idperson`)
    REFERENCES `mydb`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`student` (
  `idstudent` INT NOT NULL AUTO_INCREMENT,
  `classname` VARCHAR(45) NOT NULL,
  `idperson` INT NOT NULL,
  `idparent` INT NULL,
  PRIMARY KEY (`idstudent`),
  INDEX `fk_student_class1_idx` (`classname` ASC) VISIBLE,
  INDEX `fk_student_parent1_idx` (`idparent` ASC) VISIBLE,
  INDEX `person_idx` (`idperson` ASC) VISIBLE,
  CONSTRAINT `fk_student_class1`
    FOREIGN KEY (`classname`)
    REFERENCES `mydb`.`class` (`classname`),
  CONSTRAINT `fk_student_parent1`
    FOREIGN KEY (`idparent`)
    REFERENCES `mydb`.`parent` (`idparent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `person`
    FOREIGN KEY (`idperson`)
    REFERENCES `mydb`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`absence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`absence` (
  `idseance` INT NOT NULL,
  `idstudent` INT NOT NULL,
  PRIMARY KEY (`idseance`, `idstudent`),
  INDEX `fk_seance_has_student_student1_idx` (`idstudent` ASC) VISIBLE,
  INDEX `fk_seance_has_student_seance1_idx` (`idseance` ASC) VISIBLE,
  CONSTRAINT `fk_seance_has_student_seance1`
    FOREIGN KEY (`idseance`)
    REFERENCES `mydb`.`seance` (`idseance`),
  CONSTRAINT `fk_seance_has_student_student1`
    FOREIGN KEY (`idstudent`)
    REFERENCES `mydb`.`student` (`idstudent`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`chat` (
  `idteacher` INT NOT NULL,
  `idparent` INT NOT NULL,
  PRIMARY KEY (`idteacher`, `idparent`),
  INDEX `fk_teacher_has_parent_parent1_idx` (`idparent` ASC) VISIBLE,
  INDEX `fk_teacher_has_parent_teacher1_idx` (`idteacher` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_has_parent_parent1`
    FOREIGN KEY (`idparent`)
    REFERENCES `mydb`.`parent` (`idparent`),
  CONSTRAINT `fk_teacher_has_parent_teacher1`
    FOREIGN KEY (`idteacher`)
    REFERENCES `mydb`.`teacher` (`idteacher`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`following`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`following` (
  `idteacher` INT NOT NULL,
  `idstudent` INT NOT NULL,
  `examen_point` INT NULL DEFAULT NULL,
  `test_point` INT NULL DEFAULT NULL,
  `evaluation` INT NULL DEFAULT NULL,
  `notes` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idteacher`, `idstudent`),
  INDEX `fk_teacher_has_student_student1_idx` (`idstudent` ASC) VISIBLE,
  INDEX `fk_teacher_has_student_teacher1_idx` (`idteacher` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_has_student_student1`
    FOREIGN KEY (`idstudent`)
    REFERENCES `mydb`.`student` (`idstudent`),
  CONSTRAINT `fk_teacher_has_student_teacher1`
    FOREIGN KEY (`idteacher`)
    REFERENCES `mydb`.`teacher` (`idteacher`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

alter table following
add check (examen_point>=0 and examen_point<=20 and
			test_point>=0 and test_point<=20 and
            evaluation>=0 and evaluation<=20 );
