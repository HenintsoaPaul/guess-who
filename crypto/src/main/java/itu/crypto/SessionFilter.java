package itu.crypto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SessionFilter extends OncePerRequestFilter {
    private final CryptoConfigProperties cryptoConfigProperties;

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
	System.out.println("current token: " + token);
//	System.out.println("current token: " + token);

	if (token == null || token.isEmpty()) {
	    // Le token n'est pas présent, nous pouvons soit :
	    // - Retourner une réponse d'erreur
	    // - Rediriger vers une page d'authentification
	    // - Continuer le traitement normal

	    // Verifier la duree de vie de la session(token)
	    // ...

	    // Exemple : retourner une réponse d'erreur
	    sendErrorResponse(response, "Token absent dans la session");

	    // Ne pas passer au filtre de chaîne
	    return;
	}
	
	// Ajouter le token dans le header de la requete
	request.setAttribute("Authorization", "Bearer " + token);

	// Le token est présent, continuer le traitement
	filterChain.doFilter(request, response);
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
