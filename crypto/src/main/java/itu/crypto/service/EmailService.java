package itu.crypto.service;

import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.entity.fund.PendingState;
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

    public String sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        message.setFrom(fromEmail);

        mailSender.send(message);

        String msg = "Email envoye a " + to;
        log.info(msg);
        return msg;
    }

    /**
     * Ecrire et envoyer un email pour informer les utilisateurs que leurs
     * demande de depot/retrait sont en attente de validation des admins.
     */
    public String writeEmailAttente(PendingMvFund pendingMvFund) {
        String typeAction = pendingMvFund.getTypeMvFund().getName();

        String to = pendingMvFund.getAccount().getEmail(),
                subject = "Demande de " + typeAction,
                text = "Nous vous informons que votre demande de " + typeAction
                        + " est en attente d'une validation venant de nos admins. \nVeuillez patientez pour la confirmation/refus de votre "
                        + typeAction + ".\n Nous vous remercions de votre patience. Notre equipe vous souhaite une bonne journee 😊.";

        return sendEmail(to, subject, text);
    }

    /**
     * Ecrire et envoyer un email pour informer les utilisateurs que leurs
     * demande de depot/retrait ont ete repondu (validation/refus) par les admins.
     */
    public String writeEmailReponse(PendingMvFund pendingMvFund) {

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

        return sendEmail(to, subject, text);
    }

    /**
     * Ecrire et envoyer un email pour informer les utilisateurs apres une operation sur les demandes depot/retrait
     * en envoi ou en reponse(validation/refus).
     */
    public String writeEmailPendingMvFund(PendingMvFund pmf) {
        PendingState etat = pmf.getPendingState();
        if (pmf.getDateValidation() == null && etat.getId() == 1) {
            return this.writeEmailAttente(pmf);
        } else {
            return this.writeEmailReponse(pmf);
        }
    }
}
