package itu.crypto.service.transaction.fund;

import com.google.firebase.messaging.FirebaseMessagingException;

import itu.crypto.entity.fund.MvFund;
import itu.crypto.entity.fund.PendingMvFund;
import itu.crypto.firebase.firestore.generalisation.BaseService;
import itu.crypto.firebase.notification.NotificationService;
import itu.crypto.firebase.notification.websocke.WebSocketHandler;
import itu.crypto.repository.transaction.fund.MvFundRepository;
import itu.crypto.service.account.AccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<MvFund> findByPendingMvFund(PendingMvFund pendingMvFund) {
        return Optional.ofNullable(mvFundRepository.findByPendingMvFund(pendingMvFund));
    }

    /**
     * Verifie si un {@code mv_fund} est deja lie au pending_mv_fund. Si aucun mv_fund n'est
     * trouve, nous ajoutons un nouveau mv_fund pour cette pending_mv_fund. Sinon, nous ne
     * faisons rien et nous retournons null.
     */
    @Transactional
    public MvFund addFromPending(PendingMvFund pmf) throws FirebaseMessagingException {
        if (this.findByPendingMvFund(pmf).isEmpty()) {
            MvFund mv = new MvFund(pmf);
            return this.save(mv);
        }
        return null;
    }
}
