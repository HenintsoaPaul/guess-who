package itu.crypto.firebase.firestore.account;

import com.google.cloud.firestore.Firestore;
import itu.crypto.entity.account.Account;
import itu.crypto.firebase.firestore.generalisation.GenericSyncService;
import itu.crypto.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountSyncService extends GenericSyncService<Account, AccountDocument> {

    public AccountSyncService(Firestore firestore, AccountService accountService) {
        super(firestore, accountService, "account");
    }

    @Override
    protected AccountDocument toDocument(Account entity) {
        return new AccountDocument(entity);
    }

    @Override
    protected Account toEntity(AccountDocument document) {
        return document.toEntity();
    }

    @Override
    protected String getEntityId(Account entity) {
        return entity.getId().toString();
    }

    @Override
    protected Class<AccountDocument> getDocumentClass() {
        return AccountDocument.class;
    }
}
