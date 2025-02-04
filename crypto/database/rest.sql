\c postgres
drop database crypto;
create database crypto;
\c crypto;
\i crypto/database/001_crypto.sql;
\i crypto/database/003_data.sql;
