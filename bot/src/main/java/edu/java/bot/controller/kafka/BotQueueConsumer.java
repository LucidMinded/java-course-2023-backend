package edu.java.bot.controller.kafka;

import edu.java.bot.service.UpdateHandlingService;
import edu.java.dto.bot.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotQueueConsumer {
    private final UpdateHandlingService updateHandlingService;

    @KafkaListener(topics = "${app.kafka-config.topic}", groupId = "${app.kafka-config.group-id}")
    public void listen(@Valid LinkUpdateRequest update) {
        updateHandlingService.handleLinkUpdate(update);
    }
}
