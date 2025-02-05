package itu.crypto.firebase.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseMessagingConfiguration {

    @Value("classpath:/private-key.json")
    private Resource privateKey;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream credentials = new ByteArrayInputStream(privateKey.getContentAsByteArray());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
