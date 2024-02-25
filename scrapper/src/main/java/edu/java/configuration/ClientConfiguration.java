package edu.java.configuration;

import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
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
public class ClientConfiguration {
    public static GithubClient githubClient(String githubUrl) {
        @SuppressWarnings("MultipleStringLiterals")
        WebClient webClient = WebClient.builder()
            .baseUrl(githubUrl)
            .defaultHeader("Content-Type", GithubConfig.JSON_CONTENT_TYPE)
            .defaultHeader("Accept", GithubConfig.ACCEPT)
            .build();
        HttpServiceProxyFactory clientFactory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return clientFactory.createClient(GithubClient.class);
    }

    public static StackoverflowClient stackoverflowClient(String stackoverflowUrl) {
        WebClient webClient = WebClient.builder()
            .baseUrl(stackoverflowUrl)
            .defaultHeader("Content-Type", StackoverflowConfig.JSON_CONTENT_TYPE)
            .defaultHeader("Accept", StackoverflowConfig.ACCEPT)
            .build();
        HttpServiceProxyFactory clientFactory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return clientFactory.createClient(StackoverflowClient.class);
    }

    @Bean
    public GithubClient githubClient() {
        return githubClient(GithubConfig.API_URL);
    }

    @Bean
    public StackoverflowClient stackoverflowClient() {
        return stackoverflowClient(StackoverflowConfig.API_URL);
    }

}
