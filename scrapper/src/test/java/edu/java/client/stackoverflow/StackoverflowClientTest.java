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
                    }""")));

        QuestionDto questionDto = stackoverflowClientLocal.getQuestionResponse("1732348").getQuestions().getFirst();
        Assertions.assertEquals(OffsetDateTime.parse("2024-02-12T17:24:17Z"), questionDto.getLastActivityDate());
    }
}
