package itu.crypto.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiResponse fetchUrl(String url, Object payload) {
        try {
            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(payload),
                    ApiResponse.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error on api '{}' fetch via restTemplate. Desc: {}", url, e.getMessage());
            throw e;
        }
    }
}
