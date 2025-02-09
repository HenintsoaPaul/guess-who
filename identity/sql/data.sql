INSERT INTO type_account_state (val)
VALUES ('Active'),
       ('Suspended');

INSERT INTO pending_register (email, password, date_register, date_expiration, date_validation, pin)
VALUES ('alice@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('bob@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('charlie@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('diana@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('eve@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('frank@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('grace@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('hank@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('ivy@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234'),
       ('jack@example.com', 'mypassword', NOW(), NOW() + INTERVAL '30 days', NOW(), '1234');

INSERT INTO account (email, password, attempt, max_attempt, id_pending_register, id_type_account_state)
VALUES ('alice@example.com', 'mypassword', 0, 3, 1, 1),
       ('bob@example.com', 'mypassword', 1, 3, 2, 1),
       ('charlie@example.com', 'mypassword', 3, 0, 3, 1),
       ('diana@example.com', 'mypassword', 0, 3, 4, 2),
       ('eve@example.com', 'mypassword', 0, 3, 5, 2),
       ('frank@example.com', 'mypassword', 0, 3, 6, 1),
       ('grace@example.com', 'mypassword', 0, 3, 7, 1),
       ('hank@example.com', 'mypassword', 1, 3, 8, 1),
       ('ivy@example.com', 'mypassword', 1, 3, 9, 1),
       ('jack@example.com', 'mypassword', 1, 3, 10, 1);
