package itu.crypto;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(fixedRate = 10000)
    public void asaBe() {
        System.out.println("Manao update toutes les 10 secondes...\n");
    }
}
