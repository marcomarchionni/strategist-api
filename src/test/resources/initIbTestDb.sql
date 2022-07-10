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
    `when_generated` DATE NOT NULL
);

CREATE TABLE `portfolio`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `portfolio_name` VARCHAR(50) NOT NULL,
    UNIQUE (`portfolio_name`)
);

CREATE TABLE `strategy`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `strategy_name` VARCHAR(50) NOT NULL UNIQUE,
    `strategy_portfolio_id` BIGINT NOT NULL,
    CONSTRAINT `FK_strategy_portfolio` FOREIGN KEY (`strategy_portfolio_id`)
        REFERENCES `portfolio` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) AUTO_INCREMENT=8000000;

CREATE TABLE `trade`
(
    `id` BIGINT NOT NULL PRIMARY KEY, -- Primary Key column
    `trade_id` BIGINT,
    `con_id` BIGINT NOT NULL,
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
        ON UPDATE CASCADE
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
    `market_price` DECIMAL(15,4) NOT NULL,
    `multiplier` INT NOT NULL,
    `cost_basis` DECIMAL(15,4) AS (`cost_basis_price` * `quantity` * `multiplier`) STORED,
    `market_value` DECIMAL(15,4) AS (`market_price` * `quantity` * `multiplier`) STORED,
    `unrealized_PnL` DECIMAL(15,4) AS ((`market_price` - `cost_basis_price`) * `quantity` * `multiplier`) STORED,
    CONSTRAINT `FK_position_strategy` FOREIGN KEY (`position_strategy_id`)
        REFERENCES `strategy` (`id`)
        ON UPDATE CASCADE
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
    `gross_rate` DECIMAL(15,4) NOT NULL,
    `quantity` INT NOT NULL,
    `gross_amount` DECIMAL(15,4) NOT NULL,
    `tax` DECIMAL(15,4),
    `net_amount` DECIMAL(15,4) NOT NULL,
    `open_closed` VARCHAR(50) NOT NULL,
    CONSTRAINT `FK_dividend_strategy` FOREIGN KEY (`dividend_strategy_id`)
        REFERENCES `strategy` (`id`)
        ON UPDATE CASCADE
);