package itu.crypto.service;

import itu.crypto.entity.fund.PendingMvFund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        message.setFrom(fromEmail);

        mailSender.send(message);
        log.info("Email envoye a " + to);
    }

    /**
     * Ecrire et envoyer un email pour informer les utilisateurs que leurs
     * demande de depot/retrait sont en attente de validation des admins.
     */
    public void writeEmailAttente(PendingMvFund pendingMvFund) {
        String typeAction = pendingMvFund.getTypeMvFund().getName();

        String to = pendingMvFund.getAccount().getEmail(),
                subject = "Demande de " + typeAction,
                text = "Nous vous informons que votre demande de " + typeAction
                        + " est en attente d'une validation venant de nos admins. Veuillez patientez pour la confirmation/refus de votre "
                        + typeAction + ".\n Nous vous remercions de votre patience. Notre equipe vous souhaite une bonne journee 😊.";

        sendEmail(to, subject, text);
    }

    /**
     * Ecrire et envoyer un email pour informer les utilisateurs que leurs
     * demande de depot/retrait ont ete repondu (validation/refus) par les admins.
     */
    public void writeEmailReponse(PendingMvFund pendingMvFund) {

        String typeAction = pendingMvFund.getTypeMvFund().getName(),
                to = pendingMvFund.getAccount().getEmail(),
                subject = "Reponse a la demande de " + typeAction;

        String text = "Nous vous informons que votre demande de " + typeAction + " a ete " +
                switch (pendingMvFund.getPendingState().getId()) {
                    case 2 -> " accepter par les admins. Merci pour votre attente.";
                    case 3 -> " refuser par les admins. Merci pour votre comprehension.";
                    default -> "";
                };
        text += "\nNotre equipe vous souhaite une bonne journee 😊.";

        sendEmail(to, subject, text);
    }
}
