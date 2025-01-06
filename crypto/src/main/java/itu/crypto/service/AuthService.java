package itu.crypto.service;

import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.AuthDTO;
import itu.crypto.dto.register.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final FetchService fetchService;
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse sendData(AuthDTO authDTO) {
	return fetchService.fetchUrl("/api/register", authDTO);
    }

    public ApiResponse sendPin(AuthDTO authDTO) {
	return fetchService.fetchUrl("/api/register/validate", authDTO);
    }
}
