package itu.crypto.service.account;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.register.RegisterRequest;
import itu.crypto.entity.account.Account;
import itu.crypto.service.FetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final FetchService fetchService;
    private final AccountService accountService;

    public ApiResponse sendData(RegisterRequest authDTO) {
        return fetchService.fetchUrl("/api/register", authDTO, false);
    }

    public ApiResponse sendPin(RegisterRequest authDTO) {
        return fetchService.fetchUrl("/api/register/validate", authDTO, false);
    }

    @Transactional
    public Account register(RegisterRequest authDTO) {
        Account account = new Account();
        account.setEmail(authDTO.getEmail());
        account.setPseudo(authDTO.getPseudo());
        account.setFund(0);

        return accountService.save(account);
    }
}
