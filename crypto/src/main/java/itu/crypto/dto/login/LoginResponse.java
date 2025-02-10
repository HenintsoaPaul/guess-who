package itu.crypto.dto.login;

import itu.crypto.api.ApiResponse;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class LoginResponse {
    private String token;
    private String expiration;

    public LoginResponse(ApiResponse apiResponse) {
	LinkedHashMap<?, ?> dataMap = (LinkedHashMap<?, ?>) apiResponse.getData();

	// Map fields from LinkedHashMap to LoginResponse object
	this.setToken((String) dataMap.get("token"));
	this.setExpiration((String) dataMap.get("expiration"));
    }
}
