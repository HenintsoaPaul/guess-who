package itu.crypto.repository;

import itu.crypto.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRepository extends JpaRepository<Classification, String> {

    // Trouver une classification par son id
    Classification findByIdClassification(String idClassification);
}
