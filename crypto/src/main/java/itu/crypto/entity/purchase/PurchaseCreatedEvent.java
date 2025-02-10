package itu.crypto.entity.purchase;


import lombok.Getter;

@Getter
public class PurchaseCreatedEvent {
    private final Purchase purchase;

    public PurchaseCreatedEvent(Purchase purchase) {
        this.purchase = purchase;
    }

}
