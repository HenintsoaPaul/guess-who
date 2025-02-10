package itu.crypto.firebase.firestore.generalisation;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List<T> findAll();

    Optional<T> findById(int id);
}
