package itu.crypto.firebase.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifs")
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationMessage notificationMessage) throws FirebaseMessagingException {
        notificationService.sendNotificationByToken(notificationMessage);
    }
}
