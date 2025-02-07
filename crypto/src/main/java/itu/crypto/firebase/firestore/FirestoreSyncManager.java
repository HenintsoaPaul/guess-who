package itu.crypto.firebase.firestore;

import itu.crypto.firebase.firestore.generalisation.FirestoreSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class FirestoreSyncManager {

    private final List<FirestoreSyncService<?, ?>> listeners;
    private final List<String> collectionNames;

    protected FirestoreSyncManager(
            List<FirestoreSyncService<?, ?>> listeners,
            @Value("${firestore.listen.collections.sync}") List<String> collectionNames) {
        this.listeners = listeners;
        this.collectionNames = collectionNames;
    }

    @PostConstruct
    public void init() {
        log.info("[local->firebase] Replication db local begins...");

        listeners.forEach(listener -> {
            if (collectionNames.contains(listener.getCollectionName())) {
                listener.syncWithFirebase();
            } else {
                log.info("Replication ignoré pour la collection: {}", listener.getCollectionName());
            }
        });

        log.info("[local->firebase] Replication db local complete.");
    }
}
