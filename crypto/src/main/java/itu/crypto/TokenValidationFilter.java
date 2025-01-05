package itu.crypto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final CryptoConfigProperties cryptoConfigProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	String authorizationHeader = request.getHeader("Authorization");

	if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	    String token = authorizationHeader.substring(7);
	    try {
		validateTokenWithLaravel(token);
	    } catch (Exception e) {
		System.out.println(e.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		return;
	    }
	} else {
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
	    return;
	}

	filterChain.doFilter(request, response);
    }

    private void validateTokenWithLaravel(String token) throws Exception {
	String laravelUrl = cryptoConfigProperties.getLaravelUrl();
	if (laravelUrl == null || laravelUrl.isBlank()) {
	    System.out.println("tss laravel url");
	    throw new Exception("Laravel URL is not configured");
	}

	HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Bearer " + token);
	var entity = new HttpEntity<>(headers);

	String apiEndPoint = laravelUrl + "/api/validate-token";
	ResponseEntity<String> response = restTemplate.exchange(apiEndPoint, HttpMethod.GET, entity, String.class);

	if (response.getStatusCode().is2xxSuccessful() || response.getBody().contains("valid")) {
	    // okay...
	} else  {
	    throw new Exception("Invalid token");
	}
    }
}


