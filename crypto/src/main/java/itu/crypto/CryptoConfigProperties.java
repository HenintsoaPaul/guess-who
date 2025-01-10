package itu.crypto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CryptoConfigProperties {
    @Value("${app.laravel-url}")
    private String laravelUrl;

    @Value("${app.excluded-paths}")
    private final List<String> excludedPaths;
}
