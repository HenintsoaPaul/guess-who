package itu.crypto.security;

import itu.crypto.CryptoConfigProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

@Slf4j
@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final CryptoConfigProperties cryptoConfigProperties;
    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();

        // Check if the path is permitted for All
        if (securityService.isExcludedPath(cryptoConfigProperties.getExcludedPaths(), requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Proceed with token validation
        String authorizationHeader = (String) request.getAttribute("Authorization");
//        log.debug("Authorization header: {}", authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.debug("TokenFilter >> token: {}", token);
            try {
                validateTokenWithLaravel(token);
            } catch (Exception e) {
                String errorMsg = e.getMessage();
                redirectToLogin(request, response, errorMsg);
                return;
            }
        } else {
            String errorMsg = "Authorization header missing or invalid";
            redirectToLogin(request, response, errorMsg);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void validateTokenWithLaravel(String token) {
        String baseUrl = cryptoConfigProperties.getLaravelUrl(),
                endpointUrl = cryptoConfigProperties.getTokenValidationEndpoint(),
                validationEndpoint = baseUrl + endpointUrl;

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Laravel URL is not configured");
        }
        if (endpointUrl == null || endpointUrl.isBlank()) {
            throw new IllegalStateException("Auth endpoint URL is not configured");
        }

        HttpHeaders authHeaders = createAuthHeaders(token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(authHeaders);

        try {
            ResponseEntity<String> response = restTemplate.exchange(validationEndpoint, HttpMethod.GET, httpEntity, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || !isTokenValid(response.getBody())) {
                throw new IllegalArgumentException("Invalid token: Unsuccessful response or unexpected content.");
            }
        } catch (RestClientException e) {
            log.debug(e.getMessage());
            throw new IllegalArgumentException("Token Invalid.");
        }
    }

    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private boolean isTokenValid(String responseBody) {
        return responseBody != null && responseBody.contains("valid");
    }

    /**
     * Ajouter l'erreur  vers la page de login.
     */
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        log.error("Token validation error! Redirection to login. Detail : {}", errorMessage);

        securityService.redirectToLogin(request, response, errorMessage);
    }
}


