package itu.crypto.service;

import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final FetchService fetchService;

    public ApiResponse sendLoginDto(LoginRequest loginRequest) {
	return fetchService.fetchUrl("/api/login", loginRequest, false);
    }

    public ApiResponse sendPin(LoginRequest loginRequest) {
	return fetchService.fetchUrl("/api/login/validate", loginRequest, false);
    }
}
