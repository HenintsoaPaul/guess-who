INSERT INTO commission (name, val)
VALUES ('Commission Vente', 10),
       ('Commission Achat', 5);

------ 10 utilisateurs
INSERT INTO account (pseudo, email, fund)
VALUES ('Alice', 'rocruxappafra-4143@yopmail.com', 10000),
       ('Bob', 'frucodillefeu-1226@yopmail.com', 12000),
       ('Charlie', 'giyoibrappoihou-3938@yopmail.com', 15000),
       ('Diana', 'barauxinulli-4329@yopmail.com', 8000),
       ('Eve', 'meiwoussadate-5662@yopmail.com', 9000),
       ('Frank', 'boifoissecoullu-3728@yopmail.com', 7000),
       ('Grace', 'yettennevuffu-9049@yopmail.com', 9500),
       ('Hank', 'baviwagrouye-6787@yopmail.com', 11000),
       ('Ivy', 'bappoppotameu-6100@yopmail.com', 10500),
       ('Jack', 'nopeummeuxoigrau-1855@yopmail.com', 11500);

---- Cryptomonnaie
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

-----  Types de transactions
INSERT INTO transaction_type (name)
VALUES ('Achat'),
       ('Vente');

------ cours des cryptomonnaie
INSERT INTO cours (price, daty, id_crypto)
VALUES
-- Cours pour Bitcoin (BTC)
(43000, CURRENT_DATE - 4, 1),
(43500, CURRENT_DATE - 3, 1),
(44000, CURRENT_DATE - 2, 1),
(44500, CURRENT_DATE - 1, 1),
(45000, CURRENT_DATE, 1),

-- Cours pour Ethereum (ETH)
(3000, CURRENT_DATE - 4, 2),
(3050, CURRENT_DATE - 3, 2),
(3100, CURRENT_DATE - 2, 2),
(3200, CURRENT_DATE - 1, 2),
(3300, CURRENT_DATE, 2),

-- Cours pour Tether (USDT)
(1.00, CURRENT_DATE - 4, 3),
(1.01, CURRENT_DATE - 3, 3),
(1.01, CURRENT_DATE - 2, 3),
(1.02, CURRENT_DATE - 1, 3),
(1.03, CURRENT_DATE, 3),

-- Cours pour XRP
(0.50, CURRENT_DATE - 4, 4),
(0.55, CURRENT_DATE - 3, 4),
(0.60, CURRENT_DATE - 2, 4),
(0.65, CURRENT_DATE - 1, 4),
(0.70, CURRENT_DATE, 4),

-- Cours pour USD Coin (USDC)
(1.00, CURRENT_DATE - 4, 5),
(1.00, CURRENT_DATE - 3, 5),
(1.01, CURRENT_DATE - 2, 5),
(1.01, CURRENT_DATE - 1, 5),
(1.02, CURRENT_DATE, 5),

-- Cours pour Binance Coin (BNB)
(280, CURRENT_DATE - 4, 6),
(285, CURRENT_DATE - 3, 6),
(290, CURRENT_DATE - 2, 6),
(295, CURRENT_DATE - 1, 6),
(300, CURRENT_DATE, 6),

-- Cours pour Cardano (ADA)
(0.45, CURRENT_DATE - 4, 7),
(0.46, CURRENT_DATE - 3, 7),
(0.47, CURRENT_DATE - 2, 7),
(0.48, CURRENT_DATE - 1, 7),
(0.50, CURRENT_DATE, 7),

-- Cours pour Polkadot (DOT)
(5.00, CURRENT_DATE - 4, 8),
(5.10, CURRENT_DATE - 3, 8),
(5.20, CURRENT_DATE - 2, 8),
(5.30, CURRENT_DATE - 1, 8),
(5.40, CURRENT_DATE, 8),

-- Cours pour Solana (SOL)
(20.00, CURRENT_DATE - 4, 9),
(20.50, CURRENT_DATE - 3, 9),
(21.00, CURRENT_DATE - 2, 9),
(21.50, CURRENT_DATE - 1, 9),
(22.00, CURRENT_DATE, 9),

-- Cours pour Chainlink (LINK)
(6.00, CURRENT_DATE - 4, 10),
(6.10, CURRENT_DATE - 3, 10),
(6.20, CURRENT_DATE - 2, 10),
(6.30, CURRENT_DATE - 1, 10),
(6.50, CURRENT_DATE, 10);


---- Transaction
INSERT INTO transaction (trans_date, trans_price, id_account_receiver, id_account_source, id_transaction_type)
VALUES
-- Transactions pour Alice (id_account = 1)
(CURRENT_DATE - 2, 45000, NULL, 1, 2), -- Vente de 1 BTC
(CURRENT_DATE - 1, 3200, 1, 2, 1),     -- Achat de 1 ETH
(CURRENT_DATE, 1.03, NULL, 1, 2),      -- Vente de 500 USDT

-- Transactions pour Bob (id_account = 2)
(CURRENT_DATE - 2, 45000, 2, 1, 1),    -- Achat de 1 BTC à Alice
(CURRENT_DATE - 1, 4500, NULL, 2, 2),  -- Vente de 10 ADA
(CURRENT_DATE, 3300, NULL, 2, 2),      -- Vente de 5 ETH

-- Transactions pour Charlie (id_account = 3)
(CURRENT_DATE - 2, 1.02, NULL, 3, 2),  -- Vente de 1000 USDT
(CURRENT_DATE - 1, 44000, 3, 4, 1),    -- Achat de 1 BTC
(CURRENT_DATE, 1.01, NULL, 3, 2),      -- Vente de 2000 USDT

-- Transactions pour Diana (id_account = 4)
(CURRENT_DATE - 2, 3100, 4, 3, 1),     -- Achat de 1 ETH
(CURRENT_DATE - 1, 2000, NULL, 4, 2),  -- Vente de 10 SOL
(CURRENT_DATE, 50, NULL, 4, 2),        -- Vente de 50 LINK

-- Transactions pour Eve (id_account = 5)
(CURRENT_DATE - 2, 1000, NULL, 5, 2),  -- Vente de 100 DOT
(CURRENT_DATE - 1, 3100, 5, 6, 1),     -- Achat de 1 ETH
(CURRENT_DATE, 1.02, 5, 7, 1),         -- Achat de 500 USDT

-- Transactions pour Frank (id_account = 6)
(CURRENT_DATE - 2, 8800, 6, 1, 1),     -- Achat de 2 SOL
(CURRENT_DATE - 1, 1500, NULL, 6, 2),  -- Vente de 5 DOT
(CURRENT_DATE, 450, 6, 3, 1),          -- Achat de 1 ADA

-- Transactions pour Grace (id_account = 7)
(CURRENT_DATE - 2, 1000, NULL, 7, 2),  -- Vente de 50 LINK
(CURRENT_DATE - 1, 30000, 7, 5, 1),    -- Achat de 0.75 BTC
(CURRENT_DATE, 1.05, NULL, 7, 2),      -- Vente de 1000 USDT

-- Transactions pour Harry (id_account = 8)
(CURRENT_DATE - 2, 22000, NULL, 8, 2), -- Vente de 0.5 BTC
(CURRENT_DATE - 1, 6000, 8, 9, 1),     -- Achat de 3 ETH
(CURRENT_DATE, 200, NULL, 8, 2),       -- Vente de 20 ADA

-- Transactions pour Ivy (id_account = 9)
(CURRENT_DATE - 2, 1.04, NULL, 9, 2),  -- Vente de 1000 USDT
(CURRENT_DATE - 1, 3400, 9, 10, 1),    -- Achat de 1 ETH
(CURRENT_DATE, 100, NULL, 9, 2),       -- Vente de 1 LINK

-- Transactions pour Jack (id_account = 10)
(CURRENT_DATE - 2, 4500, NULL, 10, 2), -- Vente de 10 ADA
(CURRENT_DATE - 1, 33000, 10, 1, 1),   -- Achat de 0.75 BTC
(CURRENT_DATE, 1.02, 10, 7, 1);
-- Achat de 500 USDT

----- DetailTransaction
INSERT INTO transaction_detail (quantity, detail_price, id_transaction, id_crypto)
VALUES
-- Détails pour Alice
(1, 45000, 1, 1),     -- 1 BTC vendu
(1, 3200, 2, 2),      -- 1 ETH acheté
(500, 1.03, 3, 3),    -- 500 USDT vendu

-- Détails pour Bob
(1, 45000, 4, 1),     -- 1 BTC acheté
(10, 450, 5, 7),      -- 10 ADA vendu
(5, 3300, 6, 2),      -- 5 ETH vendu

-- Détails pour Charlie
(1000, 1.02, 7, 3),   -- 1000 USDT vendu
(1, 44000, 8, 1),     -- 1 BTC acheté
(2000, 1.01, 9, 3),   -- 2000 USDT vendu

-- Détails pour Diana
(1, 3100, 10, 2),     -- 1 ETH acheté
(10, 200, 11, 9),     -- 10 SOL vendu
(50, 1, 12, 8),       -- 50 LINK vendu

-- Détails pour Eve
(100, 10, 13, 6),     -- 100 DOT vendu
(1, 3100, 14, 2),     -- 1 ETH acheté
(500, 1.02, 15, 3),   -- 500 USDT acheté

-- Détails pour Frank
(2, 4400, 16, 9),     -- 2 SOL acheté
(5, 300, 17, 6),      -- 5 DOT vendu
(1, 450, 18, 7),      -- 1 ADA acheté

-- Détails pour Grace
(50, 20, 19, 8),      -- 50 LINK vendu
(0.75, 40000, 20, 1), -- 0.75 BTC acheté
(1000, 1.05, 21, 3),  -- 1000 USDT vendu

-- Détails pour Harry
(0.5, 44000, 22, 1),  -- 0.5 BTC vendu
(3, 2000, 23, 2),     -- 3 ETH acheté
(20, 10, 24, 7),      -- 20 ADA vendu

-- Détails pour Ivy
(1000, 1.04, 25, 3),  -- 1000 USDT vendu
(1, 3400, 26, 2),     -- 1 ETH acheté
(1, 100, 27, 8),      -- 1 LINK vendu

-- Détails pour Jack
(10, 450, 28, 7),     -- 10 ADA vendu
(0.75, 44000, 29, 1), -- 0.75 BTC acheté
(500, 1.02, 30, 3); -- 500 USDT acheté

