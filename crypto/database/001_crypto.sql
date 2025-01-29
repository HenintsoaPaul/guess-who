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

CREATE TABLE commission
(
    id_commission      SERIAL,
    val                NUMERIC(15, 2) NOT NULL,
    daty               TIMESTAMP      NOT NULL,
    id_commission_type INTEGER        NOT NULL,
    PRIMARY KEY (id_commission),
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

CREATE TABLE crypto_fav
(
    id_crypto  INTEGER,
    id_account INTEGER,
    date_fav   TIMESTAMP,
    PRIMARY KEY (id_crypto, id_account),
    FOREIGN KEY (id_crypto) REFERENCES crypto (id_crypto),
    FOREIGN KEY (id_account) REFERENCES account (id_account)
);
