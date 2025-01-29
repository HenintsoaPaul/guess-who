package itu.crypto.service;

import itu.crypto.dto.SaleFormData;
import itu.crypto.entity.Account;
import itu.crypto.entity.Sale;
import itu.crypto.entity.SaleDetail;
import itu.crypto.entity.Wallet;
import itu.crypto.repository.transaction.SaleDetailRepository;
import itu.crypto.repository.transaction.SaleRepository;
import itu.crypto.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final WalletRepository walletRepository;

    public Sale findById(int id) {
	return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    }

    public List<Sale> findAllByIdAccount(Integer idAccount) {
	return saleRepository.findAllByIdAccount(idAccount);
    }

    public List<SaleDetail> findAllSaleDetails(Sale sale) {
	return saleDetailRepository.findAllBySale(sale);
    }

    public List<Wallet> findAllWallets(Account account) {
	return walletRepository.findAllByAccount(account);
    }

    private static final String NOT_ENOUGH_CRYPTO_ERROR = "Not enough cryptos in wallet. Crypto: %s, available: %d, required: %d";

    private boolean areWalletsCapable(List<Wallet> wallets, List<SaleDetail> saleDetails) throws Exception {
	for (SaleDetail saleDetail : saleDetails) {
	    validateWalletCryptoQuantity(wallets, saleDetail);
	}
	return true;
    }

    private void validateWalletCryptoQuantity(List<Wallet> wallets, SaleDetail saleDetail) throws Exception {
	for (Wallet wallet : wallets) {
	    if (wallet.getCrypto().equals(saleDetail.getCrypto()) && wallet.getQuantity() < saleDetail.getQuantity()) {
		throw new Exception(String.format(NOT_ENOUGH_CRYPTO_ERROR, saleDetail.getCrypto(), wallet.getQuantity(),
			saleDetail.getQuantity()));
	    }
	}
    }

    @Transactional
    public void save(SaleFormData saleFormData) throws Exception {
	Sale sale = saleFormData.getSale();
	List<SaleDetail> saleDetails = saleFormData.getSaleDetails();

	// Verifier que les Crypto dans mon wallet suffisent pour faire la vente
	List<Wallet> wallets = this.findAllWallets(sale.getAccount());
	areWalletsCapable(wallets, saleDetails);

	System.out.println(saleFormData);

	saleRepository.save(sale);

	for (SaleDetail saleDetail : saleDetails) {
	    saleDetail.setSale(sale);
	    saleDetail.setQuantityLeft(saleDetail.getQuantity());

	    saleDetailRepository.save(saleDetail);
	}
    }
}
