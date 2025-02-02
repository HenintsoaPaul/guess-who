package itu.crypto.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import itu.crypto.dto.ApiResponse;
import itu.crypto.entity.Account;
import itu.crypto.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountSyncService {

    private final AccountService accountService;

    /**
     * Synchroniser les comptes existants avec Firebase Auth.
     */
    public String syncWithFirebase() {
        List<Account> accounts = accountService.findAll();

        int nbSynced = 0, nbTotal = accounts.size();
        log.info("Début de la synchronisation des comptes avec Firebase Auth ({} comptes à traiter)", nbTotal);

        for (Account account : accounts) {
            try {
                FirebaseAuth.getInstance().getUserByEmail(account.getEmail());
                log.debug("Utilisateur déjà synchronisé : {}", account.getEmail());
            } catch (Exception e) {
                try {
                    ApiResponse apiResponse = accountService.fetchPasswordFromIdentityProvider(account);
                    String password = (String) apiResponse.getData();

                    CreateRequest request = new CreateRequest()
                            .setEmail(account.getEmail())
                            .setPassword(password) // 🔥 Remplace par un mot de passe temporaire si haché
                            .setDisplayName(account.getPseudo());

                    UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
                    nbSynced++;
                    log.info("Utilisateur ajouté à Firebase : {} (UID: {})", account.getEmail(), userRecord.getUid());
                } catch (Exception ex) {
                    log.error("Erreur lors de l'ajout de l'utilisateur {} à Firebase : {}", account.getEmail(), ex.getMessage());
                }
            }
        }

        log.info("Synchronisation terminée : {} comptes synchronisés sur {} au total", nbSynced, nbTotal);
        return String.format("Synchronisation terminée : %d comptes synchronisés sur %d au total", nbSynced, nbTotal);
    }
}
