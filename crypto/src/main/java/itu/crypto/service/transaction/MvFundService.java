package itu.crypto.service.transaction;

import com.google.firebase.messaging.FirebaseMessagingException;

import itu.crypto.entity.fund.MvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.firebase.notification.NotificationService;
import itu.crypto.firebase.notification.websocke.WebSocketHandler;
import itu.crypto.repository.transaction.MvFundRepository;
import itu.crypto.service.account.AccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MvFundService implements BaseService<MvFund> {

    private final MvFundRepository mvFundRepository;
    private final AccountService accountService;
    private final NotificationService notificationService;
    private final WebSocketHandler webSocketHandler;

    @Transactional
    public MvFund save(MvFund mvFund) throws FirebaseMessagingException {
        MvFund saved = mvFundRepository.save(mvFund);

        // todo: send notification
        if (saved.isDepotRetrait()) {
            String title = "Nouveau Depot/Retrait",
                    body = "Montant de " + saved.getAmount() + " Ar.";

            List<String> fcmTokens = accountService.findAllFCMTokens(mvFund.getAccount());
            notificationService.sendNotification(title, body, fcmTokens);


            // Envoi via WebSockets
            webSocketHandler.sendNotification("🚀 Nouvelle Notification: " + title + " - " + body);
        }


        return saved;
    }

    public List<MvFund> findAll() {
        return this.mvFundRepository.findAll();
    }
}
