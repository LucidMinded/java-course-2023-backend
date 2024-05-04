package edu.java.service.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.bot.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final ApplicationConfig.KafkaConfig kafkaConfig;

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(kafkaConfig.topic(), update);
    }
}
