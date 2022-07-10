-- Insert sample data in ibTestDb

INSERT INTO `portfolio` (`id`,`portfolio_name`) VALUES (1,'Saver Portfolio');
INSERT INTO `portfolio` (`id`,`portfolio_name`) VALUES (2,'Trader Portfolio');


INSERT INTO `strategy` (`id`,`strategy_name`,`strategy_portfolio_id`) VALUES (1,'AAPL long',1);
INSERT INTO `strategy` (`id`,`strategy_name`,`strategy_portfolio_id`) VALUES (2,'AMZN long',1);
INSERT INTO `strategy` (`id`,`strategy_name`,`strategy_portfolio_id`) VALUES (3,'ZM leap',2);
INSERT INTO `strategy` (`id`,`strategy_name`,`strategy_portfolio_id`) VALUES (4,'UPST leap',2);


INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1180780161,387679436,361181057,NULL,'2022-06-07','ZM','ZOOM VIDEO COMMUNICATIONS-A','STK',1,NULL,NULL,NULL,'BUY',15.0000,111.3300,1669.9500,0.0000,-1.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1180785204,387681643,370695082,NULL,'2022-06-07','FVRR','FIVERR INTERNATIONAL LTD','STK',1,NULL,NULL,NULL,'BUY',10.0000,40.5450,405.4500,0.0000,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1198688377,391765250,520934333,NULL,'2022-06-15','IBKR  220617P00055000','IBKR 17JUN22 55 P','OPT',100,'P',55.0000,'2022-06-17','BUY',1.0000,0.5500,55.0000,213.0981,-0.6492);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1198688378,391765279,539457903,NULL,'2022-06-15','IBKR  220916P00055000','IBKR 16SEP22 55 P','OPT',100,'P',55.0000,'2022-09-16','SELL',-1.0000,3.7000,-370.0000,0.0000,-0.6598);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1222538552,NULL,6478131,NULL,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWRE','STK',1,NULL,NULL,NULL,'SELL (Ca.)',0.7440,115.2709,85.7615,0.0000,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1222538553,NULL,6478131,NULL,'2022-06-28','TTWO','TAKE-TWO INTERACTIVE SOFTWRE','STK',1,NULL,NULL,NULL,'SELL',-0.7440,122.6900,-91.2814,-0.6919,0.0000);
INSERT INTO `trade` (`id`,`trade_id`,`con_id`,`trade_strategy_id`,`trade_date`,`symbol`,`description`,`asset_category`,`multiplier`,`put_call`,`strike`,`expiry`,`buy_sell`,`quantity`,`trade_price`,`trade_money`,`fifo_pnl_realized`,`ib_commission`) VALUES (1238155321,400172483,12087792,NULL,'2022-07-06','EUR.USD','EUR.USD','CASH',1,NULL,NULL,NULL,'BUY',1.0000,1.0174,1.0174,0.0000,-1.9484);
