CREATE SCHEMA currency_service;
CREATE TABLE IF NOT EXISTS currency_service.currencies_rates(id bigint  auto_increment NOT NULL PRIMARY KEY, code character NOT NULL UNIQUE, rate double NOT NULL, is_enabled bit);
INSERT INTO currency_service.currencies_rates(code, rate, is_enabled) VALUES('USD', 93.14, 1);
INSERT INTO currency_service.currencies_rates(code, rate, is_enabled) VALUES('EUR', 101.58, 1);
INSERT INTO currency_service.currencies_rates(code, rate, is_enabled) VALUES('RUB', 1.00, 1);
INSERT INTO currency_service.currencies_rates(code, rate, is_enabled) VALUES('GBP', 133.71, 0);

