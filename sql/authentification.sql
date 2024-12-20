CREATE TABLE pending_register(
   id_pending_register SERIAL,
   email VARCHAR(50)  NOT NULL,
   password VARCHAR(250)  NOT NULL,
   date_register TIMESTAMP NOT NULL,
   date_expiration TIMESTAMP NOT NULL,
   date_validation TIMESTAMP,
   pin VARCHAR(250)  NOT NULL,
   PRIMARY KEY(id_pending_register)
);

CREATE TABLE type_account_state(
   id_type_account_state SERIAL,
   val VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id_type_account_state)
);

CREATE TABLE type_pending_auth(
   id_type_pending_auth SERIAL,
   val VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_pending_auth)
);

CREATE TABLE account(
   id_account SERIAL,
   email VARCHAR(50)  NOT NULL,
   password VARCHAR(250)  NOT NULL,
   attempt INTEGER NOT NULL DEFAULT 0,
   max_attempt INTEGER NOT NULL DEFAULT 3,
   id_pending_register INTEGER NOT NULL,
   id_type_account_state INTEGER NOT NULL,
   PRIMARY KEY(id_account),
   UNIQUE(id_pending_register),
   FOREIGN KEY(id_pending_register) REFERENCES pending_register(id_pending_register),
   FOREIGN KEY(id_type_account_state) REFERENCES type_account_state(id_type_account_state)
);

CREATE TABLE token(
   id_token SERIAL,
   date_expiration TIMESTAMP NOT NULL,
   token VARCHAR(250)  NOT NULL,
   id_account INTEGER NOT NULL,
   PRIMARY KEY(id_token),
   FOREIGN KEY(id_account) REFERENCES account(id_account)
);

CREATE TABLE account_state(
   id_account_state SERIAL,
   date_state TIMESTAMP NOT NULL,
   id_account INTEGER NOT NULL,
   id_type_account_state INTEGER NOT NULL,
   PRIMARY KEY(id_account_state),
   FOREIGN KEY(id_account) REFERENCES account(id_account),
   FOREIGN KEY(id_type_account_state) REFERENCES type_account_state(id_type_account_state)
);

CREATE TABLE pending_auth(
   id_pending_auth SERIAL,
   date_expiration TIMESTAMP NOT NULL,
   date_creation TIMESTAMP NOT NULL,
   pin VARCHAR(50)  NOT NULL,
   id_type_pending_auth INTEGER NOT NULL,
   id_account INTEGER NOT NULL,
   PRIMARY KEY(id_pending_auth),
   FOREIGN KEY(id_type_pending_auth) REFERENCES type_pending_auth(id_type_pending_auth),
   FOREIGN KEY(id_account) REFERENCES account(id_account)
);
