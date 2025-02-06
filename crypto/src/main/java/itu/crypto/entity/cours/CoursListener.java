package itu.crypto.entity.cours;

import itu.crypto.firebase.firestore.cours.CoursSyncService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CoursListener {

    private final CoursSyncService coursSyncService;

    @Autowired
    public CoursListener(@Lazy CoursSyncService coursSyncService) {
        this.coursSyncService = coursSyncService;
    }

    @PostPersist
    public void apresSauvegarde(Cours cours) {
        coursSyncService.saveAsDocument(cours);
    }

    @PostUpdate
    public void apresModification(Cours cours) {
        coursSyncService.updateAsDocument(cours);
    }
}
