package itu.crypto.service;

import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final FetchService fetchService;
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse sendLoginDto(LoginRequest loginRequest) {
	return fetchService.fetchUrl("/api/login", loginRequest);
    }

    public ApiResponse sendPin(LoginRequest loginRequest) {
	return fetchService.fetchUrl("/api/login/validate", loginRequest);
    }
}
