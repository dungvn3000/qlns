# qlns schema

# --- !Ups

CREATE TABLE `qlns`.`user` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `username` VARCHAR(250) NOT NULL, `password` VARCHAR(250) NOT NULL, `email` VARCHAR(250) NOT NULL, `role` VARCHAR(250) NOT NULL, UNIQUE (`username`)) ENGINE = InnoDB;

# --- !Downs

DROP TABLE `qlns`.`user`;