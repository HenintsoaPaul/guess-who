package itu.crypto.firebase.firestore.cours;

import com.google.cloud.Timestamp;
import itu.crypto.entity.Cours;
import itu.crypto.entity.Crypto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.Date;

@Data
@NoArgsConstructor
public class CoursDocument {

    private Integer id;
    private double pu;
    private Timestamp dateCours;
    private Crypto crypto;

    private String createdAt;
    private String updatedAt;

    public CoursDocument(Cours cours) {
        this.id = cours.getId();
        this.pu = cours.getPu();
        this.dateCours = Timestamp.of(Date.from(cours.getDateCours().toInstant(ZoneOffset.UTC)));
        this.crypto = cours.getCrypto();
    }

    public Cours toEntity() {
        return new Cours(
                id,
                pu,
                dateCours.toDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                crypto
        );
    }
}
