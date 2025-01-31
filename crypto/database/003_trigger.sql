-- on insert account
CREATE OR REPLACE FUNCTION innit_account_wallets()
    RETURNS TRIGGER AS
$$
DECLARE
    crypto_id INTEGER;
BEGIN
    -- Vérifiez si un wallet existe déjà pour cet account
    IF NOT EXISTS (SELECT 1
                   FROM wallet
                   WHERE id_account = NEW.id_account) THEN
        -- Si aucun wallet n'existe, insérez-en un pour chaque crypto
        FOR crypto_id IN (SELECT id_crypto FROM crypto)
            LOOP
                INSERT INTO wallet (quantity, id_crypto, id_account)
                VALUES (0, crypto_id, NEW.id_account);
            END LOOP;
    END IF;

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
    FOR account_id IN (SELECT id_account FROM account)
        LOOP
            INSERT INTO wallet (quantity, id_crypto, id_account)
            VALUES (0, NEW.id_crypto, account_id);
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_add_account_wallet
    AFTER INSERT
    ON crypto
    FOR EACH ROW
EXECUTE FUNCTION add_account_wallet();


-- on insert purchase
CREATE OR REPLACE FUNCTION add_commission_purchase()
    RETURNS TRIGGER AS
$$
DECLARE
    current_id_commission_sale       INTEGER;
    current_id_commission_purchase   INTEGER;
    current_rate_commission_sale     NUMERIC(15, 2);
    current_rate_commission_purchase NUMERIC(15, 2);
    amount_commission_sale           NUMERIC(15, 2);
    amount_commission_purchase       NUMERIC(15, 2);
BEGIN
    -- pour le vendeur
    select rate, id_commission_rate
    into current_rate_commission_sale, current_id_commission_sale
    from v_current_commission_rate vccr
    where vccr.id_commission_type = 1;

    amount_commission_sale := NEW.total_price + (NEW.total_price * current_rate_commission_sale);

    INSERT INTO commission_purchase (amount, id_purchase, id_commission_rate)
    VALUES (amount_commission_sale, NEW.id_purchase, current_id_commission_sale);

-- pour l'acheteur
    select rate, id_commission_rate
    into current_rate_commission_purchase, current_id_commission_purchase
    from v_current_commission_rate vccr
    where vccr.id_commission_type = 2;

    amount_commission_purchase := NEW.total_price + (NEW.total_price * current_rate_commission_purchase);

    INSERT INTO commission_purchase (amount, id_purchase, id_commission_rate)
    VALUES (amount_commission_purchase, NEW.id_purchase, current_id_commission_purchase);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_commission_purchase
    AFTER INSERT
    ON purchase
    FOR EACH ROW
EXECUTE FUNCTION add_commission_purchase();
