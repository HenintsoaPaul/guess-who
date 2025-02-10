package itu.crypto.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SecurityService {

    private boolean pathMatchesPattern(String path, String pattern) {
        if (pattern.endsWith("/*")) {
            return path.startsWith(pattern.substring(0, pattern.length() - 2));
        }
        return path.equals(pattern);
    }

    public boolean isExcludedPath(List<String> excludedPaths, String path) {
        boolean isExcluded = excludedPaths.stream()
                .anyMatch(
                        pattern -> pathMatchesPattern(path, pattern)
                );

        return isExcluded;
    }

    /**
     * Redirection vers la page de login, en stockant l'erreur dans la session.
     */
    public void redirectToLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            String errorMessage
    ) throws IOException {
        request.getSession().setAttribute("loginError", errorMessage);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
