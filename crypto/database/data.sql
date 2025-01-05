INSERT INTO crypto_currency (name, symbol)
VALUES ('Bitcoin', 'BTC'),
       ('Ethereum', 'ETH'),
       ('Tether', 'USDT'),
       ('XRP', 'XRP'),
       ('USD Coin', 'USDC'),
       ('Binance Coin', 'BNB'),
       ('Cardano', 'ADA'),
       ('Polkadot', 'DOT'),
       ('Solana', 'SOL'),
       ('Chainlink', 'LINK');

INSERT INTO cours (price, daty, id_crypto_currency)
VALUES (50000.00, '2023-01-04', 1),
       (3000.00, '2023-01-05', 2),
       (100000.00, '2023-01-06', 3),
       (20000.00, '2023-01-07', 4),
       (150000.00, '2023-01-08', 5),
       (8000.00, '2023-01-09', 6),
       (40000.00, '2023-01-10', 7),
       (25000.00, '2023-01-11', 8),
       (70000.00, '2023-01-12', 9),
       (180000.00, '2023-01-13', 10);

INSERT INTO transaction_type (name)
VALUES ('Depot'),
       ('Retrait'),
       ('Vente'),
       ('Achat');

INSERT INTO account (pseudo, email, password, fund)
VALUES ('atlasss', 'henintsoapaul@gmail.com', 'mypassword', 500);

-- wallet
-- TODO: add triggers

-- transaction
INSERT INTO transaction (trans_date, trans_price, id_account_receiver, id_account_source, id_transaction_type)
VALUES ('2023-01-04', 50000, 1, 1, 1);

-- transaction_detail
INSERT INTO transaction_detail (quantity, detail_price, id_transaction, id_crypto_currency)
values (0, 50000, 1, 1);
