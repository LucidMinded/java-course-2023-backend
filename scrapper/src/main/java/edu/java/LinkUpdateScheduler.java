package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.service.LinkUpdater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j @AllArgsConstructor @Component public class LinkUpdateScheduler {
    private final ApplicationConfig applicationConfig;
    private final LinkUpdater linkUpdater;

    //    @Scheduled(fixedDelay = 60 * 1000)
    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("Updating links...");
        linkUpdater.update();
    }
}

