package itu.crypto;

import itu.crypto.firebase.firestore.cours.CoursSyncService;
import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final CoursService coursService;
    private final CoursSyncService coursSyncService;

    @Scheduled(fixedRate = 10000)
    public void regenerateCours() {
//        coursService.generateCours();
//        coursSyncService.syncWithFirebase();

        System.out.println("Cours updated after 10 sec...\n");

        // todo: sync with firebase
        // ...
    }
}
