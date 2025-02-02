package itu.crypto.firebase;

import com.google.cloud.firestore.Firestore;

import java.util.List;

public interface ISyncService<T> {

    String syncWithFirebase(Firestore firestore, List<T> data);
}
