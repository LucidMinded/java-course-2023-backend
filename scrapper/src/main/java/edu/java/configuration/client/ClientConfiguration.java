package edu.java.configuration.client;

import edu.java.client.bot.BotClient;
import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.configuration.ApplicationConfig;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
@Slf4j
public class ClientConfiguration {
    public static final int WEBCLIENT_MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024; // 16MB
    private final ApplicationConfig.RetryConfig retryConfig;
    private final Retry retry;

    public <S> S createClient(Class<S> clientClass, Map<String, String> headers, String baseUrl) {
        WebClient.Builder webclientBuilder = WebClient.builder()
            .baseUrl(baseUrl)
            .filter(this::retryFilter)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(WEBCLIENT_MAX_IN_MEMORY_SIZE));
        headers.forEach(webclientBuilder::defaultHeader);
        WebClient webClient = webclientBuilder.build();

        HttpServiceProxyFactory clientFactory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return clientFactory.createClient(clientClass);
    }

    private Mono<ClientResponse> retryFilter(ClientRequest request, ExchangeFunction next) {
        return next.exchange(request)
            .flatMap(clientResponse -> Mono.just(clientResponse)
                .filter(response -> retryConfig.statusCodes().contains(clientResponse.statusCode().value()))
                .flatMap(response -> clientResponse.createException())
                .flatMap(Mono::error)
                .thenReturn(clientResponse))
            .retryWhen(retry);
    }

    public GithubClient githubClient(String githubUrl) {
        return createClient(GithubClient.class, Map.of(
            "Content-Type", GithubClientConfig.CONTENT_TYPE,
            "Accept", GithubClientConfig.ACCEPT
        ), githubUrl);
    }

    public StackoverflowClient stackoverflowClient(String stackoverflowUrl) {
        return createClient(StackoverflowClient.class, Map.of(
            "Content-Type", StackoverflowClientConfig.CONTENT_TYPE,
            "Accept", StackoverflowClientConfig.ACCEPT
        ), stackoverflowUrl);
    }

    public BotClient botClient(String botUrl) {
        return createClient(BotClient.class, Map.of(
            "Content-Type", BotClientConfig.CONTENT_TYPE,
            "Accept", BotClientConfig.ACCEPT
        ), botUrl);
    }

    @Bean
    public GithubClient githubClient() {
        return githubClient(GithubClientConfig.API_URL);
    }

    @Bean
    public StackoverflowClient stackoverflowClient() {
        return stackoverflowClient(StackoverflowClientConfig.API_URL);
    }

    @Bean
    public BotClient botClient() {
        return botClient(BotClientConfig.API_URL);
    }
}
