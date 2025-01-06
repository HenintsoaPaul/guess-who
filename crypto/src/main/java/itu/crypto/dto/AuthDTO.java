package itu.crypto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDTO {
    private String pseudo;
    private String email;
    private String password;
    private String pin;

    public AuthDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthDTO(String email) {
        this.email = email;
    }
}
