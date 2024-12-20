UPDATE account
SET email = 'rd.jasonchris@gmail.com'
WHERE email = 'recipientgestion@gmail.com';

SELECT * FROM pending_auth WHERE id_account = (SELECT id_account FROM account WHERE email = 'antema.fy01@gmail.com') AND date_expiration > NOW();
