-- Schema
CREATE TABLE crypto
(
    id_crypto SERIAL,
    name      VARCHAR(50) NOT NULL,
    symbol    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_crypto)
);

CREATE TABLE commission
(
    id_commission SERIAL,
    name          VARCHAR(50)    NOT NULL,
    val           NUMERIC(15, 2) NOT NULL,
    PRIMARY KEY (id_commission)
);

CREATE TABLE cours
(
    id_cours   SERIAL,
    date_cours TIMESTAMP      NOT NULL,
    pu         NUMERIC(15, 2) NOT NULL,
    id_crypto  INTEGER        NOT NULL,
    PRIMARY KEY (id_cours),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto)
);

CREATE TABLE type_mv_wallet
(
    id_type_mv_wallet SERIAL,
    name              VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_type_mv_wallet)
);

CREATE TABLE account
(
    id_account SERIAL,
    email      VARCHAR(50)    NOT NULL,
    pseudo     VARCHAR(50),
    fund       NUMERIC(15, 2) NOT NULL,
    PRIMARY KEY (id_account)
);

CREATE TABLE wallet
(
    id_wallet  SERIAL,
    quantity   NUMERIC(15, 2) NOT NULL,
    id_crypto  INTEGER        NOT NULL,
    id_account INTEGER,
    PRIMARY KEY (id_wallet),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE mv_wallet
(
    id_mv_wallet      SERIAL,
    date_mv           TIMESTAMP,
    quantity          INTEGER NOT NULL,
    id_wallet         INTEGER NOT NULL,
    id_type_mv_wallet INTEGER NOT NULL,
    PRIMARY KEY (id_mv_wallet),
    FOREIGN KEY (id_wallet) REFERENCES wallet (id_wallet),
    FOREIGN KEY (id_type_mv_wallet) REFERENCES type_mv_wallet (id_type_mv_wallet)
);

CREATE TABLE sale
(
    id_sale    SERIAL,
    date_sale  DATE    NOT NULL,
    id_account INTEGER NOT NULL,
    PRIMARY KEY (id_sale),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE sale_detail
(
    id_sale_detail SERIAL,
    quantity       NUMERIC(15, 2) NOT NULL,
    quantity_left  NUMERIC(15, 2) NOT NULL,
    id_crypto      INTEGER        NOT NULL,
    id_sale        INTEGER        NOT NULL,
    PRIMARY KEY (id_sale_detail),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_sale) REFERENCES sale (id_sale)
);

CREATE TABLE type_mv_fund
(
    id_type_mv_fund SERIAL,
    name            VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_type_mv_fund)
);

CREATE TABLE mv_fund
(
    id_mv_fund      SERIAL,
    date_mv         TIMESTAMP,
    quantity        INTEGER NOT NULL,
    id_source       INTEGER NOT NULL,
    id_type_mv_fund INTEGER NOT NULL,
    id_account      INTEGER NOT NULL,
    PRIMARY KEY (id_mv_fund),
    FOREIGN KEY (id_type_mv_fund) REFERENCES type_mv_fund (id_type_mv_fund),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE purchase
(
    id_purchase    SERIAL,
    date_purchase  DATE    NOT NULL,
    quantity       INTEGER NOT NULL,
    id_account     INTEGER NOT NULL,
    id_sale_detail INTEGER NOT NULL,
    PRIMARY KEY (id_purchase),
    FOREIGN KEY (id_account) REFERENCES account (id_account),
    FOREIGN KEY (id_sale_detail) REFERENCES sale_detail (id_sale_detail)
);


-- Data
INSERT INTO commission (name, val)
VALUES ('Commission Vente', 10),
       ('Commission Achat', 5);

------ 10 utilisateurs
INSERT INTO account (pseudo, email, fund)
VALUES ('Alice', 'alice@example.com', 10000),
       ('Bob', 'bob@example.com', 12000),
       ('Charlie', 'charlie@example.com', 15000),
       ('Diana', 'diana@example.com', 8000),
       ('Eve', 'eve@example.com', 9000),
       ('Frank', 'frank@example.com', 7000),
       ('Grace', 'grace@example.com', 9500),
       ('Hank', 'hank@example.com', 11000),
       ('Ivy', 'ivy@example.com', 10500),
       ('Jack', 'jack@example.com', 11500);

-- Insertion de données dans la table 'crypto'
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

-- Insertion de données dans la table 'type_mv_wallet'
INSERT INTO type_mv_wallet (name)
VALUES ('Dépôt'),
       ('Retrait'),
       ('Achat'),
       ('Vente'),
       ('Transfert');

-- Insertion de données dans la table 'type_mv_fund'
INSERT INTO type_mv_fund (name)
VALUES ('Dépôt'),
       ('Retrait'),
       ('Référencement');

-- Insertion de données dans la table 'account'
INSERT INTO account (pseudo, email, fund)
VALUES ('Alice', 'alice@example.com', 10000),
       ('Bob', 'bob@example.com', 12000),
       ('Charlie', 'charlie@example.com', 15000),
       ('Diana', 'diana@example.com', 8000),
       ('Eve', 'eve@example.com', 9000),
       ('Frank', 'frank@example.com', 7000),
       ('Grace', 'grace@example.com', 9500),
       ('Hank', 'hank@example.com', 11000),
       ('Ivy', 'ivy@example.com', 10500),
       ('Jack', 'jack@example.com', 11500);

-- Insertion de données dans la table 'wallet'
INSERT INTO wallet (quantity, id_crypto, id_account)
VALUES (100, 1, 1),
       (150, 1, 2),
       (200, 1, 3),
       (120, 2, 1);

-- Insertion de données dans la table 'mv_wallet' (exemple pour Alice et Bob)
-- Alice
INSERT INTO mv_wallet (date_mv, quantity, id_wallet, id_type_mv_wallet)
VALUES ('2025-01-08 10:00:00', 100, 1, 1),
       ('2025-01-08 11:30:00', 150, 1, 2),
       ('2025-01-09 12:00:00', 200, 1, 1),
       ('2025-01-09 14:00:00', 120, 1, 2),
       ('2025-01-09 15:00:00', 250, 2, 1),
       ('2025-01-09 16:30:00', 300, 2, 2),
       ('2025-01-09 18:00:00', 180, 2, 1),
       ('2025-01-09 19:00:00', 210, 3, 2),
       ('2025-01-10 09:00:00', 100, 3, 1),
       ('2025-01-10 10:30:00', 130, 3, 2),
       ('2025-01-10 11:00:00', 50, 1, 1),
       ('2025-01-10 12:00:00', 40, 2, 2),
       ('2025-01-10 14:00:00', 80, 3, 1),
       ('2025-01-10 15:00:00', 200, 1, 2),
       ('2025-01-10 16:00:00', 90, 2, 1),
       ('2025-01-10 17:00:00', 50, 3, 2),
       ('2025-01-10 18:00:00', 70, 1, 1),
       ('2025-01-10 19:00:00', 110, 2, 2);

-- Bob
INSERT INTO mv_wallet (date_mv, quantity, id_wallet, id_type_mv_wallet)
VALUES ('2025-01-08 09:00:00', 90, 1, 1),
       ('2025-01-08 11:00:00', 110, 1, 2),
       ('2025-01-08 12:30:00', 140, 2, 1),
       ('2025-01-09 14:00:00', 150, 2, 2),
       ('2025-01-09 15:00:00', 180, 2, 1),
       ('2025-01-09 16:00:00', 170, 3, 2),
       ('2025-01-09 18:00:00', 200, 3, 1),
       ('2025-01-10 09:30:00', 210, 3, 2),
       ('2025-01-10 10:00:00', 220, 3, 1),
       ('2025-01-10 11:30:00', 250, 4, 2),
       ('2025-01-10 12:30:00', 180, 1, 1),
       ('2025-01-10 13:00:00', 150, 2, 2),
       ('2025-01-10 14:30:00', 200, 3, 1),
       ('2025-01-10 15:30:00', 90, 4, 2),
       ('2025-01-10 16:00:00', 60, 1, 1),
       ('2025-01-10 17:30:00', 100, 2, 2),
       ('2025-01-10 18:00:00', 50, 3, 1);

-- Insertion de données dans la table 'cours' pour Bitcoin (50 changements de cours)
-- Changement de cours tous les 30 minutes
INSERT INTO cours (date_cours, pu, id_crypto)
VALUES ('2025-01-08 09:00:00', 30000, 1),
       ('2025-01-08 09:30:00', 30500, 1),
       ('2025-01-08 10:00:00', 31000, 1),
       ('2025-01-08 10:30:00', 31500, 1),
       ('2025-01-08 11:00:00', 32000, 1),
       ('2025-01-08 11:30:00', 32500, 1),
       ('2025-01-08 12:00:00', 33000, 1),
       ('2025-01-08 12:30:00', 33500, 1),
       ('2025-01-08 13:00:00', 34000, 1),
       ('2025-01-08 13:30:00', 34500, 1),
       ('2025-01-08 14:00:00', 35000, 1),
       ('2025-01-08 14:30:00', 35500, 1),
       ('2025-01-08 15:00:00', 36000, 1),
       ('2025-01-08 15:30:00', 36500, 1),
       ('2025-01-08 16:00:00', 37000, 1),
       ('2025-01-08 16:30:00', 37500, 1),
       ('2025-01-08 17:00:00', 38000, 1),
       ('2025-01-08 17:30:00', 38500, 1),
       ('2025-01-08 18:00:00', 39000, 1),
       ('2025-01-08 18:30:00', 39500, 1),
       ('2025-01-08 19:00:00', 40000, 1),
       ('2025-01-08 19:30:00', 40500, 1),
       ('2025-01-08 20:00:00', 41000, 1),
       ('2025-01-08 20:30:00', 41500, 1),
       ('2025-01-08 21:00:00', 42000, 1),
       ('2025-01-08 21:30:00', 42500, 1),
       ('2025-01-08 22:00:00', 43000, 1),
       ('2025-01-08 22:30:00', 43500, 1),
       ('2025-01-08 23:00:00', 44000, 1),
       ('2025-01-08 23:30:00', 44500, 1),
       ('2025-01-09 00:00:00', 45000, 1),
       ('2025-01-09 00:30:00', 45500, 1),
       ('2025-01-09 01:00:00', 46000, 1),
       ('2025-01-09 01:30:00', 46500, 1),
       ('2025-01-09 02:00:00', 47000, 1),
       ('2025-01-09 02:30:00', 47500, 1),
       ('2025-01-09 03:00:00', 48000, 1),
       ('2025-01-09 03:30:00', 48500, 1),
       ('2025-01-09 04:00:00', 49000, 1),
       ('2025-01-09 04:30:00', 49500, 1),
       ('2025-01-09 05:00:00', 50000, 1),
       ('2025-01-09 05:30:00', 50500, 1),
       ('2025-01-09 06:00:00', 51000, 1),
       ('2025-01-09 06:30:00', 51500, 1),
       ('2025-01-09 07:00:00', 52000, 1),
       ('2025-01-09 07:30:00', 52500, 1),
       ('2025-01-09 08:00:00', 53000, 1),
       ('2025-01-09 08:30:00', 53500, 1);

-- Insertion de données dans la table 'sale'
INSERT INTO sale (date_sale, id_account)
VALUES ('2025-01-08 13:00:00', 1),
       ('2025-01-09 14:30:00', 2),
       ('2025-01-10 15:30:00', 3);

-- Insertion de données dans la table 'sale_detail'
INSERT INTO sale_detail (quantity, quantity_left, id_crypto, id_sale)
VALUES (1000, 800, 1, 1),
       (1500, 1200, 2, 2),
       (2000, 1800, 3, 3);

-- Insertion de données dans la table 'purchase'
INSERT INTO purchase (date_purchase, quantity, id_account, id_sale_detail)
VALUES ('2025-01-09 11:30:00', 500, 1, 1),
       ('2025-01-10 12:30:00', 600, 2, 2);
