-- liquibase formatted sql

-- changeset strategist:001-create-schema-user-details
CREATE TABLE `user_details`
(
	`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50),
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `role` VARCHAR(50) NOT NULL,
    `account_id` VARCHAR(50) NOT NULL,
    UNIQUE(`email`),
    UNIQUE(`account_id`)
);

-- changeset strategist:001-create-schema-flex-statement
CREATE TABLE `flex_statement`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `from_date`	DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `period` VARCHAR(50) NOT NULL,
    `when_generated` DATETIME NOT NULL
);

-- changeset strategist:001-create-schema-portfolio
CREATE TABLE `portfolio`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_at` DATE NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `account_id` VARCHAR(50) NOT NULL,
    UNIQUE (`name`, `account_id`)
);

-- changeset strategist:001-create-schema-strategy
CREATE TABLE `strategy`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `strategy_portfolio_id` BIGINT NOT NULL,
    UNIQUE (`name`, `account_id`),
    CONSTRAINT `FK_strategy_portfolio` FOREIGN KEY (`strategy_portfolio_id`)
        REFERENCES `portfolio` (`id`)
);

-- changeset strategist:001-create-schema-trade
CREATE TABLE `trade`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `trade_id` BIGINT,
    `con_id` BIGINT NOT NULL,
    `ib_order_id` BIGINT NOT NULL,
    `trade_strategy_id` BIGINT,
    `trade_date` DATE NOT NULL,
    `date_time` DATETIME,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `asset_category` VARCHAR(50),
    `multiplier` INT NOT NULL,
    `put_call` VARCHAR(50),
    `strike` DECIMAL(15,4),
    `expiry` DATE,
    `buy_sell` VARCHAR(50) NOT NULL,
    `quantity` DECIMAL(15,4) NOT NULL,
    `trade_price` DECIMAL(15,4) NOT NULL,
    `trade_money` DECIMAL(15,4) NOT NULL,
    `fifo_pnl_realized` DECIMAL(15,4),
    `ib_commission` DECIMAL(15,4),
    CONSTRAINT `FK_trade_strategy` FOREIGN KEY (`trade_strategy_id`)
        REFERENCES `strategy` (`id`)
);

-- changeset strategist:001-create-schema-position
CREATE TABLE `position`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `con_id` BIGINT NOT NULL,
    `report_date` DATE,
    `position_strategy_id` BIGINT,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `asset_category` VARCHAR(50),
    `put_call` VARCHAR(50),
    `strike` DECIMAL(15,4),
    `expiry` DATE,
    `quantity` INT NOT NULL,
    `cost_basis_price` DECIMAL(15,4) NOT NULL,
    `mark_price` DECIMAL(15,4) NOT NULL,
    `multiplier` INT NOT NULL,
    `cost_basis_money` DECIMAL(15,4),
    `position_value` DECIMAL(15,4),
    `fifo_pnl_unrealized` DECIMAL(15,4),
    CONSTRAINT `FK_position_strategy` FOREIGN KEY (`position_strategy_id`)
        REFERENCES `strategy` (`id`)
);

-- changeset strategist:001-create-schema-dividend
CREATE TABLE `dividend`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `con_id` BIGINT NOT NULL,
    `action_id` BIGINT,
    `dividend_strategy_id` BIGINT,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    `ex_date` DATE NOT NULL,
    `pay_date` DATE NOT NULL,
    `gross_rate` DECIMAL(15,4) NOT NULL,
    `quantity` INT NOT NULL,
    `gross_amount` DECIMAL(15,4) NOT NULL,
    `tax` DECIMAL(15,4),
    `net_amount` DECIMAL(15,4) NOT NULL,
    `open_closed` VARCHAR(50) NOT NULL,
    CONSTRAINT `FK_dividend_strategy` FOREIGN KEY (`dividend_strategy_id`)
        REFERENCES `strategy` (`id`)
);


