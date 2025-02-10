package itu.crypto.security;

import itu.crypto.entity.account.Admin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AdminFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();

        if (requestPath.startsWith("/back-office")) {
            boolean isAdmin  = verifyIsAdmin(request.getSession());
            if (!isAdmin) {
                request.getSession().setAttribute("adminError", "Pas reservee aux admins.");
                response.sendRedirect(request.getContextPath() + "/front-office");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean verifyIsAdmin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        return admin != null;
    }
}
