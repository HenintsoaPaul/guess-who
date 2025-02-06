package itu.crypto.service.account;

import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements BaseService<Account> {

    private final AccountRepository accountRepository;

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
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
