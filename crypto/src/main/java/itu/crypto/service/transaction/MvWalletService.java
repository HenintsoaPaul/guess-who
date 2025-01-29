package itu.crypto.service.transaction;

import itu.crypto.entity.Account;
import itu.crypto.entity.Crypto;
import itu.crypto.entity.MvWallet;
import itu.crypto.repository.CryptoRepository;
import itu.crypto.repository.transaction.MvWalletRepository;
import itu.crypto.service.AccountService;
import itu.crypto.service.CryptoService;
import itu.crypto.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
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

    public MvWallet findById(Integer id) throws Exception {
        return mvWalletRepository.findById(id).orElseThrow(() -> new Exception("MvWallet not found"));
    }

    public List<MvWallet> findAllAfterDateMin(LocalDate dateMin) {
        return mvWalletRepository.findAllAfterDateMin(dateMin);
    }

    public List<MvWallet> findAllBeforeDateMax(LocalDate dateMax) {
        return mvWalletRepository.findAllBeforeDateMax(dateMax);
    }

    public List<MvWallet> findByIdCrypto(Integer idCrypto) {
        return mvWalletRepository.findAllByIdCrypto(idCrypto);
    }

    public List<MvWallet> findByIdAccount(Integer idAccount) {
        return mvWalletRepository.findAllByIdAccount(idAccount);
    }
}
