-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fooddeliveryservice
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fooddeliveryservice
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fooddeliveryservice` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fooddeliveryservice` ;

-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`client` (
  `client_id` INT NOT NULL,
  `document` INT NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `surname` VARCHAR(40) NOT NULL,
  `patronymics` VARCHAR(40) NULL DEFAULT NULL,
  `phone_number` TEXT NOT NULL,
  `City` VARCHAR(30) NOT NULL,
  `Street` VARCHAR(60) NOT NULL,
  `Apartments` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`couriers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`couriers` (
  `courier_id` INT NOT NULL,
  `courier_document` INT NOT NULL,
  `phone` INT NOT NULL,
  `status` ENUM('busy', ' free') NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `surname` VARCHAR(40) NOT NULL,
  `patronymics` VARCHAR(40) NULL DEFAULT NULL,
  `expirience` VARCHAR(10) NOT NULL,
  `type_of_courier` ENUM("walking", "bicycling", "by car") NULL,
  PRIMARY KEY (`courier_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`position_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`position_types` (
  `position_type_id` INT NOT NULL,
  `food_type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`position_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`restaraunts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`restaraunts` (
  `restaraunt_id` INT NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`restaraunt_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`menu` (
  `position_id` INT NOT NULL,
  `restaraunts_id` INT NOT NULL,
  `position_types_id` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `food_name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`position_id`, `restaraunts_id`, `position_types_id`),
  INDEX `fk_menu_restaraunts1_idx` (`restaraunts_id` ASC) VISIBLE,
  INDEX `fk_menu_position_types1_idx` (`position_types_id` ASC) VISIBLE,
  CONSTRAINT `fk_menu_position_types1`
    FOREIGN KEY (`position_types_id`)
    REFERENCES `fooddeliveryservice`.`position_types` (`position_type_id`),
  CONSTRAINT `fk_menu_restaraunts1`
    FOREIGN KEY (`restaraunts_id`)
    REFERENCES `fooddeliveryservice`.`restaraunts` (`restaraunt_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`orders` (
  `order_id` INT NOT NULL,
  `client_id` INT NOT NULL,
  `couriers_id` INT NOT NULL,
  `datetime` DATE NOT NULL,
  `status` SMALLINT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`order_id`, `client_id`, `couriers_id`),
  INDEX `fk_orders_client1_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_orders_couriers1_idx` (`couriers_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `fooddeliveryservice`.`client` (`client_id`),
  CONSTRAINT `fk_orders_couriers1`
    FOREIGN KEY (`couriers_id`)
    REFERENCES `fooddeliveryservice`.`couriers` (`courier_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`ordered_food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`ordered_food` (
  `order_id` INT NOT NULL,
  `menu_position_id` INT NOT NULL,
  `count` INT NOT NULL,
  PRIMARY KEY (`order_id`, `menu_position_id`),
  INDEX `fk_ordered_food_orders1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_ordered_food_menu1_idx` (`menu_position_id` ASC) VISIBLE,
  CONSTRAINT `fk_ordered_food_menu1`
    FOREIGN KEY (`menu_position_id`)
    REFERENCES `fooddeliveryservice`.`menu` (`position_id`),
  CONSTRAINT `fk_ordered_food_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `fooddeliveryservice`.`orders` (`order_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fooddeliveryservice`.`reports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddeliveryservice`.`reports` (
  `order_id` INT NOT NULL,
  `compl_datetime` DATE NOT NULL,
  `grade` SMALLINT NULL DEFAULT NULL,
  `comment_cl` VARCHAR(150) NULL DEFAULT NULL,
  `result` VARCHAR(4000) NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_reports_orders1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_reports_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `fooddeliveryservice`.`orders` (`order_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
