package itu.crypto.dto.register;

import itu.crypto.api.ApiResponse;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class RegisterResponse {

    private String token;
    private String expiration;
    private String hashed_password;

    public RegisterResponse(ApiResponse apiResponse) {
        LinkedHashMap<?, ?> dataMap = (LinkedHashMap<?, ?>) apiResponse.getData();

        // Map fields from LinkedHashMap to LoginResponse object
        this.setToken((String) dataMap.get("token"));
        this.setExpiration((String) dataMap.get("expiration"));
        this.setHashed_password((String) dataMap.get("hashed_password"));
    }
}
