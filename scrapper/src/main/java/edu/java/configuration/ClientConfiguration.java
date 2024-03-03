package edu.java.configuration;

import edu.java.client.bot.BotClient;
import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableScheduling
@AllArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class ClientConfiguration {
    public static <S> S createClient(Class<S> clientClass, Map<String, String> headers, String baseUrl) {
        WebClient.Builder webclientBuilder = WebClient.builder().baseUrl(baseUrl);
        headers.forEach(webclientBuilder::defaultHeader);
        WebClient webClient = webclientBuilder.build();

        HttpServiceProxyFactory clientFactory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return clientFactory.createClient(clientClass);
    }

    public static GithubClient githubClient(String githubUrl) {
        return createClient(GithubClient.class, Map.of(
            "Content-Type", GithubClientConfig.CONTENT_TYPE,
            "Accept", GithubClientConfig.ACCEPT
        ), githubUrl);
    }

    public static StackoverflowClient stackoverflowClient(String stackoverflowUrl) {
        return createClient(StackoverflowClient.class, Map.of(
            "Content-Type", StackoverflowClientConfig.CONTENT_TYPE,
            "Accept", StackoverflowClientConfig.ACCEPT
        ), stackoverflowUrl);
    }

    public static BotClient botClient(String botUrl) {
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
