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
VALUES ('Alice', 'alice@example.com', 'mypassword', 3, 0, 1, 10000),
       ('Bob', 'bob@example.com', 'mypassword', 3, 0, 2, 12000),
       ('Charlie', 'charlie@example.com', 'mypassword', 3, 0, 3, 15000),
       ('Diana', 'diana@example.com', 'mypassword', 3, 0, 4, 8000),
       ('Eve', 'eve@example.com', 'mypassword', 3, 0, 5, 9000),
       ('Frank', 'frank@example.com', 'mypassword', 3, 0, 6, 7000),
       ('Grace', 'grace@example.com', 'mypassword', 3, 0, 7, 9500),
       ('Hank', 'hank@example.com', 'mypassword', 3, 0, 8, 11000),
       ('Ivy', 'ivy@example.com', 'mypassword', 3, 0, 9, 10500),
       ('Jack', 'jack@example.com', 'mypassword', 3, 0, 10, 11500);
