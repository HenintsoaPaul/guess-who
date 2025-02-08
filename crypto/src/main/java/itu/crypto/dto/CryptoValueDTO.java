package itu.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CryptoValueDTO {
    private String name;
    private LocalDateTime dateCours;
    private double value;
}
