package itu.crypto.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import itu.crypto.dto.ApiResponse;
import itu.crypto.entity.Account;
import itu.crypto.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountSyncService {

    private final AccountService accountService;

    /**
     * Synchroniser les comptes existantes avec la liste des utilisateurs de firebase auth.
     */
    public String syncWithFirebase() {
        List<Account> accounts = accountService.findAll();

        int nbSynced = 0, nb = accounts.size();
        for (Account account : accounts) {
            try {
                // Vérifier si l'utilisateur existe déjà dans Firebase
                FirebaseAuth.getInstance().getUserByEmail(account.getEmail());

                System.out.println("Utilisateur déjà synchronisé : " + account.getEmail());
            } catch (Exception e) {
                // Si l'utilisateur n'existe pas, l'ajouter à Firebase Auth
                try {
                    ApiResponse apiResponse = accountService.fetchPasswordFromIdentityProvider(account);
                    String email = (String) apiResponse.getData();

                    CreateRequest request = new CreateRequest()
                            .setEmail(account.getEmail())
                            .setPassword(email) // 🔥 Remplace par un mot de passe temporaire si haché
                            .setDisplayName(account.getPseudo());

                    UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

                    System.out.println("Utilisateur ajouté à Firebase : " + userRecord.getUid());
                } catch (Exception ex) {

                    System.err.println("Erreur lors de l'ajout de l'utilisateur : " + account.getEmail());
                }
            }
        }

        return "Sync done! Synced " + nbSynced + " accounts on " + nb + " total accounts";
    }
}
