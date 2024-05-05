package edu.java.bot.service;

import edu.java.bot.configuration.kafka.KafkaConfiguration;
import edu.java.dto.bot.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotDeadLetterQueueProducer {
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final KafkaConfiguration kafkaConfiguration;

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(kafkaConfiguration.getDLQTopic(), update);
    }
}
