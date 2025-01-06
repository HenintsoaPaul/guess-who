package itu.crypto.dto.register;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {
    private String pseudo;
    private String email;
    private String password;
    private String pin;

    public RegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterDTO(String email) {
        this.email = email;
    }
}
