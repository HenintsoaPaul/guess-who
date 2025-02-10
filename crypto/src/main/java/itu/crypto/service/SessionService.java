package itu.crypto.service;

import itu.crypto.entity.account.Admin;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SessionService {
    public void viderSession(HttpSession session) {
        log.info("Terminate session...");
        session.removeAttribute("admin");
        session.removeAttribute("id_account");
        session.removeAttribute("token");
        session.removeAttribute("token_expiration");
    }

    public void initSession(HttpSession session, int idAccount, String token, String tokenExpiration, Admin admin) {
        log.info("Initialize session...");
        session.setAttribute("admin", admin);
        session.setAttribute("id_account", idAccount);
        session.setAttribute("token", token);
        session.setAttribute("token_expiration", tokenExpiration);
    }
}
