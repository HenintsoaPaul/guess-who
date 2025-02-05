package itu.crypto.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    @Value("classpath:/private-key.json")
    private Resource privateKey;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        InputStream credentials = new ByteArrayInputStream(privateKey.getContentAsByteArray());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();

        return FirebaseApp.initializeApp(firebaseOptions);
    }
}
