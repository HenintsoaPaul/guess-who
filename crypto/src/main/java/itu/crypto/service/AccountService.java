package itu.crypto.service;

import itu.crypto.entity.Account;
import itu.crypto.entity.Cours;
import itu.crypto.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Retrouver un Account a partir d'un token dans le session.
     * Mbola tsy mande io.
     *
     * @param token
     * @return
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
}
