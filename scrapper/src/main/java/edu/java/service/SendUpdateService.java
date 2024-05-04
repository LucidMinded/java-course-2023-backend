package edu.java.service;

import edu.java.client.bot.BotClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.bot.request.LinkUpdateRequest;
import edu.java.service.kafka.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendUpdateService {
    private final ApplicationConfig applicationConfig;
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final BotClient botClient;

    public void sendUpdate(LinkUpdateRequest update) {
        log.info("Sending update for link: {}", update.getUrl());
        if (applicationConfig.useQueue()) {
            scrapperQueueProducer.send(update);
        } else {
            botClient.updates(update);
        }
    }
}
