-- Delete any existing data in ibTestDb

-- noinspection SqlWithoutWhereForFile
DELETE FROM `flex_statement`;
DELETE FROM `dividend`;
DELETE FROM `position`;
DELETE FROM `trade`;
DELETE FROM `strategy`;
DELETE FROM `portfolio`;
DELETE FROM `user_details`;

-- Insert sample data in ibTestDb

-- Insert test user
INSERT INTO `user_details` (`first_name`, `last_name`, `email`, `password`, `role`, `account_id`) 
VALUES ('test-admin', 'test-admin', 'test@admin.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'ADMIN', 'U1111111');

INSERT INTO `flex_statement` (`account_id`, `from_date`, `to_date`, `period`, `when_generated`) VALUES ('U1111111', '2022-06-01', '2022-06-30', 'LastMonth', '2022-07-08 12:04:33');
INSERT INTO `flex_statement` (`account_id`, `from_date`, `to_date`, `period`, `when_generated`) VALUES ('U1111111', '2022-06-01', '2022-07-08', 'Custom', '2022-07-08 13:04:33');

INSERT INTO `portfolio` (`created_at`, `name`, `description`, `account_id`) 
VALUES 
('2024-01-10', 'Saver Portfolio', 'Designed for long-term, risk-averse investors.', 'U1111111'),
('2024-01-23', 'Trader Portfolio', 'Ideal for day traders seeking high volatility.', 'U1111111'),
('2024-02-16', 'Millionaire Portfolio', 'Tailored for high net worth individuals.', 'U1111111'),
('2024-03-02', 'Retirement Portfolio', 'Focused on generating consistent income for retirees.', 'U1111111'),
('2024-04-26', 'Growth Portfolio', 'Aims at high growth through aggressive stock picks.', 'U1111111');


SELECT @SaverId := id FROM `portfolio` WHERE name = 'Saver Portfolio';
SELECT @TraderId := id FROM `portfolio` WHERE name = 'Trader Portfolio';
SELECT @MillionaireId := id FROM `portfolio` WHERE name = 'Millionaire Portfolio';

INSERT INTO `strategy` (`name`,`strategy_portfolio_id`, `account_id`) 
VALUES 
('ZM long',@SaverId, 'U1111111'),
('IBKR put',@TraderId, 'U1111111'),
('DIS long',@SaverId, 'U1111111'),
('NKE long',@SaverId, 'U1111111'),
('EBAY covcall',@TraderId, 'U1111111'),
('IRBT long',@SaverId, 'U1111111'),
('TTWO long',@SaverId, 'U1111111');


SELECT @ZMId := id FROM `strategy` WHERE name = 'ZM long';
SELECT @IBKRId := id FROM `strategy` WHERE name = 'IBKR put';
SELECT @DISId := id FROM `strategy` WHERE name = 'DIS long';
SELECT @NKEId := id FROM `strategy` WHERE name = 'NKE long';
SELECT @EBAYId := id FROM `strategy` WHERE name = 'EBAY covcall';
SELECT @IRBTId := id FROM `strategy` WHERE name = 'IRBT long';
SELECT @TTWOId := id FROM `strategy` WHERE name = 'TTWO long';


INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`ib_order_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`, `account_id`) 
VALUES 
(339578772,387679436,361181057,339578772,@ZMId,'2022-06-07','ZM','ZOOM VIDEO COMMUNICATIONS-A','STK',1,NULL,NULL,NULL,'BUY',15.0000,111.3300,1669.9500,0.0000,-1.0000, 'U1111111'),
(339580463,387681643,370695082,339580463,NULL,'2022-06-07','FVRR','FIVERR INTERNATIONAL LTD','STK',1,NULL,NULL,NULL,'BUY',10.0000,40.5450,405.4500,0.0000,0.0000, 'U1111111'),
(342799737,391765250,520934333,342799737,@IBKRId,'2022-06-15','IBKR  220617P00055000','IBKR 17JUN22 55 P','OPT',100,'P',55.0000,'2022-06-17','BUY',1.0000,0.5500,55.0000,213.0981,-0.6492, 'U1111111'),
(342799754,391765279,539457903,342799754,NULL,'2022-06-15','IBKR  220916P00055000','IBKR 16SEP22 55 P','OPT',100,'P',55.0000,'2022-09-16','SELL',-1.0000,3.7000,-370.0000,0.0000,-0.6598, 'U1111111'),
(1222538552,NULL,6478131,1222538552,@TTWOId,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWARE','STK',1,NULL,NULL,NULL,'BUY',0.7440,115.2709,85.7615,0.0000,0.0000, 'U1111111'),
(1222538553,NULL,6478131,1222538553,@TTWOId,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWARE','STK',1,NULL,NULL,NULL,'SELL',-0.7440,122.6900,-91.2814,-0.6919,0.0000, 'U1111111'),
(1238155321,400172483,12087792,1238155321,NULL,'2022-05-06','EUR.USD','EUR.USD','CASH',1,NULL,NULL,NULL,'BUY',1.0000,1.0174,1.0174,0.0000,-1.9484, 'U1111111');

INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`, `account_id`) 
VALUES (6459,6459,'2022-06-30',@DISId,'DIS','WALT DISNEY CO/THE','STK',NULL,NULL,NULL,202,89.3512,94.4000,1,NULL,NULL,NULL, 'U1111111'),
 (10291,10291,'2022-06-30',@NKEId,'NKE','NIKE INC -CL B','STK',NULL,NULL,NULL,84,24.5601,102.2000,1,NULL,NULL,NULL, 'U1111111'),
 (265598,265598,'2022-06-30',@ZMId,'AAPL','APPLE INC','STK',NULL,NULL,NULL,540,16.0184,136.7200,1,NULL,NULL,NULL, 'U1111111'),
 (265768,265768,'2022-06-30',NULL,'ADBE','ADOBE INC','STK',NULL,NULL,NULL,10,434.4900,366.0600,1,NULL,NULL,NULL, 'U1111111'),
 (267547,267547,'2022-06-30',NULL,'CGNX','COGNEX CORP','STK',NULL,NULL,NULL,44,46.8727,42.5200,1,NULL,NULL,NULL, 'U1111111'),
 (272997,272997,'2022-06-30',NULL,'COST','COSTCO WHOLESALE CORP','STK',NULL,NULL,NULL,20,138.3800,479.2800,1,NULL,NULL,NULL, 'U1111111'),
 (274105,274105,'2022-06-30',NULL,'SBUX','STARBUCKS CORP','STK',NULL,NULL,NULL,200,55.1595,76.3900,1,NULL,NULL,NULL, 'U1111111'),
 (274385,274385,'2022-06-30',NULL,'SIVB','SVB FINANCIAL GROUP','STK',NULL,NULL,NULL,8,146.9850,394.9900,1,NULL,NULL,NULL, 'U1111111'),
 (3691937,3691937,'2022-06-30',NULL,'AMZN','AMAZON.COM INC','STK',NULL,NULL,NULL,460,10.9868,106.2100,1,NULL,NULL,NULL, 'U1111111'),
 (4347086,4347086,'2022-06-30',NULL,'EBAY','EBAY INC','STK',NULL,NULL,NULL,100,58.8100,41.6700,1,NULL,NULL,NULL, 'U1111111'),
 (4815747,4815747,'2022-06-30',NULL,'NVDA','NVIDIA CORP','STK',NULL,NULL,NULL,64,46.6325,151.5900,1,NULL,NULL,NULL, 'U1111111');

INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`, `account_id`) 
VALUES 
(26754720220603,267547,NULL,'CGNX','COGNEX CORP','2022-05-19','2022-06-03',0.0650,44,2.8600,0.4300,2.4300,'CLOSED', 'U1111111'),
(434708620220617,4347086,@EBAYId,'EBAY','EBAY INC','2022-05-31','2022-06-17',0.2200,100,22.0000,3.3000,18.7000,'OPEN', 'U1111111'),
(510058320220711,5100583,NULL,'FDX','FEDEX CORPORATION','2022-06-24','2022-07-11',1.1500,47,54.0500,8.1100,45.9400,'OPEN', 'U1111111'),
(4512773920220627,45127739,NULL,'CME','CME GROUP INC','2022-06-09','2022-06-27',1.0000,10,10.0000,1.5000,8.5000,'CLOSED', 'U1111111');
