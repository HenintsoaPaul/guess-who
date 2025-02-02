package itu.crypto.firebase.firestore;

import lombok.Data;

import java.util.Map;

@Data
public class FirestoreDocumentDTO {
    private String documentId;
    private Map<String, Object> data;
    private String createdAt;
    private String updatedAt;

    public Map<String, Object> getData() {
        data.put("updatedAt", this.getUpdatedAt());
        data.put("createdAt", this.getCreatedAt());
        return data;
    }
}
