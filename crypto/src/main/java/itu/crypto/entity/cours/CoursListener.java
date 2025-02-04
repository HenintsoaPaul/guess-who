package itu.crypto.entity.cours;

import itu.crypto.firebase.firestore.cours.CoursSyncService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoursListener {

    private final CoursSyncService coursSyncService;

//    @PrePersist
//    public void avantSauvegarde(Cours cours) {
//        // Actions à effectuer AVANT l'insertion ou la mise à jour
//        System.out.println("Avant sauvegarde : " + cours);
//    }

    @PostPersist
    public void apresSauvegarde(Cours cours) {
        // Actions à effectuer APRES l'insertion
        System.out.println("Après insertion : " + cours);
    }

//    @PreUpdate
//    public void avantModification(Cours cours) {
//        // Actions à effectuer AVANT la mise à jour
//        System.out.println("Avant modification : " + cours);
//    }

    @PostUpdate
    public void apresModification(Cours cours) {
        coursSyncService.syncWithFirebase();
    }

//    @PreRemove
//    public void avantSuppression(Cours cours) {
//        // Actions à effectuer AVANT la suppression
//        System.out.println("Avant suppression : " + cours);
//    }
//
//    @PostRemove
//    public void apresSuppression(Cours cours) {
//        // Actions à effectuer APRES la suppression
//        System.out.println("Après suppression : " + cours);
//    }
}
