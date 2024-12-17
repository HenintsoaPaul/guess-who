INSERT INTO pending_registers (email, password, date_register, date_expiration, date_validation, pin)
VALUES 
('testuser1@example.com', 'hashed_password_1', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234');

INSERT INTO type_account_states (val)
VALUES 
('Active'),
('Inactive'),
('Suspended');

INSERT INTO accounts (email, password, attempt, max_attempt, id_pending_register, id_type_account_state)
VALUES 
('user1@example.com', 'hashed_password_1', 0, 3, 1, 1);
