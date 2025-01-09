package itu.crypto.service;

import itu.crypto.entity.Account;
import itu.crypto.entity.CryptoCurrency;
import itu.crypto.entity.Wallet;
import itu.crypto.repository.WalletRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> getWallets(Account account) {
        return walletRepository.findByAccount(account);
    }

    public Wallet createWallet(Account account , CryptoCurrency crypto) {
        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallet.setQuantity(0.0);
        wallet.setCryptoCurrency(crypto);
        walletRepository.save(wallet);
        return wallet;
    }


    public Wallet getWallet(Account account, CryptoCurrency crypto) {
        Wallet wallet = walletRepository.findWallet(account, crypto);
        if (wallet == null) {
            wallet = createWallet(account, crypto);
        }
        return wallet;
    }

    public double getWalletQuantity(Account account , CryptoCurrency crypto) {
        Wallet wallet = walletRepository.findWallet(account,crypto);
        return wallet != null ? wallet.getQuantity() : 0.0;
    }

    public void addFunds(Account account,CryptoCurrency crypto, double amount) {
        Wallet wallet = walletRepository.findWallet(account,crypto);
        if (wallet != null) {
            wallet.setQuantity(wallet.getQuantity() + amount);
            walletRepository.save(wallet);
        }
    }

    public void withdrawFunds(Account account, CryptoCurrency crypto, double amount) {
        Wallet wallet = walletRepository.findWallet(account,crypto);
        if (wallet != null && wallet.getQuantity() >= amount) {
            wallet.setQuantity(wallet.getQuantity() - amount);
            walletRepository.save(wallet);
        }
    }
}

