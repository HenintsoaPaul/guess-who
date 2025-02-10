package itu.crypto.firebase.firestore.account;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import itu.crypto.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountChangeListener extends FirestoreChangeListener<Account, AccountDocument> {

    private final AccountService accountService;

    public AccountChangeListener(Firestore firestore, AccountService accountService) {
        super(firestore, accountService, "account");
        this.accountService = accountService;
    }

    @Override
    protected Account toEntity(AccountDocument document) {
        return document.toEntity();
    }

    @Override
    protected Class<AccountDocument> getDocumentClass() {
        return AccountDocument.class;
    }

    @Override
    protected void updateDatabase(Account entity) {
        accountService.updateOrCreate(entity); // 🔥 Mise à jour ou insertion
    }

    @Override
    protected void deleteFromDatabase(String entityId) {
        try {
            accountService.deleteById(Integer.parseInt(entityId)); // 🔥 Suppression de la ligne
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
    }
}
