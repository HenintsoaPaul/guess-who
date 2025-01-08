CREATE TABLE crypto
(
    id_crypto SERIAL,
    name      VARCHAR(50) NOT NULL,
    symbol    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_crypto)
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
    pseudo     VARCHAR(50)    NOT NULL,
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
