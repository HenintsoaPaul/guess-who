package itu.crypto.firebase.firestore.generalisation;

public interface TimestampedDocument {
    void setCreatedAt(String createdAt);
    void setUpdatedAt(String updatedAt);
    String getCreatedAt();
}
