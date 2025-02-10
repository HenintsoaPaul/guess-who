package itu.crypto;

import itu.crypto.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
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
        boolean isExcluded = excludedPaths.stream()
                .anyMatch(pattern -> pathMatchesPattern(requestPath, pattern));
        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }

        // Vérifier si un token est présent dans la session
        String token = (String) request.getSession().getAttribute("token");

        if (token == null || token.isEmpty()) {
            String errorMsg = "Token absent dans la session. Veuillez vous (re-)connecter";
            redirectToLogin(request, response, errorMsg);
            return; // Ne pas passer au filtre suivant
        }

        boolean isExpired = verifyTokenLifeTime(request, response);
        if (isExpired) {
            String errorMsg = "Token expiree. Veuillez vous (re-)connecter";
            redirectToLogin(request, response, errorMsg);
            return; // Ne pas passer au filtre suivant
        }

        // Ajouter le token dans le header de la requete
        request.setAttribute("Authorization", "Bearer " + token);
        filterChain.doFilter(request, response);
    }

    private boolean verifyTokenLifeTime(HttpServletRequest request, HttpServletResponse response) {
        String tokenExpiration = (String) request.getSession().getAttribute("token_expiration");

        Instant instant = Instant.parse(tokenExpiration);
        LocalDateTime d = LocalDateTime.ofInstant(instant, ZoneId.of(cryptoConfigProperties.getTimeZone()));

        boolean isExpired = d.isBefore(LocalDateTime.now());
        if (isExpired) {
            sessionService.viderSession(request.getSession());
            log.warn("Token is expired. Expiration date: {} | current date: {}", d, LocalDateTime.now());
        }
        return isExpired;
    }

    /**
     * Ajoute un flash attribute contenant le message d'erreur et redirige l'utilisateur vers la page de login.
     */
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        request.getSession().setAttribute("loginError", errorMessage);

        log.error("Session error! Redirection to login. Detail : {}", errorMessage);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
