-- liquibase formatted sql

-- changeset strategist:004-add-strategy-descriptions
UPDATE `strategy` SET `description` = 'Long-term bullish position on Zoom Video Communications, expecting continued growth in remote work solutions' WHERE `name` = 'ZM long';

UPDATE `strategy` SET `description` = 'Protective put strategy on Interactive Brokers stock to hedge against potential market volatility' WHERE `name` = 'IBKR put';

UPDATE `strategy` SET `description` = 'Value investment in Disney, betting on recovery of theme parks and streaming services growth' WHERE `name` = 'DIS long';

UPDATE `strategy` SET `description` = 'Growth play on Nike, capitalizing on global athletic wear market expansion and brand strength' WHERE `name` = 'NKE long';

UPDATE `strategy` SET `description` = 'Covered call strategy on eBay to generate income while maintaining upside potential' WHERE `name` = 'EBAY covcall';

UPDATE `strategy` SET `description` = 'Speculative position on iRobot, anticipating growth in home automation and robotics market' WHERE `name` = 'IRBT long';

UPDATE `strategy` SET `description` = 'Gaming industry play on Take-Two Interactive, leveraging strong game portfolio and digital transformation' WHERE `name` = 'TTWO long';
