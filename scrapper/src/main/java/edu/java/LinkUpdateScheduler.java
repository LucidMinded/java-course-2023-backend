package edu.java;

import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j @AllArgsConstructor @Component public class LinkUpdateScheduler {
    private final GithubClient githubClient;
    private final StackoverflowClient stackoverflowClient;

    @Scheduled(fixedDelay = 60 * 1000)
//    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Updating links...");
    }
}
