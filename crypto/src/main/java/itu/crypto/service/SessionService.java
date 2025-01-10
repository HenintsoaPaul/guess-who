package itu.crypto.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public void viderSession(HttpSession session) {
	System.out.println("Terminate session...");
	session.removeAttribute("id_account");
	session.removeAttribute("token");
	session.removeAttribute("token_expiration");
    }

    public void initSession(HttpSession session, int idAccount, String token, String tokenExpiration) {
	System.out.println("Initialize session...");
	session.setAttribute("id_account", idAccount);
	session.setAttribute("token", token);
	session.setAttribute("token_expiration", tokenExpiration);
    }
}
