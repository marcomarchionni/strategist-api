
-- Clear db
DELETE FROM `dividend`;
DELETE FROM `position`;
DELETE FROM `trade`;
DELETE FROM `strategy`;
DELETE FROM `portfolio`;

-- Insert sample data in ibTestDb
INSERT INTO `portfolio` (`id`,`name`) VALUES (1,'Saver Portfolio');
INSERT INTO `portfolio` (`id`,`name`) VALUES (2,'Trader Portfolio');
INSERT INTO `portfolio` (`id`,`name`) VALUES (3,'Millionaire Portfolio');


INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (1,'ZM long',1);
INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (2,'IBKR put',2);
INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (3,'DIS long',1);
INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (4,'NKE long',1);
INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (5,'EBAY coveredCall',2);
INSERT INTO `strategy` (`id`,`name`,`strategy_portfolio_id`) VALUES (6,'IRBT long',1);


INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1180780161,387679436,361181057,1,'2022-06-07','ZM','ZOOM VIDEO COMMUNICATIONS-A','STK',1,NULL,NULL,NULL,'BUY',15.0000,111.3300,1669.9500,0.0000,-1.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1180785204,387681643,370695082,NULL,'2022-06-07','FVRR','FIVERR INTERNATIONAL LTD','STK',1,NULL,NULL,NULL,'BUY',10.0000,40.5450,405.4500,0.0000,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1198688377,391765250,520934333,2,'2022-06-15','IBKR  220617P00055000','IBKR 17JUN22 55 P','OPT',100,'P',55.0000,'2022-06-17','BUY',1.0000,0.5500,55.0000,213.0981,-0.6492);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1198688378,391765279,539457903,NULL,'2022-06-15','IBKR  220916P00055000','IBKR 16SEP22 55 P','OPT',100,'P',55.0000,'2022-09-16','SELL',-1.0000,3.7000,-370.0000,0.0000,-0.6598);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1222538552,NULL,6478131,NULL,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWRE','STK',1,NULL,NULL,NULL,'SELL (Ca.)',0.7440,115.2709,85.7615,0.0000,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1222538553,NULL,6478131,NULL,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWRE','STK',1,NULL,NULL,NULL,'SELL',-0.7440,122.6900,-91.2814,-0.6919,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1238155321,400172483,12087792,NULL,'2022-07-06','EUR.USD','EUR.USD','CASH',1,NULL,NULL,NULL,'BUY',1.0000,1.0174,1.0174,0.0000,-1.9484);

INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (6459,6459,'2022-06-30',3,'DIS','WALT DISNEY CO/THE','STK',NULL,NULL,NULL,202,89.3512,94.4000,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (10291,10291,'2022-06-30',4,'NKE','NIKE INC -CL B','STK',NULL,NULL,NULL,84,24.5601,102.2000,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (265598,265598,'2022-06-30',1,'AAPL','APPLE INC','STK',NULL,NULL,NULL,540,16.0184,136.7200,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (265768,265768,'2022-06-30',NULL,'ADBE','ADOBE INC','STK',NULL,NULL,NULL,10,434.4900,366.0600,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (267547,267547,'2022-06-30',NULL,'CGNX','COGNEX CORP','STK',NULL,NULL,NULL,44,46.8727,42.5200,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (272997,272997,'2022-06-30',NULL,'COST','COSTCO WHOLESALE CORP','STK',NULL,NULL,NULL,20,138.3800,479.2800,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (274105,274105,'2022-06-30',NULL,'SBUX','STARBUCKS CORP','STK',NULL,NULL,NULL,200,55.1595,76.3900,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (274385,274385,'2022-06-30',NULL,'SIVB','SVB FINANCIAL GROUP','STK',NULL,NULL,NULL,8,146.9850,394.9900,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (3691937,3691937,'2022-06-30',NULL,'AMZN','AMAZON.COM INC','STK',NULL,NULL,NULL,460,10.9868,106.2100,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (4347086,4347086,'2022-06-30',NULL,'EBAY','EBAY INC','STK',NULL,NULL,NULL,100,58.8100,41.6700,1,NULL,NULL,NULL);
INSERT INTO `position` (`id`,`con_id`,`report_date`,`position_strategy_id`,`symbol`,`description`,`asset_category`,`put_call`,`strike`,`expiry`,`quantity`,`cost_basis_price`,`mark_price`,`multiplier`,`cost_basis_money`,`position_value`,`fifo_pnl_unrealized`) VALUES (4815747,4815747,'2022-06-30',NULL,'NVDA','NVIDIA CORP','STK',NULL,NULL,NULL,64,46.6325,151.5900,1,NULL,NULL,NULL);

INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (1029120220603,10291,NULL,'NKE','NIKE INC -CL B','2022-06-03','2022-07-01',0.3050,84,25.6200,3.8400,21.7800,'OPEN');
INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (26754720220519,267547,NULL,'CGNX','COGNEX CORP','2022-05-19','2022-06-03',0.0650,44,2.8600,0.4300,2.4300,'CLOSED');
INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (434708620220531,4347086,5,'EBAY','EBAY INC','2022-05-31','2022-06-17',0.2200,100,22.0000,3.3000,18.7000,'CLOSED');
INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (481574720220608,4815747,NULL,'NVDA','NVIDIA CORP','2022-06-08','2022-07-01',0.0400,64,2.5600,0.3800,2.1800,'OPEN');
INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (510058320220624,5100583,NULL,'FDX','FEDEX CORPORATION','2022-06-24','2022-07-11',1.1500,47,54.0500,8.1100,45.9400,'OPEN');
INSERT INTO `dividend` (`id`,`con_id`,`dividend_strategy_id`,`symbol`,`description`,`ex_date`,`pay_date`,`gross_rate`,`quantity`,`gross_amount`,`tax`,`net_amount`,`open_closed`) VALUES (4512773920220609,45127739,NULL,'CME','CME GROUP INC','2022-06-09','2022-06-27',1.0000,10,10.0000,1.5000,8.5000,'CLOSED');
