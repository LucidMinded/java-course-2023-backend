package edu.java.client.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import edu.java.client.stackoverflow.dto.QuestionDto;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.RetryConfiguration;
import edu.java.configuration.client.ClientConfiguration;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class StackoverflowClientTest {
    private WireMockServer wireMockServer;
    private StackoverflowClient stackoverflowClient;

    private static ResponseDefinitionBuilder getResponse() {
        return aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                  "items": [
                    {
                      "tags": [
                        "html",
                        "regex",
                        "xhtml"
                      ],
                      "owner": {
                        "account_id": 47944,
                        "reputation": 6064,
                        "user_id": 142233,
                        "user_type": "registered",
                        "accept_rate": 100,
                        "profile_image": "https://i.stack.imgur.com/3h2RG.png?s=256&g=1",
                        "display_name": "Jeff",
                        "link": "https://stackoverflow.com/users/142233/jeff"
                      },
                      "is_answered": true,
                      "view_count": 3774367,
                      "protected_date": 1291642187,
                      "accepted_answer_id": 1732454,
                      "answer_count": 37,
                      "community_owned_date": 1258593236,
                      "score": 2233,
                      "locked_date": 1339098076,
                      "last_activity_date": 1707758657,
                      "creation_date": 1258151906,
                      "last_edit_date": 1705914929,
                      "question_id": 1732348,
                      "content_license": "CC BY-SA 4.0",
                      "link": "https://stackoverflow.com/questions/1732348/regex-match-open-tags-except-xhtml-self-contained-tags",
                      "title": "RegEx match open tags except XHTML self-contained tags"
                    }
                  ],
                  "has_more": false,
                  "quota_max": 10000,
                  "quota_remaining": 9986
                }""");
    }

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        ApplicationConfig.RetryConfig retryConfig =
            new ApplicationConfig.RetryConfig(3, Duration.ofSeconds(2), "linear", List.of(500));
        RetryConfiguration retryConfiguration = new RetryConfiguration(retryConfig);
        Retry retry = retryConfiguration.retry();
        ClientConfiguration clientConfiguration = new ClientConfiguration(retryConfig, retry);
        stackoverflowClient = clientConfiguration.stackoverflowClient("http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void test_success() {
        stubFor(get(urlEqualTo("/questions/1732348?site=stackoverflow"))
            .willReturn(getResponse()));

        QuestionDto questionDto = stackoverflowClient.getQuestionResponse("1732348").getQuestions().getFirst();
        Assertions.assertEquals(OffsetDateTime.parse("2024-02-12T17:24:17Z"), questionDto.getLastActivityDate());
    }

    @Test
    public void test_failureThenSuccessOnRetry() {
        stubFor(get(urlEqualTo("/questions/1732348?site=stackoverflow"))
            .inScenario("Retry Scenario")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse().withStatus(500))
            .willSetStateTo("Cause Success"));

        stubFor(get(urlEqualTo("/questions/1732348?site=stackoverflow"))
            .inScenario("Retry Scenario")
            .whenScenarioStateIs("Cause Success")
            .willReturn(getResponse()));

        QuestionDto questionDto = stackoverflowClient.getQuestionResponse("1732348").getQuestions().getFirst();
        Assertions.assertEquals(OffsetDateTime.parse("2024-02-12T17:24:17Z"), questionDto.getLastActivityDate());
    }

    @Test
    public void test_failureOnRetry() {
        stubFor(get(urlEqualTo("/questions/1732348?site=stackoverflow"))
            .willReturn(aResponse().withStatus(500)));

        Assertions.assertThrows(
            RuntimeException.class,
            () -> stackoverflowClient.getQuestionResponse("1732348").getQuestions().getFirst()
        );
    }
}
