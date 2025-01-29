-- on insert account
CREATE OR REPLACE FUNCTION innit_account_wallets()
    RETURNS TRIGGER AS
$$
DECLARE
    crypto_id INTEGER;
BEGIN
    -- Boucler sur chaque crypto existant et créer un wallet pour le nouvel account
    FOR crypto_id IN (SELECT id_crypto FROM crypto) LOOP
            INSERT INTO wallet (quantity, id_crypto, id_account)
            VALUES (0, crypto_id, NEW.id_account);
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_innit_account_wallets
    AFTER INSERT
    ON account
    FOR EACH ROW
EXECUTE FUNCTION innit_account_wallets();

-- on insert crypto
CREATE OR REPLACE FUNCTION add_account_wallet()
    RETURNS TRIGGER AS
$$
DECLARE
    account_id INTEGER;
BEGIN
    -- Boucler sur chaque account existant et créer un wallet pour le nouvel crypto
    FOR account_id IN (SELECT id_account FROM account) LOOP
            INSERT INTO wallet (quantity, id_crypto, id_account)
            VALUES (0, NEW:id_crypto, account_id);
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_add_account_wallet
    AFTER INSERT
    ON crypto
    FOR EACH ROW
EXECUTE FUNCTION add_account_wallet();
