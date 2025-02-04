package itu.crypto.firebase.firestore.generalisation;

import com.google.cloud.firestore.Firestore;

import java.util.List;

public interface ISyncService<T> {

    void syncWithFirebase(Firestore firestore, List<T> data);
}
