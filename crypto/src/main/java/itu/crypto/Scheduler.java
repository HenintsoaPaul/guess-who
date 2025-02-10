package itu.crypto;

import itu.crypto.service.CoursService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final CoursService coursService;

    @Scheduled(fixedRate = 10000)
    public void regenerateCours() {
        coursService.generateCours();
        log.info("Cours updated after 10 sec...");
    }
}
