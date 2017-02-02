ALTER TABLE `games`
ADD COLUMN `player_cards` VARCHAR(45) NULL AFTER `outcome`,
ADD COLUMN `dealer_cards` VARCHAR(45) NULL AFTER `player_cards`,
ADD COLUMN `player_value` INT NULL AFTER `dealer_cards`,
ADD COLUMN `dealer_value` INT NULL AFTER `player_value`,
ADD COLUMN `bet`  INT NOT NULL AFTER `dealer_value`;
