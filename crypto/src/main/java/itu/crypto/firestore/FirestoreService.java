package itu.crypto.firestore;

import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FirestoreService {

    private final Firestore firestore;

    public String saveData(String collection, FirestoreDocumentDTO documentDTO)
            throws ExecutionException, InterruptedException {

        CollectionReference collectionReference = firestore.collection(collection);
        String creationTime = Instant.now().toString();

        // Generate unique id if null or empty
        String uid = (documentDTO.getDocumentId() == null || documentDTO.getDocumentId().isEmpty())
                ? generateUniqueDocumentId(collectionReference) : documentDTO.getDocumentId();
        documentDTO.setDocumentId(uid);

        // createdAt
        DocumentReference documentReference = collectionReference.document(uid);
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        String createdAt = !documentSnapshot.exists() ? creationTime : documentSnapshot.getString("createdAt");
        documentDTO.setCreatedAt(createdAt);

        // updatedAt
        documentDTO.setUpdatedAt(creationTime);

        WriteResult writeResult = collectionReference.document(documentDTO.getDocumentId()).set(documentDTO.getData()).get();
        return log(writeResult, uid, createdAt, documentDTO.getUpdatedAt());
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

    private String log(WriteResult writeResult, String uid, String createdAt, String updatedAt) {
        return "id: " + uid + " | createdAt: " + createdAt + " | updatedAt: " + updatedAt;
    }
}
