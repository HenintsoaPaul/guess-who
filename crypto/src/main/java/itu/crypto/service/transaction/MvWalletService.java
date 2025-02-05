package itu.crypto.service.transaction;

import itu.crypto.entity.account.Account;
import itu.crypto.entity.Crypto;
import itu.crypto.entity.MvWallet;
import itu.crypto.repository.transaction.MvWalletRepository;
import itu.crypto.service.account.AccountService;
import itu.crypto.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MvWalletService {
    private final MvWalletRepository mvWalletRepository;
    private final CryptoService cryptoService;
    private final AccountService accountService;

    public List<MvWallet> findAll() {
        return mvWalletRepository.findAll();
    }

    public List<Crypto> findAllCrypto() {
        return cryptoService.findAll();
    }

    public List<Account> findAllAccount() {
        return accountService.findAll();
    }

    public Account findAccountById(Integer id) {
        return accountService.findById(id);
    }

    public MvWallet findById(Integer id) throws Exception {
        return mvWalletRepository.findById(id).orElseThrow(() -> new Exception("MvWallet not found"));
    }

    public List<MvWallet> findAllAfterDateMin(LocalDate dateMin) {
        return mvWalletRepository.findAllAfterDateMin(dateMin);
    }

    public List<MvWallet> findAllBeforeDateMax(LocalDate dateMax) {
        return mvWalletRepository.findAllBeforeDateMax(dateMax);
    }

    public List<MvWallet> findAllByIdCrypto(Integer idCrypto) {
        return mvWalletRepository.findAllByIdCrypto(idCrypto);
    }

    public List<MvWallet> findAllByIdAccount(Integer idAccount) {
        return mvWalletRepository.findAllByIdAccount(idAccount);
    }
}
