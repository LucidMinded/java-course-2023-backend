package edu.java.bot.configuration.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.dto.bot.request.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {
    public static final int DLQ_PARTITION_COUNT = 1;
    public static final int DLQ_REPLICA_COUNT = 1;
    public static final String DLQ_SUFFIX = "_dlq";
    private final ApplicationConfig.KafkaConfig kafkaConfig;

    @Bean
    public ConsumerFactory<String, LinkUpdateRequest> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.bootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.groupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> kafkaListenerContainerFactory(
        KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler(kafkaTemplate));
        return factory;
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate) {
        return new DefaultErrorHandler(
            new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (cr, e) -> new TopicPartition(cr.topic() + DLQ_SUFFIX, cr.partition())
            ),
            new FixedBackOff(0L, 2L)
        );
    }

    @Bean
    public ProducerFactory<String, LinkUpdateRequest> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.bootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic beanDLQTopic() {
        return TopicBuilder.name(getDLQTopic())
            .partitions(DLQ_PARTITION_COUNT)
            .replicas(DLQ_REPLICA_COUNT)
            .build();
    }

    public String getDLQTopic() {
        return kafkaConfig.topic() + DLQ_SUFFIX;
    }
}

