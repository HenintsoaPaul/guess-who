package itu.crypto;

import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final CoursService coursService;

    @Scheduled(fixedRate = 10000)
    public void regenerateCours() {
        coursService.generateCours();
        System.out.println("Cours updated after 10 sec...\n");
    }
}
