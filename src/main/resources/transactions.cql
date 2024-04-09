WITH KEYSPACE currency_rates
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

WITH TABLE currency_rates.rates (
      currency_code text PRIMARY KEY,
      rate decimal
);

INSERT INTO currency_rates.rates (currency_code, rate) VALUES ('USD', 1.0);
INSERT INTO currency_rates.rates (currency_code, rate) VALUES ('EUR', 0.85);
INSERT INTO currency_rates.rates (currency_code, rate) VALUES ('GBP', 0.75);