-- Insérer les états de compte
INSERT INTO type_account_states (val)
VALUES ('unlocked'), ('locked');

-- Insérer un pending register
INSERT INTO pending_registers (email, password, date_register, date_expiration, date_validation, pin)
VALUES (
    'user@example.com', 
    '$2y$10$eW5TaHhvbk5ka0hFVG1sFuj1K1CrlwnVNoYPxy1JXBtlwNpVw3pUy', -- Mot de passe hashé (ex: "password")
    NOW(), 
    NOW() + INTERVAL '1 day', 
    NOW(), 
    '123456'
);

-- Insérer un compte
INSERT INTO accounts (email, password, attempt, max_attempt, id_pending_register, id_type_account_state)
VALUES (
    'user@example.com', 
    '$2y$10$eW5TaHhvbk5ka0hFVG1sFuj1K1CrlwnVNoYPxy1JXBtlwNpVw3pUy', -- Mot de passe hashé
    0, 
    3, 
    (SELECT Id_pending_register FROM pending_registers WHERE email = 'user@example.com'), 
    (SELECT id_type_account_state FROM type_account_states WHERE val = 'unlocked')
);
