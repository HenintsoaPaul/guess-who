INSERT INTO crypto (name, symbol)
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

INSERT INTO cours (date_cours, pu, id_crypto)
VALUES ('2023-01-04', 50000.00, 1),
       ('2023-01-05', 3000.00, 2),
       ('2023-01-06', 100000.00, 3),
       ('2023-01-07', 20000.00, 4),
       ('2023-01-08', 150000.00, 5),
       ('2023-01-09', 8000.00, 6),
       ('2023-01-10', 40000.00, 7),
       ('2023-01-11', 25000.00, 8),
       ('2023-01-12', 70000.00, 9),
       ('2023-01-13', 180000.00, 10);

INSERT INTO type_mv_wallet (name)
VALUES ('Depot'),
       ('Retrait'),
       ('Vente'),
       ('Achat');

INSERT INTO account (email, pseudo, fund)
VALUES ('henintsoapaul@gmail.com', 'atlasss', 500);

-- wallet
-- TODO: add triggers

-- sale
INSERT INTO sale (date_sale, id_account)
VALUES ('2023-01-04', 1),
       ('2023-01-05', 1);

-- sale_detail
INSERT INTO sale_detail (quantity, quantity_left, id_crypto, id_sale)
values (5, 5, 1, 1),
       (4, 4, 2, 1),
       (5, 5, 1, 2),
       (4, 4, 2, 2);
