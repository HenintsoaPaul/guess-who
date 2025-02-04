package itu.crypto.firebase.firestore.generalisation;

import java.util.List;

public interface BaseService<T> {

    List<T> findAll();
}
