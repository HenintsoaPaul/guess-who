CREATE TABLE crypto_currency(
   id_crypto_currency SERIAL,
   name VARCHAR(250)  NOT NULL,
   symbol VARCHAR(5)  NOT NULL,
   PRIMARY KEY(id_crypto_currency),
   UNIQUE(name),
   UNIQUE(symbol)
);

CREATE TABLE account(
   id_account SERIAL,
   pseudo VARCHAR(250)  NOT NULL,
   email VARCHAR(250)  NOT NULL,
   fund NUMERIC(15,2)  ,
   PRIMARY KEY(id_account)
);

CREATE TABLE transaction_type(
   id_transaction_type SERIAL,
   name VARCHAR(250)  NOT NULL,
   PRIMARY KEY(id_transaction_type),
   UNIQUE(name)
);

CREATE TABLE cours(
   id_cours SERIAL,
   price NUMERIC(15,2)   NOT NULL,
   daty DATE NOT NULL,
   id_crypto_currency INTEGER NOT NULL,
   PRIMARY KEY(id_cours),
   FOREIGN KEY(id_crypto_currency) REFERENCES crypto_currency(id_crypto_currency)
);

CREATE TABLE wallet(
   id_wallet SERIAL,
   quantity INTEGER,
   id_crypto_currency INTEGER NOT NULL,
   id_account INTEGER NOT NULL,
   PRIMARY KEY(id_wallet),
   UNIQUE(id_account),
   FOREIGN KEY(id_crypto_currency) REFERENCES crypto_currency(id_crypto_currency),
   FOREIGN KEY(id_account) REFERENCES account(id_account)
);

CREATE TABLE transaction(
   id_transaction SERIAL,
   trans_date DATE NOT NULL,
   trans_price NUMERIC(15,2)   NOT NULL,
   id_account_receiver INTEGER,
   id_account_source INTEGER NOT NULL,
   id_transaction_type INTEGER NOT NULL,
   PRIMARY KEY(id_transaction),
   FOREIGN KEY(id_account_receiver) REFERENCES account(id_account),
   FOREIGN KEY(id_account_source) REFERENCES account(id_account),
   FOREIGN KEY(id_transaction_type) REFERENCES transaction_type(id_transaction_type)
);

CREATE TABLE transaction_detail(
   id_transaction_detail SERIAL,
   quantity INTEGER NOT NULL,
   detail_price NUMERIC(15,2)   NOT NULL,
   id_transaction INTEGER NOT NULL,
   id_crypto_currency INTEGER NOT NULL,
   PRIMARY KEY(id_transaction_detail),
   FOREIGN KEY(id_transaction) REFERENCES transaction(id_transaction),
   FOREIGN KEY(id_crypto_currency) REFERENCES crypto_currency(id_crypto_currency)
);
