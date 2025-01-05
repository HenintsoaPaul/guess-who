package itu.crypto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final CryptoConfigProperties cryptoConfigProperties;
    private static final String VALIDATION_PATH = "/api/validate-token";

    // Check if a path matches a pattern (e.g., wildcard `/*`)
    private boolean pathMatchesPattern(String path, String pattern) {
	if (pattern.endsWith("/*")) { // Wildcard match
	    return path.startsWith(pattern.substring(0, pattern.length() - 2));
	}
	return path.equals(pattern); // Exact match
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	// Check if the path is permitted for All
	String requestPath = request.getServletPath();
	List<String> excludedPaths = cryptoConfigProperties.getExcludedPaths();
	boolean isExcluded = excludedPaths.stream().anyMatch(pattern -> pathMatchesPattern(requestPath, pattern));
	if (isExcluded) {
	    filterChain.doFilter(request, response);
	    return;
	}

	// Proceed with token validation
	String authorizationHeader = request.getHeader("Authorization");

	if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	    String token = authorizationHeader.substring(7);
	    try {
		validateTokenWithLaravel(token);
	    } catch (Exception e) {
		sendErrorResponse(response, e.getMessage());
		return;
	    }
	} else {
	    sendErrorResponse(response, "Authorization header missing or invalid");
	    return;
	}

	filterChain.doFilter(request, response);
    }

    private void validateTokenWithLaravel(String token) {
	String baseUrl = cryptoConfigProperties.getLaravelUrl();
	if (baseUrl == null || baseUrl.isBlank()) {
	    throw new IllegalStateException("Laravel URL is not configured");
	}

	HttpHeaders authHeaders = createAuthHeaders(token);
	HttpEntity<Void> httpEntity = new HttpEntity<>(authHeaders);

	String validationEndpoint = baseUrl + VALIDATION_PATH;
	try {
	    ResponseEntity<String> response = restTemplate.exchange(validationEndpoint, HttpMethod.GET, httpEntity, String.class);

	    if (!response.getStatusCode().is2xxSuccessful() || !isTokenValid(response.getBody())) {
		throw new IllegalArgumentException("Invalid token: Unsuccessful response or unexpected content.");
	    }
	} catch (RestClientException e) {
	    System.out.println(e.getMessage());
	    throw new IllegalArgumentException("Invalid token.");
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

    // Utility to send custom error responses
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	String errorJson = String.format("{\"timestamp\": \"%s\", \"status\": %d, \"message\": \"%s\"}",
		java.time.Instant.now().toString(), HttpServletResponse.SC_UNAUTHORIZED, message);
	response.getWriter().write(errorJson);
	response.getWriter().flush();
	response.getWriter().close();
    }
}


