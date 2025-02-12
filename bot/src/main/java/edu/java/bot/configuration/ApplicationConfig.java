package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotEmpty String telegramToken, @NotNull KafkaConfig kafkaConfig) {
    public static <S> S createClient(Class<S> clientClass, Map<String, String> headers, String baseUrl) {
        WebClient.Builder webclientBuilder = WebClient.builder().baseUrl(baseUrl);
        headers.forEach(webclientBuilder::defaultHeader);
        WebClient webClient = webclientBuilder.build();

        HttpServiceProxyFactory clientFactory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return clientFactory.createClient(clientClass);
    }

    public ScrapperClient scrapperClient(String botUrl) {
        return createClient(ScrapperClient.class, Map.of(
            "Content-Type", ScrapperClientConfig.CONTENT_TYPE,
            "Accept", ScrapperClientConfig.ACCEPT
        ), botUrl);
    }

    @Bean
    public ScrapperClient scrapperClient() {
        return scrapperClient(ScrapperClientConfig.API_URL);
    }

    @Bean
    public KafkaConfig kafkaConfig() {
        return kafkaConfig;
    }

    public record KafkaConfig(@NotNull String topic, @NotNull String groupId, @NotNull String bootstrapServers) {
    }
}
