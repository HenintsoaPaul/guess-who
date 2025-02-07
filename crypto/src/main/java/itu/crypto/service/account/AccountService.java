package itu.crypto.service.account;

import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements BaseService<Account> {

    private final AccountRepository accountRepository;

    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void updateOrCreate(Account account) {
        this.save(account);
    }

    public void deleteById(int id) {
        accountRepository.deleteById(id);
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
