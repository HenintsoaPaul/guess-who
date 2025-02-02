package itu.crypto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import itu.crypto.CryptoConfigProperties;
import itu.crypto.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FetchService {

    private final CryptoConfigProperties cryptoConfigProperties;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ERROR_STATUS = "error";

    public ApiResponse fetchUrl(String url, Object payload, boolean log) {
        if (log) {
            System.out.println("Payload: " + payload);
        }

        String apiUrl = buildApiUrl(url);
        RestTemplate restTemplate = configureRestTemplate();

        try {
            String responseBody = restTemplate.postForObject(apiUrl, payload, String.class);
            if (log) {
                System.out.println("Response body: " + responseBody);
            }
            return deserializeResponse(responseBody);
        } catch (Exception ex) {
            ex.printStackTrace();
            return handleApiError(ex);
        }
    }

    private RestTemplate configureRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) {
                // Suppresses default error handling
            }
        });
        return restTemplate;
    }

    private ApiResponse handleApiError(Exception ex) {
        if (ex instanceof HttpClientErrorException) {
            String responseBody = ((HttpClientErrorException) ex).getResponseBodyAsString();
            System.out.println("Error Response Body: " + responseBody);
            try {
                return deserializeResponse(responseBody);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return new ApiResponse(ERROR_STATUS, ex.getMessage(), null, null);
    }

    private ApiResponse deserializeResponse(String responseBody) throws IOException {
        return OBJECT_MAPPER.readValue(responseBody, ApiResponse.class);
    }

    private String buildApiUrl(String endpoint) {
        return cryptoConfigProperties.getLaravelUrl() + endpoint;
    }
}