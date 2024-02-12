package development.apodbackend.services;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class SchedulerService {
    @Scheduled(fixedDelay=15000)
    void taskLog() {
        System.err.println(".");
    }
}