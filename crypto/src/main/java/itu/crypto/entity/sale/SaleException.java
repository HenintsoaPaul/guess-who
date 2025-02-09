package itu.crypto.entity.sale;

import itu.crypto.entity.wallet.Wallet;

public class SaleException extends Exception {
    private static final String NOT_ENOUGH_CRYPTO_ERROR = "Not enough cryptos in wallet. Crypto: %s, available: %d, required: %d";

    public SaleException(String message) {
        super(message);
    }

    public SaleException(SaleDetail saleDetail, Wallet wallet) {
        super(
                String.format(
                        NOT_ENOUGH_CRYPTO_ERROR,
                        saleDetail.getCrypto().getName(),
                        wallet.getQuantity(),
                        saleDetail.getQuantity()
                )
        );
    }
}
