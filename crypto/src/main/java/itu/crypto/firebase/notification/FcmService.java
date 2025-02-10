package itu.crypto.firebase.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import itu.crypto.entity.account.Account;
import itu.crypto.entity.purchase.Purchase;
import itu.crypto.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final AccountService accountService;
    private final FirebaseMessaging firebaseMessaging;

    private Message writeMessage(FcmMessage fcmMessage, String recipientToken) {
        Notification notification = Notification.builder()
                .setTitle(fcmMessage.getTitle())
                .setBody(fcmMessage.getBody())
                .build();

        return Message.builder()
                .setToken(recipientToken)
                .setNotification(notification)
                .build();
    }

    private void sendMessage(Message message) {
        try {
            firebaseMessaging.send(message);
            log.info("Message sent");
        } catch (FirebaseMessagingException e) {
            log.error("Message error: {}", e.getMessage());
        }
    }

    public void sendToOne(Account account, Purchase purchase) {
        FcmMessage msg = new FcmMessage(purchase);
        sendMessage(writeMessage(msg, account.getFcmToken()));
    }

    public void sendToMany(List<Account> accounts, Purchase purchase) {
        for (Account account : accounts) {
            sendToOne(account, purchase);
        }
    }

    /**
     * Envoyer une notification a toutes les personnes qui sont interresse
     * par le crypto de la vente/achat.
     */
    public void send(Purchase purchase) {
        sendToMany(
                accountService.findAllToNotifyOnPurchase(purchase),
                purchase
        );
    }
}
