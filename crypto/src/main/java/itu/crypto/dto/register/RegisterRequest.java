package itu.crypto.dto.register;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String pseudo;
    private String email;
    private String password;
    private String pin;
}
