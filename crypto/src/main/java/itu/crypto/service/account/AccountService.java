package itu.crypto.service.account;

import itu.crypto.dto.ApiResponse;
import itu.crypto.entity.Account;
import itu.crypto.repository.AccountRepository;
import itu.crypto.service.FetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final FetchService fetchService;

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


    @Deprecated
    public ApiResponse fetchPasswordFromIdentityProvider(Account account) {
//        return fetchService.fetchUrl("/api/account/password", account, false);

        return new ApiResponse("ok", "mila mitifitra anle identite", "fufu_is_password", null);
    }
}
