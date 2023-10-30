-- Delete any existing data in ibTestDb

-- noinspection SqlWithoutWhereForFile
DELETE
FROM `flex_statement`;
DELETE
FROM `dividend`;
DELETE
FROM `position`;
DELETE
FROM `trade`;
DELETE
FROM `strategy`;
DELETE
FROM `portfolio`;

-- Insert sample data in ibTestDb

INSERT INTO `flex_statement` (`id`, `account_id`, `from_date`, `to_date`, `period`, `when_generated`)
VALUES ('20220708120433', 'U7169936', '2022-06-01', '2022-06-30', 'LastMonth', '2022-07-08 12:04:33');
INSERT INTO `flex_statement` (`id`, `account_id`, `from_date`, `to_date`, `period`, `when_generated`)
VALUES ('20220708130433', 'U7169936', '2022-06-01', '2022-07-08', 'Custom', '2022-07-08 13:04:33');

INSERT INTO `portfolio` (`name`)
VALUES ('Saver Portfolio');
INSERT INTO `portfolio` (`name`)
VALUES ('Trader Portfolio');
INSERT INTO `portfolio` (`name`)
VALUES ('Millionaire Portfolio');

SELECT @SaverId := id
FROM `portfolio`
WHERE name = 'Saver Portfolio';
SELECT @TraderId := id
FROM `portfolio`
WHERE name = 'Trader Portfolio';
SELECT @MillionaireId := id
FROM `portfolio`
WHERE name = 'Millionaire Portfolio';

INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('ZM long', @SaverId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('IBKR put', @TraderId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('DIS long', @SaverId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('NKE long', @SaverId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('EBAY covcall', @TraderId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('IRBT long', @SaverId);
INSERT INTO `strategy` (`name`, `strategy_portfolio_id`)
VALUES ('TTWO long', @SaverId);


SELECT @ZMId := id
FROM `strategy`
WHERE name = 'ZM long';
SELECT @IBKRId := id
FROM `strategy`
WHERE name = 'IBKR put';
SELECT @DISId := id
FROM `strategy`
WHERE name = 'DIS long';
SELECT @NKEId := id
FROM `strategy`
WHERE name = 'NKE long';
SELECT @EBAYId := id
FROM `strategy`
WHERE name = 'EBAY covcall';
SELECT @IRBTId := id
FROM `strategy`
WHERE name = 'IRBT long';
SELECT @TTWOId := id
FROM `strategy`
WHERE name = 'TTWO long';


INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (339578772, 387679436, 361181057, 339578772, @ZMId, '2022-06-07', 'ZM', 'ZOOM VIDEO COMMUNICATIONS-A', 'STK', 1,
        NULL, NULL, NULL, 'BUY', 15.0000, 111.3300, 1669.9500, 0.0000, -1.0000);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (339580463, 387681643, 370695082, 339580463, NULL, '2022-06-07', 'FVRR', 'FIVERR INTERNATIONAL LTD', 'STK', 1,
        NULL, NULL, NULL, 'BUY', 10.0000, 40.5450, 405.4500, 0.0000, 0.0000);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (342799737, 391765250, 520934333, 342799737, @IBKRId, '2022-06-15', 'IBKR  220617P00055000', 'IBKR 17JUN22 55 P',
        'OPT', 100, 'P', 55.0000, '2022-06-17', 'BUY', 1.0000, 0.5500, 55.0000, 213.0981, -0.6492);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (342799754, 391765279, 539457903, 342799754, NULL, '2022-06-15', 'IBKR  220916P00055000', 'IBKR 16SEP22 55 P',
        'OPT', 100, 'P', 55.0000, '2022-09-16', 'SELL', -1.0000, 3.7000, -370.0000, 0.0000, -0.6598);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (1222538552, NULL, 6478131, 1222538552, @TTWOId, '2022-06-28', 'TTWO', 'TAKE-TWO INTERACTIVE SOFTWARE', 'STK', 1,
        NULL, NULL, NULL, 'BUY', 0.7440, 115.2709, 85.7615, 0.0000, 0.0000);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (1222538553, NULL, 6478131, 1222538553, @TTWOId, '2022-06-28', 'TTWO', 'TAKE-TWO INTERACTIVE SOFTWARE', 'STK', 1,
        NULL, NULL, NULL, 'SELL', -0.7440, 122.6900, -91.2814, -0.6919, 0.0000);
INSERT INTO `trade` (`id`, `trade_id`, `con_id`, `ib_order_id`, `trade_strategy_id`, `trade_date`, `symbol`,
                     `description`, `asset_category`, `multiplier`, `put_call`, `strike`, `expiry`, `buy_sell`,
                     `quantity`, `trade_price`, `trade_money`, `fifo_pnl_realized`, `ib_commission`)
VALUES (1238155321, 400172483, 12087792, 1238155321, NULL, '2022-05-06', 'EUR.USD', 'EUR.USD', 'CASH', 1, NULL, NULL,
        NULL, 'BUY', 1.0000, 1.0174, 1.0174, 0.0000, -1.9484);

INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (6459, 6459, '2022-06-30', @DISId, 'DIS', 'WALT DISNEY CO/THE', 'STK', NULL, NULL, NULL, 202, 89.3512, 94.4000,
        1, NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (10291, 10291, '2022-06-30', @NKEId, 'NKE', 'NIKE INC -CL B', 'STK', NULL, NULL, NULL, 84, 24.5601, 102.2000, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (265598, 265598, '2022-06-30', @ZMId, 'AAPL', 'APPLE INC', 'STK', NULL, NULL, NULL, 540, 16.0184, 136.7200, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (265768, 265768, '2022-06-30', NULL, 'ADBE', 'ADOBE INC', 'STK', NULL, NULL, NULL, 10, 434.4900, 366.0600, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (267547, 267547, '2022-06-30', NULL, 'CGNX', 'COGNEX CORP', 'STK', NULL, NULL, NULL, 44, 46.8727, 42.5200, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (272997, 272997, '2022-06-30', NULL, 'COST', 'COSTCO WHOLESALE CORP', 'STK', NULL, NULL, NULL, 20, 138.3800,
        479.2800, 1, NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (274105, 274105, '2022-06-30', NULL, 'SBUX', 'STARBUCKS CORP', 'STK', NULL, NULL, NULL, 200, 55.1595, 76.3900, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (274385, 274385, '2022-06-30', NULL, 'SIVB', 'SVB FINANCIAL GROUP', 'STK', NULL, NULL, NULL, 8, 146.9850,
        394.9900, 1, NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (3691937, 3691937, '2022-06-30', NULL, 'AMZN', 'AMAZON.COM INC', 'STK', NULL, NULL, NULL, 460, 10.9868, 106.2100,
        1, NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (4347086, 4347086, '2022-06-30', NULL, 'EBAY', 'EBAY INC', 'STK', NULL, NULL, NULL, 100, 58.8100, 41.6700, 1,
        NULL, NULL, NULL);
INSERT INTO `position` (`id`, `con_id`, `report_date`, `position_strategy_id`, `symbol`, `description`,
                        `asset_category`, `put_call`, `strike`, `expiry`, `quantity`, `cost_basis_price`, `mark_price`,
                        `multiplier`, `cost_basis_money`, `position_value`, `fifo_pnl_unrealized`)
VALUES (4815747, 4815747, '2022-06-30', NULL, 'NVDA', 'NVIDIA CORP', 'STK', NULL, NULL, NULL, 64, 46.6325, 151.5900, 1,
        NULL, NULL, NULL);

INSERT INTO `dividend` (`id`, `con_id`, `dividend_strategy_id`, `symbol`, `description`, `ex_date`, `pay_date`,
                        `gross_rate`, `quantity`, `gross_amount`, `tax`, `net_amount`, `open_closed`)
VALUES (26754720220603, 267547, NULL, 'CGNX', 'COGNEX CORP', '2022-05-19', '2022-06-03', 0.0650, 44, 2.8600, 0.4300,
        2.4300, 'CLOSED');
INSERT INTO `dividend` (`id`, `con_id`, `dividend_strategy_id`, `symbol`, `description`, `ex_date`, `pay_date`,
                        `gross_rate`, `quantity`, `gross_amount`, `tax`, `net_amount`, `open_closed`)
VALUES (434708620220617, 4347086, @EBAYId, 'EBAY', 'EBAY INC', '2022-05-31', '2022-06-17', 0.2200, 100, 22.0000, 3.3000,
        18.7000, 'OPEN');
INSERT INTO `dividend` (`id`, `con_id`, `dividend_strategy_id`, `symbol`, `description`, `ex_date`, `pay_date`,
                        `gross_rate`, `quantity`, `gross_amount`, `tax`, `net_amount`, `open_closed`)
VALUES (510058320220711, 5100583, NULL, 'FDX', 'FEDEX CORPORATION', '2022-06-24', '2022-07-11', 1.1500, 47, 54.0500,
        8.1100, 45.9400, 'OPEN');
INSERT INTO `dividend` (`id`, `con_id`, `dividend_strategy_id`, `symbol`, `description`, `ex_date`, `pay_date`,
                        `gross_rate`, `quantity`, `gross_amount`, `tax`, `net_amount`, `open_closed`)
VALUES (4512773920220627, 45127739, NULL, 'CME', 'CME GROUP INC', '2022-06-09', '2022-06-27', 1.0000, 10, 10.0000,
        1.5000, 8.5000, 'CLOSED');
