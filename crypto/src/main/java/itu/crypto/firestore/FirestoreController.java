package itu.crypto.firestore;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firestore")
public class FirestoreController {

    private final FirestoreService firestoreService;

    @PostMapping("/save/{collection}")
    public ResponseEntity<String> saveData(
            @PathVariable String collection,
            @RequestBody FirestoreDocumentDTO documentDTO)
            throws ExecutionException, InterruptedException {

        String response = firestoreService.saveData(collection, documentDTO);
        return ResponseEntity.ok(response);
    }
}
