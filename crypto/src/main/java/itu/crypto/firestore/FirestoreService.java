package itu.crypto.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FirestoreService {

    private final Firestore firestore;

    public String saveData(String collection, FirestoreDocumentDTO documentDTO)
            throws ExecutionException, InterruptedException {

        CollectionReference collectionReference = firestore.collection(collection);

        String documentId = generateUniqueDocumentId(collectionReference);
        documentDTO.setDocumentId(documentId);

        HashMap<String, Object> dataWithTimestamp = new HashMap<>(documentDTO.getData());
        dataWithTimestamp.put("updatedAt", documentDTO.getUpdatedAt());

        WriteResult writeResult = collectionReference.document(documentDTO.getDocumentId()).set(documentDTO.getData()).get();
        return "Update time: " + writeResult.getUpdateTime();
    }

    /**
     * Génère un ID unique qui n'existe pas déjà dans Firestore.
     */
    private String generateUniqueDocumentId(CollectionReference collectionReference)
            throws ExecutionException, InterruptedException {
        String documentId;
        DocumentReference documentReference;

        do {
            documentId = UUID.randomUUID().toString();
            documentReference = collectionReference.document(documentId);
        } while (documentReference.get().get().exists());

        return documentId;
    }
}
