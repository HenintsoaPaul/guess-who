package itu.crypto.firebase.config;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FirestoreConfiguration {

    private final FirebaseApp firebaseApp;

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore(firebaseApp);
    }
}
