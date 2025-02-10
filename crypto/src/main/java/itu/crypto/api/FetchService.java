package itu.crypto.api;

import itu.crypto.CryptoConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchService {

    private final ApiService apiService;
    private final CryptoConfigProperties cryptoConfigProperties;

    public ApiResponse fetchUrl(String url, Object payload, boolean toLog) {
        if (toLog) log.debug("Payload: {}", payload);

        String apiUrl = buildApiUrl(url);

        return apiService.fetchUrl(apiUrl, payload);
    }

    private String buildApiUrl(String endpoint) {
        return cryptoConfigProperties.getLaravelUrl() + endpoint;
    }
}