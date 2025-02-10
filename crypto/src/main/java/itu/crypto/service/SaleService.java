package itu.crypto.service;

import itu.crypto.dto.SaleFormData;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.sale.Sale;
import itu.crypto.entity.sale.SaleDetail;
import itu.crypto.entity.sale.SaleException;
import itu.crypto.entity.wallet.Wallet;
import itu.crypto.repository.transaction.SaleDetailRepository;
import itu.crypto.repository.transaction.SaleRepository;
import itu.crypto.repository.transaction.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final WalletRepository walletRepository;

    public Sale findById(int id) {
        return saleRepository.findById(id).orElseThrow();
    }

    public List<Sale> findAllByIdAccount(Integer idAccount) {
        return saleRepository.findAllByIdAccount(idAccount);
    }

    public List<SaleDetail> findAllSaleDetails(Sale sale) {
        return saleDetailRepository.findAllBySale(sale);
    }

    public List<SaleDetail> findAllDispo() {
        return saleDetailRepository.findAllDispo();
    }

    public SaleDetail findByIdSaleDetail(int id){
        return saleDetailRepository.findAllById(id);
    }

    public Sale findByIdSale(int id){
        return saleRepository.findAllById(id);
    }

    public List<Wallet> findAllWallets(Account account) {
        return walletRepository.findAllByAccount(account);
    }

    private boolean areWalletsCapable(List<Wallet> wallets, List<SaleDetail> saleDetails) throws SaleException {
        for (SaleDetail saleDetail : saleDetails) {
            validateWalletCryptoQuantity(wallets, saleDetail);
        }
        return true;
    }

    /**
     * Verifier si nous pouvons validee le sale_detail, en verifiant les restes dans les portefeuilles.
     */
    private void validateWalletCryptoQuantity(List<Wallet> wallets, SaleDetail saleDetail) throws SaleException {
        for (Wallet wallet : wallets) {
            if (wallet.getCrypto().equals(saleDetail.getCrypto()) && wallet.getQuantity() < saleDetail.getQuantity()) {
                throw new SaleException(saleDetail, wallet);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(SaleFormData saleFormData) throws SaleException {
        Sale sale = saleFormData.getSale();
        List<SaleDetail> saleDetails = saleFormData.getSaleDetails();

        // Verifier que les Crypto dans mon wallet suffisent pour faire la vente
        List<Wallet> wallets = this.findAllWallets(sale.getAccount());
        areWalletsCapable(wallets, saleDetails);

        saleRepository.save(sale);

        for (SaleDetail saleDetail : saleDetails) {
            saleDetail.setSale(sale);
            saleDetail.setQuantityLeft(saleDetail.getQuantity());

            saleDetailRepository.save(saleDetail);
        }
    }

    @Transactional
    public void buyCrypto(Account buyer, SaleDetail saleDetail, int quantity) {
        if (quantity > saleDetail.getQuantityLeft()) {
            throw new IllegalArgumentException("Quantité demandée supérieure à la quantité disponible.");
        }

        // Update the quantity left in the sale detail
        saleDetail.setQuantityLeft(saleDetail.getQuantityLeft() - quantity);
        saleDetailRepository.save(saleDetail);

        // Add the purchased quantity to the buyer's wallet
        walletRepository.addCryptoToWallet(buyer.getId(), saleDetail.getCrypto().getId(), quantity);
    }
}
