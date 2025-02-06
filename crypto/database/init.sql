CREATE TABLE crypto
(
    id_crypto SERIAL,
    name      VARCHAR(250) NOT NULL,
    symbol    VARCHAR(5)   NOT NULL,
    PRIMARY KEY (id_crypto),
    UNIQUE (name),
    UNIQUE (symbol)
);

CREATE TABLE account
(
    id_account SERIAL,
    pseudo     VARCHAR(250) NOT NULL,
    email      VARCHAR(250) NOT NULL,
    password   VARCHAR(250) NOT NULL,
    fund       NUMERIC(15, 2),
    PRIMARY KEY (id_account)
);

CREATE TABLE cours
(
    id_cours   SERIAL,
    pu         NUMERIC(15, 2) NOT NULL,
    date_cours TIMESTAMP      NOT NULL,
    id_crypto  INTEGER        NOT NULL,
    PRIMARY KEY (id_cours),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto)
);

CREATE TABLE commission_type
(
    id_commission_type SERIAL,
    name               TEXT NOT NULL,
    symbol             VARCHAR(5),
    PRIMARY KEY (id_commission_type),
    UNIQUE (name),
    UNIQUE (symbol)
);

CREATE TABLE type_mv_fund
(
    id_type_mv_fund SERIAL,
    name            VARCHAR(250) NOT NULL,
    PRIMARY KEY (id_type_mv_fund),
    UNIQUE (name)
);

CREATE TABLE mv_fund
(
    id_mv_fund      SERIAL,
    date_mv         TIMESTAMP      NOT NULL,
    amount          NUMERIC(15, 2) NOT NULL,
    id_source       INTEGER,
    id_type_mv_fund INTEGER        NOT NULL,
    id_account      INTEGER        NOT NULL,
    PRIMARY KEY (id_mv_fund),
    FOREIGN KEY (id_type_mv_fund) REFERENCES type_mv_fund (id_type_mv_fund),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE type_mv_wallet
(
    id_type_mv_wallet SERIAL,
    name              VARCHAR(250) NOT NULL,
    PRIMARY KEY (id_type_mv_wallet),
    UNIQUE (name)
);

CREATE TABLE sale
(
    id_sale    SERIAL,
    date_sale  TIMESTAMP NOT NULL,
    id_account INTEGER   NOT NULL,
    PRIMARY KEY (id_sale),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE sale_detail
(
    id_sale_detail  SERIAL,
    quantity_crypto INTEGER NOT NULL,
    quantity_left   INTEGER NOT NULL,
    id_crypto       INTEGER NOT NULL,
    id_sale         INTEGER NOT NULL,
    PRIMARY KEY (id_sale_detail),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_sale) REFERENCES sale (id_sale)
);

CREATE TABLE crypto_fav
(
    id_crypto_fav   SERIAL,
    date_crypto_fav TIMESTAMP,
    id_crypto       INTEGER NOT NULL,
    id_account      INTEGER NOT NULL,
    PRIMARY KEY (id_crypto_fav),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE wallet
(
    id_wallet  SERIAL,
    quantity   INTEGER,
    id_crypto  INTEGER NOT NULL,
    id_account INTEGER NOT NULL,
    PRIMARY KEY (id_wallet),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);

CREATE TABLE purchase
(
    id_purchase          SERIAL,
    date_purchase        TIMESTAMP      NOT NULL,
    total_price          NUMERIC(15, 2) NOT NULL,
    unit_price           NUMERIC(15, 2) NOT NULL,
    quantity_crypto      INTEGER        NOT NULL,
    id_sale_detail       INTEGER        NOT NULL,
    id_account_seller    INTEGER        NOT NULL,
    id_account_purchaser INTEGER        NOT NULL,
    PRIMARY KEY (id_purchase),
    FOREIGN KEY (id_sale_detail) REFERENCES sale_detail (id_sale_detail),
    FOREIGN KEY (id_account_seller) REFERENCES account (id_account),
    FOREIGN KEY (id_account_purchaser) REFERENCES account (id_account)
);

CREATE TABLE commission_rate
(
    id_commission_rate SERIAL,
    rate               NUMERIC(15, 2) NOT NULL,
    add_date           TIMESTAMP      NOT NULL,
    id_commission_type INTEGER        NOT NULL,
    PRIMARY KEY (id_commission_rate),
    FOREIGN KEY (id_commission_type) REFERENCES commission_type (id_commission_type)
);

CREATE TABLE mv_wallet
(
    id_mv_wallet      SERIAL,
    date_mv           TIMESTAMP NOT NULL,
    quantity_crypto   INTEGER   NOT NULL,
    id_wallet         INTEGER   NOT NULL,
    id_type_mv_wallet INTEGER   NOT NULL,
    PRIMARY KEY (id_mv_wallet),
    FOREIGN KEY (id_wallet) REFERENCES wallet (id_wallet),
    FOREIGN KEY (id_type_mv_wallet) REFERENCES type_mv_wallet (id_type_mv_wallet)
);

CREATE TABLE commission_purchase
(
    id_commission_purchase SERIAL,
    amount                 NUMERIC(15, 2) NOT NULL,
    id_purchase            INTEGER        NOT NULL,
    id_commission_rate     INTEGER        NOT NULL,
    PRIMARY KEY (id_commission_purchase),
    FOREIGN KEY (id_purchase) REFERENCES purchase (id_purchase),
    FOREIGN KEY (id_commission_rate) REFERENCES commission_rate (id_commission_rate)
);


create or replace view v_current_commission_rate as
select tab1.*
from commission_rate tab1
         join (select id_commission_type, max(add_date) as dd
               from commission_rate cr
               group by cr.id_commission_type) tab2
              on tab1.id_commission_type = tab2.id_commission_type and tab1.add_date = tab2.dd;

-- on insert account
CREATE OR REPLACE FUNCTION innit_account_wallets()
    RETURNS TRIGGER AS
$$
DECLARE
    crypto_id INTEGER;
BEGIN
    -- Vérifiez si un wallet existe déjà pour cet account
    IF NOT EXISTS (SELECT 1
                   FROM wallet
                   WHERE id_account = NEW.id_account) THEN
        -- Si aucun wallet n'existe, insérez-en un pour chaque crypto
        FOR crypto_id IN (SELECT id_crypto FROM crypto)
            LOOP
                INSERT INTO wallet (quantity, id_crypto, id_account)
                VALUES (0, crypto_id, NEW.id_account);
            END LOOP;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_innit_account_wallets
    AFTER INSERT
    ON account
    FOR EACH ROW
EXECUTE FUNCTION innit_account_wallets();



-- on insert crypto
CREATE OR REPLACE FUNCTION add_account_wallet()
    RETURNS TRIGGER AS
$$
DECLARE
    account_id INTEGER;
BEGIN
    -- Boucler sur chaque account existant et créer un wallet pour le nouvel crypto
    FOR account_id IN (SELECT id_account FROM account)
        LOOP
            INSERT INTO wallet (quantity, id_crypto, id_account)
            VALUES (0, NEW.id_crypto, account_id);
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_add_account_wallet
    AFTER INSERT
    ON crypto
    FOR EACH ROW
EXECUTE FUNCTION add_account_wallet();


-- on insert purchase
CREATE OR REPLACE FUNCTION add_commission_purchase()
    RETURNS TRIGGER AS
$$
DECLARE
    current_id_commission_sale       INTEGER;
    current_id_commission_purchase   INTEGER;
    current_rate_commission_sale     NUMERIC(15, 2);
    current_rate_commission_purchase NUMERIC(15, 2);
    amount_commission_sale           NUMERIC(15, 2);
    amount_commission_purchase       NUMERIC(15, 2);
BEGIN
    -- pour le vendeur
    select rate, id_commission_rate
    into current_rate_commission_sale, current_id_commission_sale
    from v_current_commission_rate vccr
    where vccr.id_commission_type = 1;

    amount_commission_sale := NEW.total_price + (NEW.total_price * current_rate_commission_sale);

    INSERT INTO commission_purchase (amount, id_purchase, id_commission_rate)
    VALUES (amount_commission_sale, NEW.id_purchase, current_id_commission_sale);

-- pour l'acheteur
    select rate, id_commission_rate
    into current_rate_commission_purchase, current_id_commission_purchase
    from v_current_commission_rate vccr
    where vccr.id_commission_type = 2;

    amount_commission_purchase := NEW.total_price + (NEW.total_price * current_rate_commission_purchase);

    INSERT INTO commission_purchase (amount, id_purchase, id_commission_rate)
    VALUES (amount_commission_purchase, NEW.id_purchase, current_id_commission_purchase);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_commission_purchase
    AFTER INSERT
    ON purchase
    FOR EACH ROW
EXECUTE FUNCTION add_commission_purchase();


INSERT INTO commission_type (name, symbol)
VALUES ('Commission Vente', 'CV'),
       ('Commission Achat', 'CA');

INSERT INTO commission_rate (rate, add_date, id_commission_type)
VALUES (0.1, '2020-01-01', 1),
       (0.05,'2020-01-01', 2);

-- Insertion de données dans la table 'type_mv_wallet'
INSERT INTO type_mv_wallet (name)
VALUES ('Acquisition apres achat'),
       ('Cession apres vente');

-- Insertion de données dans la table 'type_mv_fund'
INSERT INTO type_mv_fund (name)
VALUES ('Dépôt'),
       ('Retrait'),
       ('Achat'),
       ('Vente');

-- 10 crypto
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

------ 10 utilisateurs
INSERT INTO account (pseudo, email, password, fund)
VALUES ('Alice', 'rocruxappafra-4143@yopmail.com', 'mypassword', 10000),
       ('Bob', 'frucodillefeu-1226@yopmail.com', 'mypassword', 12000),
       ('Charlie', 'giyoibrappoihou-3938@yopmail.com', 'mypassword', 15000),
       ('Diana', 'barauxinulli-4329@yopmail.com', 'mypassword', 8000),
       ('Eve', 'meiwoussadate-5662@yopmail.com', 'mypassword', 9000),
       ('Frank', 'boifoissecoullu-3728@yopmail.com', 'mypassword', 7000),
       ('Grace', 'yettennevuffu-9049@yopmail.com', 'mypassword', 9500),
       ('Hank', 'baviwagrouye-6787@yopmail.com', 'mypassword', 11000),
       ('Ivy', 'bappoppotameu-6100@yopmail.com', 'mypassword', 10500),
       ('Jack', 'nopeummeuxoigrau-1855@yopmail.com', 'mypassword', 11500);

-- -- Insertion de données dans la table 'wallet'
-- INSERT INTO wallet (quantity, id_crypto, id_account)
-- VALUES (100, 1, 1),
--        (150, 1, 2),
--        (200, 1, 3),
--        (120, 2, 1);

-- Insertion de données dans la table 'mv_wallet' (exemple pour Alice et Bob)
-- Alice
INSERT INTO mv_wallet (date_mv, quantity_crypto, id_wallet, id_type_mv_wallet)
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
INSERT INTO mv_wallet (date_mv, quantity_crypto, id_wallet, id_type_mv_wallet)
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

-- Init des cours pour tout les crypto
INSERT INTO cours (date_cours, pu, id_crypto)
VALUES ('2025-01-08 09:00:00', 30000, 1),
       ('2025-01-08 09:30:00', 30500, 2),
       ('2025-01-08 10:00:00', 31000, 3),
       ('2025-01-08 10:30:00', 31500, 4),
       ('2025-01-08 11:00:00', 32000, 5),
       ('2025-01-08 11:30:00', 32500,6),
       ('2025-01-08 12:00:00', 33000, 7),
       ('2025-01-08 12:30:00', 33500, 8),
       ('2025-01-08 13:00:00', 34000, 9),
       ('2025-01-08 13:30:00', 34500, 10);

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
INSERT INTO sale_detail (quantity_crypto, quantity_left, id_crypto, id_sale)
VALUES (1000, 800, 1, 1),
       (1500, 1200, 2, 2),
       (2000, 1800, 3, 3);

-- Insertion de données dans la table 'purchase'
INSERT INTO purchase (date_purchase, total_price, unit_price, quantity_crypto, id_account_purchaser, id_account_seller, id_sale_detail)
VALUES ('2025-01-09 11:30:00', 500, 500, 1, 1, 2, 1),
       ('2025-01-10 12:30:00', 600, 600, 1, 2, 1, 2);
