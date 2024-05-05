package edu.java.configuration;

import edu.java.configuration.access.AccessType;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull Scheduler scheduler, @NotNull AccessType databaseAccessType,
                                @NotNull RetryConfig retryConfig, @NotNull KafkaConfig kafkaConfig,
                                boolean useQueue) {

    public Duration getUpdateRequestInterval() {
        return Duration.ofMinutes(1);
    }

    @Bean
    public Scheduler scheduler() {
        return scheduler;
    }

    @Bean
    public RetryConfig retryConfig() {
        return retryConfig;
    }

    @Bean
    public KafkaConfig kafkaConfig() {
        return kafkaConfig;
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record RetryConfig(int maxAttempts, @NotNull Duration interval, @NotNull String strategy,
                              @NotNull List<Integer> statusCodes) {
    }

    public record KafkaConfig(@NotNull String topic, @NotNull String groupId, @NotNull String bootstrapServers) {
    }
}
