UPDATE accounts
SET email = 'rd.jasonchris@gmail.com'
WHERE email = 'recipientgestion@gmail.com';

SELECT * FROM pending_auths WHERE id_account = (SELECT id_account FROM accounts WHERE email = 'rd.jasonchris@gmail.com') AND date_expiration > NOW();
