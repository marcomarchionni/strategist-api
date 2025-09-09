-- liquibase formatted sql

-- changeset strategist:003-add-strategy-properties-v2
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_NAME = 'strategy' AND COLUMN_NAME = 'created_at'
ALTER TABLE `strategy` ADD COLUMN `created_at` DATE;

--preconditions onFail:MARK_RAN  
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_NAME = 'strategy' AND COLUMN_NAME = 'description'
ALTER TABLE `strategy` ADD COLUMN `description` VARCHAR(255);

UPDATE `strategy` SET `created_at` = CURRENT_DATE WHERE `created_at` IS NULL;
