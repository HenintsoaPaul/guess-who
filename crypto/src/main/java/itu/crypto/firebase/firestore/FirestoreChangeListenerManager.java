package itu.crypto.firebase.firestore;

import itu.crypto.firebase.firestore.generalisation.FirestoreChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class FirestoreChangeListenerManager {

    private final List<FirestoreChangeListener<?, ?>> listeners;
    private final List<String> collectionNames;

    protected FirestoreChangeListenerManager(
            List<FirestoreChangeListener<?, ?>> listeners,
            @Value("${firestore.listen.collections.listen}") List<String> collectionNames) {
        this.listeners = listeners;
        this.collectionNames = collectionNames;
    }

    @PostConstruct
    public void init() {
        listeners.forEach(listener -> {
            if (collectionNames.contains(listener.getCollectionName())) {
                listener.startListening();
            } else {
                log.info("Listener ignoré pour la collection: {}", listener.getCollectionName());
            }
        });
    }
}
