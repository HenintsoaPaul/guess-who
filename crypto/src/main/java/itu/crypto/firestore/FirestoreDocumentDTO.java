package itu.crypto.firestore;

import lombok.Data;

import java.util.Map;

@Data
public class FirestoreDocumentDTO {
    private String documentId;
    private Map<String, Object> data;
    private String createdAt;
    private String updatedAt;
}
