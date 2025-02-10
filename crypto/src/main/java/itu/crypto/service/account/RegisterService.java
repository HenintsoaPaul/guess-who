package itu.crypto.service.account;

import itu.crypto.api.ApiResponse;
import itu.crypto.dto.register.RegisterRequest;
import itu.crypto.dto.register.RegisterResponse;
import itu.crypto.entity.account.Account;
import itu.crypto.api.FetchService;
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
    public void register(RegisterRequest request, RegisterResponse response) {

        Account account = new Account();
        account.setFund(0);

        account.setEmail(request.getEmail());
        account.setPseudo(request.getPseudo());

        account.setPassword(response.getHashed_password());

        accountService.save(account);
    }
}
