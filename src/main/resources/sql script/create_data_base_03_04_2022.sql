-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema work_exchange_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema work_exchange_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `work_exchange_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `work_exchange_db` ;

-- -----------------------------------------------------
-- Table `work_exchange_db`.`account_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`account_status` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_status_description` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `account_status_description` ON `work_exchange_db`.`account_status` (`account_status_description` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_description` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `role_description` ON `work_exchange_db`.`user_role` (`role_description` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(15) NULL DEFAULT NULL,
  `last_name` VARCHAR(15) NULL DEFAULT NULL,
  `registration_date` TIMESTAMP NOT NULL,
  `last_activity_date` TIMESTAMP NULL DEFAULT NULL,
  `e_mail` VARCHAR(25) NOT NULL,
  `mobile_number` VARCHAR(25) NULL DEFAULT NULL,
  `login` VARCHAR(20) NOT NULL,
  `user_password` VARCHAR(80) NULL DEFAULT NULL,
  `user_role_id` BIGINT NOT NULL,
  `account_status_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `users_ibfk_1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `work_exchange_db`.`user_role` (`id`),
  CONSTRAINT `users_ibfk_2`
    FOREIGN KEY (`account_status_id`)
    REFERENCES `work_exchange_db`.`account_status` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `e_mail` ON `work_exchange_db`.`users` (`e_mail` ASC) VISIBLE;

CREATE UNIQUE INDEX `login` ON `work_exchange_db`.`users` (`login` ASC) VISIBLE;

CREATE INDEX `user_role_id` ON `work_exchange_db`.`users` (`user_role_id` ASC) VISIBLE;

CREATE INDEX `account_status_id` ON `work_exchange_db`.`users` (`account_status_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`activation_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`activation_code` (
  `id` BIGINT NOT NULL,
  `code` VARCHAR(128) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `activation_code_ibfk_1`
    FOREIGN KEY (`id`)
    REFERENCES `work_exchange_db`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`close_salt_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`close_salt_table` (
  `id` BIGINT NOT NULL,
  `salt` CHAR(32) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`message_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`message_status` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `message_status_description` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `message_status_description` ON `work_exchange_db`.`message_status` (`message_status_description` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`messages` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sender_id` BIGINT NOT NULL,
  `formation_date` TIMESTAMP NULL DEFAULT NULL,
  `send_date` TIMESTAMP NOT NULL,
  `message_status_id` BIGINT NOT NULL,
  `text_content` VARCHAR(1000) NULL DEFAULT NULL,
  `image_content` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `messages_ibfk_1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `work_exchange_db`.`users` (`id`),
  CONSTRAINT `messages_ibfk_2`
    FOREIGN KEY (`message_status_id`)
    REFERENCES `work_exchange_db`.`message_status` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `sender_id` ON `work_exchange_db`.`messages` (`sender_id` ASC) VISIBLE;

CREATE INDEX `message_status_id` ON `work_exchange_db`.`messages` (`message_status_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`order_status` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_status_description` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `order_status_description` ON `work_exchange_db`.`order_status` (`order_status_description` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `creator_id` BIGINT NOT NULL,
  `executant_id` BIGINT NULL DEFAULT NULL,
  `main_message_id` BIGINT NOT NULL,
  `order_status_id` BIGINT NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_executant_id`
    FOREIGN KEY (`executant_id`)
    REFERENCES `work_exchange_db`.`users` (`id`),
  CONSTRAINT `orders_ibfk_1`
    FOREIGN KEY (`creator_id`)
    REFERENCES `work_exchange_db`.`users` (`id`),
  CONSTRAINT `orders_ibfk_2`
    FOREIGN KEY (`main_message_id`)
    REFERENCES `work_exchange_db`.`messages` (`id`),
  CONSTRAINT `orders_ibfk_3`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `work_exchange_db`.`order_status` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `creator_id` ON `work_exchange_db`.`orders` (`creator_id` ASC) VISIBLE;

CREATE INDEX `main_message_id` ON `work_exchange_db`.`orders` (`main_message_id` ASC) VISIBLE;

CREATE INDEX `order_status_id` ON `work_exchange_db`.`orders` (`order_status_id` ASC) VISIBLE;

CREATE INDEX `fk_executant_id` ON `work_exchange_db`.`orders` (`executant_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `work_exchange_db`.`order_chats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `work_exchange_db`.`order_chats` (
  `order_id` BIGINT NOT NULL,
  `message_id` BIGINT NOT NULL,
  `message_index_number` INT NOT NULL,
  CONSTRAINT `order_chats_ibfk_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `work_exchange_db`.`orders` (`id`),
  CONSTRAINT `order_chats_ibfk_2`
    FOREIGN KEY (`message_id`)
    REFERENCES `work_exchange_db`.`messages` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `order_id` ON `work_exchange_db`.`order_chats` (`order_id` ASC) VISIBLE;

CREATE INDEX `message_id` ON `work_exchange_db`.`order_chats` (`message_id` ASC) VISIBLE;

USE `work_exchange_db` ;

-- -----------------------------------------------------
-- function addCreatedAccountPassByUserLogin
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `addCreatedAccountPassByUserLogin`(loginVar VARCHAR(60), passVar VARCHAR(60)) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE hashPassVar VARCHAR(80);
DECLARE saltVar VARCHAR(32);
DECLARE userId BIGINT;
SELECT (SELECT users.id FROM users WHERE users.login = loginVar) INTO userId;
SELECT saveAndGetSalt(userId) INTO saltVar;
SELECT sha2(CONCAT(saltVar,passVar),224) INTO hashPassVar;
	UPDATE users 
	SET users.user_password = hashPassVar 
	WHERE users.login = loginVar;
RETURN true;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure addCreatedAccountPassByUserLoginProcedure
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCreatedAccountPassByUserLoginProcedure`(IN loginVar VARCHAR(60), IN passVar VARCHAR(60), OUT result BOOLEAN)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE hashPassVar VARCHAR(80);
DECLARE saltVar VARCHAR(32);
DECLARE userId BIGINT;
DECLARE result BOOLEAN DEFAULT true;
SELECT (SELECT users.id FROM users WHERE users.login = loginVar) INTO userId;
SELECT saveAndGetSalt(userId) INTO saltVar;
SELECT sha2(CONCAT(saltVar,passVar),224) INTO hashPassVar;
	UPDATE users 
	SET users.user_password = hashPassVar 
	WHERE users.login = loginVar;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function generate_rand_str
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `generate_rand_str`(resultLength INT) RETURNS varchar(128) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE CONST_CHARS CHAR(76);
DECLARE resultStr VARCHAR(128) DEFAULT '';
DECLARE counter INT UNSIGNED DEFAULT 0;
DECLARE randNumber int;
SET CONST_CHARS = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_"+?';
# 1.33333333 - const 100 / 75. 76 - count of signs in CONST_CHARS (need + 1)
# ceil() - function of rounding. ceil(23.13) -> 24
#"SELECT CEIL()" return integer of range (0,75)
WHILE counter < resultLength DO
SET randNumber = CEIL((RAND()*100)/1.33333);
SET resultStr = CONCAT(resultStr,SUBSTRING(const_chars, randNumber,1));
SET counter = counter + 1;
END WHILE;
RETURN resultStr;	
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function getSaltById
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `getSaltById`(userId BIGINT) RETURNS varchar(32) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE saltVar VARCHAR(32);
SELECT salt INTO saltVar FROM close_salt_table WHERE id = userId;
RETURN saltVar;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function getUserIdByActivationCode
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `getUserIdByActivationCode`(activationCode VARCHAR(130)) RETURNS bigint
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE userId BIGINT;
SELECT id FROM activation_code WHERE code = activationCode INTO userId;
RETURN userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getUserIdByActivationCodeProcedure
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserIdByActivationCodeProcedure`(IN activationCode VARCHAR(130), OUT userId BIGINT)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE userId BIGINT;
SELECT id FROM activation_code WHERE code = activationCode INTO userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function isFreeAccountLogin
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `isFreeAccountLogin`(loginVar VARCHAR(60)) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE countVar INT;
DECLARE resultVar BOOLEAN;
SELECT (SELECT count(*) FROM users WHERE users.login = loginVar) INTO countVar;
SELECT (IF(countVar = 0, true, false)) INTO resultVar;
RETURN resultVar;	
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure isFreeAccountLoginProcedure
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `isFreeAccountLoginProcedure`(IN loginVar VARCHAR(60), OUT isFree BOOLEAN)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE countVar INT;
DECLARE isFree BOOLEAN DEFAULT false;
SELECT (SELECT count(*) FROM users WHERE users.login = loginVar) INTO countVar;
SELECT  (IF(countVar = 0, true, false)) INTO isFree;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function saveAndGetActivationCodeByLogin
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `saveAndGetActivationCodeByLogin`(userLogin varchar(30)) RETURNS varchar(128) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE codeVar VARCHAR(128);
DECLARE userId BIGINT;
SELECT id FROM users WHERE users.login = userLogin INTO userId;
SELECT saveAndGetActivationCodeByUserId(userId) INTO codeVar;
RETURN codeVar;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveAndGetActivationCodeByLoginProcedure
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveAndGetActivationCodeByLoginProcedure`(IN userLogin varchar(30), OUT codeVar VARCHAR(128) CHARSET utf8mb4)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE codeVar VARCHAR(128);
DECLARE userId BIGINT;
SELECT id FROM users WHERE users.login = userLogin INTO userId;
SELECT saveAndGetActivationCodeByUserId(userId) INTO codeVar;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function saveAndGetActivationCodeByUserId
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `saveAndGetActivationCodeByUserId`(userId BIGINT) RETURNS varchar(128) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE codeVar VARCHAR(128);
SELECT generate_rand_str(128) INTO codeVar;
INSERT activation_code(id, code) VALUES (userId, codeVar);
RETURN codeVar;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveAndGetActivationCodeByUserIdProcedure
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveAndGetActivationCodeByUserIdProcedure`(IN userId BIGINT, OUT codeVar VARCHAR(128) CHARSET utf8mb4)
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE codeVar VARCHAR(128);
SELECT generate_rand_str(128) INTO codeVar;
INSERT activation_code(id, code) VALUES (userId, codeVar);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function saveAndGetSalt
-- -----------------------------------------------------

DELIMITER $$
USE `work_exchange_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `saveAndGetSalt`(userId BIGINT) RETURNS varchar(32) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
DECLARE saltVar VARCHAR(32);
SELECT generate_rand_str(64) INTO saltVar;
INSERT close_salt_table(id, salt) VALUES (userId, saltVar);
RETURN saltVar;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
