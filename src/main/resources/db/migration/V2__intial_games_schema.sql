CREATE TABLE `games` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `version` INT NOT NULL DEFAULT 0,
  `created` TIMESTAMP NOT NULL DEFAULT now(),
  `modified` TIMESTAMP NOT NULL DEFAULT now(),
  `user_id` INT NOT NULL,
  `outcome` ENUM('WON', 'LOST') NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


