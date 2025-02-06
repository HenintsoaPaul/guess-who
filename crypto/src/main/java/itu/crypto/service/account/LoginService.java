package itu.crypto.service.account;

import itu.crypto.api.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import itu.crypto.entity.account.Account;
import itu.crypto.api.FetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final FetchService fetchService;
    private final AccountService accountService;

    public ApiResponse sendLoginDto(LoginRequest loginRequest) {
        return fetchService.fetchUrl("/api/login", loginRequest, true);
    }

    public ApiResponse sendPin(LoginRequest loginRequest) {
        return fetchService.fetchUrl("/api/login/validate", loginRequest, true);
    }

    public Account getAccount(LoginRequest loginRequest) {
        return this.accountService.findByEmail(loginRequest.getEmail());
    }
}
