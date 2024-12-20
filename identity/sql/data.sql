INSERT INTO pending_register (email, password, date_register, date_expiration, date_validation, pin)
VALUES
('testuser1@example.com', 'hashed_password_1', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
('antema.fy01@gmail.com', 'hashed_password_1', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234');

INSERT INTO type_account_state (val)
VALUES
('Active'),
('Suspended');

CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO account (email, password, attempt, max_attempt, id_pending_register, id_type_account_state)
VALUES
('user1@example.com', crypt('hashed_password_1', gen_salt('bf')), 0, 3, 1, 1),
('antema.fy01@gmail.com', crypt('hashed_password_1', gen_salt('bf')), 0, 3, 2, 1);

INSERT INTO pending_auth (date_expiration, date_creation, pin, id_account)
VALUES
(NOW() + INTERVAL '30 days', NOW(), '12345', 1),
(NOW() + INTERVAL '20 days', NOW(), '67890', 2);
