package itu.crypto.service;

import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import itu.crypto.dto.login.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final FetchService fetchService;
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse sendLoginDto(LoginDTO loginDTO) {
	//	return fetchService.fetchUrl("/api/login", loginDTO);

	String laravelUrl = cryptoConfigProperties.getLaravelUrl();
	String apiUrl = laravelUrl + "/api/login";

	// Faire un appel vers un api
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
	    @Override
	    public void handleError(ClientHttpResponse response) {
		// Suppresses default error handling
	    }
	});

	try {
	    String responseBody = restTemplate.postForObject(apiUrl, loginDTO, String.class);
	    System.out.println(responseBody);
	    return null;
	} catch (Exception e) {
	    e.printStackTrace();
	    return new ApiResponse("error", "Failed to send login data", null, e.getMessage());
	}
    }

    public ApiResponse sendPin(LoginDTO loginDTO) {
	return fetchService.fetchUrl("/api/login/validate", loginDTO);
    }
}
