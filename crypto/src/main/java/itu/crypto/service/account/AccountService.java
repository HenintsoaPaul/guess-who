package itu.crypto.service.account;

import itu.crypto.entity.account.Account;
import itu.crypto.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Retrouver un Account a partir d'un token dans le session. Mbola tsy mande io.
     */
    @Deprecated
    public Account findFromToken(String token) {
        return null;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * Retourner les tokens firebase pour l'envoi des notifications
     * vers un utilisateur
     */
    @Deprecated
    public List<String> findAllFCMTokens(Account account) {
        return null;
    }
}
