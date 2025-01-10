package itu.crypto;

import itu.crypto.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
public class SessionFilter extends OncePerRequestFilter {
    private final CryptoConfigProperties cryptoConfigProperties;
    private final SessionService sessionService;

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

	// Vérifier si un token est présent dans la session
	String token = (String) request.getSession().getAttribute("token");

	if (token == null || token.isEmpty()) {
	    // Le token n'est pas présent, nous pouvons soit :
	    // - Retourner une réponse d'erreur
	    // - Rediriger vers une page d'authentification
	    // - La verif du durree de vie se fait dans Laravel

	    // Exemple : retourner une réponse d'erreur
	    sendErrorResponse(response, "Token absent dans la session");

	    // Ne pas passer au filtre de chaîne
	    return;
	}

	// Ajouter le token dans le header de la requete
	System.out.println("----");
	System.out.println("current token: " + token);

	boolean isExpired = verifierDureeDeVie(request, response);
	if (isExpired) {
	    sendErrorResponse(response, "Token expired");
	    return;
	}

	request.setAttribute("Authorization", "Bearer " + token);
	filterChain.doFilter(request, response);
    }

    private boolean verifierDureeDeVie(HttpServletRequest request, HttpServletResponse response) throws IOException {
	String tokenExpiration = (String) request.getSession().getAttribute("token_expiration");

	// Parse the string to Instant
	Instant instant = Instant.parse(tokenExpiration);
	// Convert Instant to LocalDateTime in a specific time zone
	LocalDateTime d = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

	boolean isExpired = d.isBefore(LocalDateTime.now());
	if (isExpired) {
	    // Supprimer la session
	    sessionService.viderSession(request.getSession());

	    System.out.println("Token is expired. Expiration date: " + d + " | current date: " + LocalDateTime.now());
	}
	return isExpired;
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
