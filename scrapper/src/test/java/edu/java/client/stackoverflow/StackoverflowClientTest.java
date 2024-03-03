package edu.java.client.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.stackoverflow.dto.QuestionDto;
import edu.java.configuration.ClientConfiguration;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class StackoverflowClientTest {
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void test() {
        StackoverflowClient stackoverflowClientLocal = ClientConfiguration.stackoverflowClient("http://localhost:8080");

        stubFor(get(urlEqualTo("/questions/1732348"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "  \"items\": [\n" +
                    "    {\n" +
                    "      \"tags\": [\n" +
                    "        \"html\",\n" +
                    "        \"regex\",\n" +
                    "        \"xhtml\"\n" +
                    "      ],\n" +
                    "      \"owner\": {\n" +
                    "        \"account_id\": 47944,\n" +
                    "        \"reputation\": 6064,\n" +
                    "        \"user_id\": 142233,\n" +
                    "        \"user_type\": \"registered\",\n" +
                    "        \"accept_rate\": 100,\n" +
                    "        \"profile_image\": \"https://i.stack.imgur.com/3h2RG.png?s=256&g=1\",\n" +
                    "        \"display_name\": \"Jeff\",\n" +
                    "        \"link\": \"https://stackoverflow.com/users/142233/jeff\"\n" +
                    "      },\n" +
                    "      \"is_answered\": true,\n" +
                    "      \"view_count\": 3774367,\n" +
                    "      \"protected_date\": 1291642187,\n" +
                    "      \"accepted_answer_id\": 1732454,\n" +
                    "      \"answer_count\": 37,\n" +
                    "      \"community_owned_date\": 1258593236,\n" +
                    "      \"score\": 2233,\n" +
                    "      \"locked_date\": 1339098076,\n" +
                    "      \"last_activity_date\": 1707758657,\n" +
                    "      \"creation_date\": 1258151906,\n" +
                    "      \"last_edit_date\": 1705914929,\n" +
                    "      \"question_id\": 1732348,\n" +
                    "      \"content_license\": \"CC BY-SA 4.0\",\n" +
                    "      \"link\": \"https://stackoverflow.com/questions/1732348/regex-match-open-tags-except-xhtml-self-contained-tags\",\n" +
                    "      \"title\": \"RegEx match open tags except XHTML self-contained tags\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"has_more\": false,\n" +
                    "  \"quota_max\": 10000,\n" +
                    "  \"quota_remaining\": 9986\n" +
                    "}")));

        QuestionDto questionDto = stackoverflowClientLocal.getQuestionResponse("1732348").getQuestions().getFirst();
        Assertions.assertEquals(OffsetDateTime.parse("2024-02-12T17:24:17Z"), questionDto.getLastActivityDate());
    }
}
