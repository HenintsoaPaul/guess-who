package itu.crypto.service;

import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse sendLoginDto(LoginDTO loginDTO) {
	System.out.println(loginDTO);
	return null;
    }
}
