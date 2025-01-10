\c postgres
drop database crypto;
create database crypto;
\c crypto;
\i crypto/database/schema.sql;
\i crypto/database/data.sql;
