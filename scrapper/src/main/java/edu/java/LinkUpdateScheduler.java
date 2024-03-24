package edu.java;

import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.configuration.ApplicationConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j @AllArgsConstructor @Component public class LinkUpdateScheduler {
    private final GithubClient githubClient;
    private final StackoverflowClient stackoverflowClient;
    private final ApplicationConfig applicationConfig;

    //    @Scheduled(fixedDelay = 60 * 1000)
    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("Updating links...");
    }
}
