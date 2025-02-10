package itu.crypto.firebase.notification;

import itu.crypto.entity.purchase.Purchase;
import lombok.Data;


@Data
public class FcmMessage {
    private String title;
    private String body;

    public FcmMessage(Purchase purchase) {
        this.title = "Notification sur purchase";
        this.body = "Vente/Achat entre les utilisateurs .... fkdafdjfkfjdla;";
    }
}
