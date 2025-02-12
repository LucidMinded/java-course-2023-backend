package edu.java.configuration;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {
    private final ApplicationConfig.RetryConfig retryConfig;

    @Bean
    public Retry retry() {
        final int maxAttempts = retryConfig.maxAttempts();
        final Duration interval = retryConfig.interval();

        return switch (retryConfig.strategy()) {
            case "constant" -> Retry.fixedDelay(maxAttempts, interval).doBeforeRetry(this::logRetry);
            case "exponential" -> Retry.backoff(maxAttempts, interval).doBeforeRetry(this::logRetry);
            case "linear" -> linearRetry(maxAttempts, interval);
            default -> throw new IllegalArgumentException("Unknown retry strategy: " + retryConfig.strategy());
        };
    }

    private @NotNull Retry linearRetry(int maxAttempts, @NotNull Duration interval) {
        return Retry.from(companion -> companion
            .flatMap(retrySignal -> {
                if (retrySignal.totalRetries() < maxAttempts) {
                    logRetry(retrySignal);
                    return Mono.delay(Duration.ofSeconds(
                        retrySignal.totalRetries() * interval.getSeconds()));
                } else {
                    return Mono.error(new RuntimeException("Giving up after %d retries".formatted(maxAttempts)));
                }
            })
        );
    }

    private void logRetry(Retry.RetrySignal retrySignal) {
        log.debug("Retrying request for the {} time", retrySignal.totalRetries() + 1);
    }
}
