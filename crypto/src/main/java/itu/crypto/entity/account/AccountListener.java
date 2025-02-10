package itu.crypto.entity.account;

import itu.crypto.firebase.firestore.account.AccountSyncService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AccountListener {

    private final AccountSyncService accountSyncService;

    @Autowired
    public AccountListener(@Lazy AccountSyncService accountSyncService) {
        this.accountSyncService = accountSyncService;
    }

    @PostPersist
    public void apresSauvegarde(Account account) {
        accountSyncService.saveAsDocument(account);
    }

    @PostUpdate
    public void apresModification(Account account) {
        accountSyncService.updateAsDocument(account);
    }
}
