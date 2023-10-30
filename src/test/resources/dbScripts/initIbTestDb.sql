CREATE DATABASE IF NOT EXISTS `ibtest`;
-- Init Datatabase
USE `ibtest`;
DROP TABLE IF EXISTS `flex_statement`, `portfolio`, `strategy`, `trade`, `position`, `dividend`;

CREATE TABLE `flex_statement`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` VARCHAR(50) NOT NULL,
    `from_date`	DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `period` VARCHAR(50) NOT NULL,
    `when_generated` DATETIME NOT NULL
);

CREATE TABLE `portfolio`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    UNIQUE (`name`)
);

CREATE TABLE `strategy`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    `strategy_portfolio_id` BIGINT NOT NULL,
    CONSTRAINT `FK_strategy_portfolio` FOREIGN KEY (`strategy_portfolio_id`)
        REFERENCES `portfolio` (`id`)
);

CREATE TABLE `trade`
(
    `id` BIGINT NOT NULL PRIMARY KEY, -- Primary Key column
    `trade_id` BIGINT,
    `con_id` BIGINT NOT NULL,
    `ib_order_id` BIGINT NOT NULL,
    `trade_strategy_id` BIGINT,
    `trade_date` DATE NOT NULL,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(200),
    `asset_category` VARCHAR(50), --     CONSTRAINT `CHK_trade_asset_category` CHECK (`asset_category` IN ('STK','OPT','FUT','CASH')),
    `multiplier` INT NOT NULL,
    `put_call` VARCHAR(50), --     CONSTRAINT `CHK_put_call` CHECK (`put_call` IN ('PUT','CALL',NULL)),
    `strike` DECIMAL(15,4),
    `expiry` DATE,
    `buy_sell` VARCHAR(50) NOT NULL, --     CONSTRAINT `CHK_buy_sell` CHECK (`buy_sell` IN('BUY','SELL')),
    `quantity` DECIMAL(15,4) NOT NULL,
    `trade_price` DECIMAL(15,4) NOT NULL,
    `trade_money` DECIMAL(15,4) NOT NULL,
    `fifo_pnl_realized` DECIMAL(15,4),
    `ib_commission` DECIMAL(15,4),
    CONSTRAINT `FK_trade_strategy` FOREIGN KEY (`trade_strategy_id`)
        REFERENCES `strategy` (`id`)
);

CREATE TABLE `position`
(
    `id` BIGINT NOT NULL PRIMARY KEY, -- Primary Key column,
    `con_id` BIGINT NOT NULL,
    `report_date` DATE,
    `position_strategy_id` BIGINT,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(300),
    `asset_category` VARCHAR(50), --     CONSTRAINT `CHK_position_asset_category` CHECK (`asset_category` IN('STK','OPT','FUT', 'CASH')),
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

CREATE TABLE `dividend`
(
    `id` BIGINT NOT NULL PRIMARY KEY, -- Primary Key column
    `con_id` BIGINT NOT NULL,
    `dividend_strategy_id` BIGINT,
    `symbol` VARCHAR(50) NOT NULL,
    `description` VARCHAR(300),
    `ex_date` DATE NOT NULL,
    `pay_date` DATE NOT NULL,
    `gross_rate`   DECIMAL(18, 5) NOT NULL,
    `quantity`     DECIMAL(18, 5) NOT NULL,
    `gross_amount` DECIMAL(18, 5) NOT NULL,
    `tax`          DECIMAL(18, 5),
    `net_amount`   DECIMAL(18, 5) NOT NULL,
    `open_closed` VARCHAR(50) NOT NULL,
    CONSTRAINT `FK_dividend_strategy` FOREIGN KEY (`dividend_strategy_id`)
        REFERENCES `strategy` (`id`)
);