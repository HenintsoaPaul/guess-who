package itu.crypto.service;

import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse sendLoginDto(LoginDTO loginDTO) {
	System.out.println(loginDTO);
	
	String laravelUrl = cryptoConfigProperties.getLaravelUrl();
	String apiUrl = laravelUrl + "/api/login";
	// Faire un appel vers un api
	RestTemplate restTemplate = new RestTemplate();
	try {
	    return restTemplate.postForObject(apiUrl, loginDTO, ApiResponse.class);
	} catch (Exception e) {
	    e.printStackTrace();
	    return new ApiResponse<>("error", "Failed to send login data", null, e.getMessage());
	}
    }

    public ApiResponse sendPin(LoginDTO loginDTO) {
	System.out.println(loginDTO);

	String laravelUrl = cryptoConfigProperties.getLaravelUrl();
	String apiUrl = laravelUrl + "/api/login/validate";
	// Faire un appel vers un api
	RestTemplate restTemplate = new RestTemplate();
	try {
	    return restTemplate.postForObject(apiUrl, loginDTO, ApiResponse.class);
	} catch (Exception e) {
	    e.printStackTrace();
	    return new ApiResponse<>("error", "Failed to send login validation data", null, e.getMessage());
	}
    }
}
