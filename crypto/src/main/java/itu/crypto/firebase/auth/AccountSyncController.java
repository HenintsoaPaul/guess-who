package itu.crypto.firebase.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firebase/auth")
@RequiredArgsConstructor
public class AccountSyncController {

    private final AccountSyncService accountSyncService;

    @GetMapping("/sync")
    public String syncUsers() {
        return accountSyncService.syncWithFirebase();
    }
}
