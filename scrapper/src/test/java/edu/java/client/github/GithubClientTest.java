package edu.java.client.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.github.dto.RepositoryEventDto;
import edu.java.configuration.ClientConfiguration;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class GithubClientTest {
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
        GithubClient githubClientLocal = ClientConfiguration.githubClient("http://localhost:8080");

        stubFor(get(urlEqualTo("/repos/octocat/Hello-World/events"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [
                        {
                            "id": "22249084964",
                            "type": "PushEvent",
                            "actor": {
                                "id": 583231,
                                "login": "octocat",
                                "display_login": "octocat",
                                "gravatar_id": "",
                                "url": "https://api.github.com/users/octocat",
                                "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                            },
                            "repo": {
                                "id": 1296269,
                                "name": "octocat/Hello-World",
                                "url": "https://api.github.com/repos/octocat/Hello-World"
                            },
                            "payload": {
                                "push_id": 10115855396,
                                "size": 1,
                                "distinct_size": 1,
                                "ref": "refs/heads/master",
                                "head": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                                "before": "883efe034920928c47fe18598c01249d1a9fdabd",
                                "commits": [
                                    {
                                        "sha": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                                        "author": {
                                            "email": "octocat@github.com",
                                            "name": "Monalisa Octocat"
                                        },
                                        "message": "commit",
                                        "distinct": true,
                                        "url": "https://api.github.com/repos/octocat/Hello-World/commits/7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300"
                                    }
                                ]
                            },
                            "public": true,
                            "created_at": "2022-06-09T12:47:28Z"
                        },
                        {
                            "id": "22237752260",
                            "type": "WatchEvent",
                            "actor": {
                                "id": 583231,
                                "login": "octocat",
                                "display_login": "octocat",
                                "gravatar_id": "",
                                "url": "https://api.github.com/users/octocat",
                                "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                            },
                            "repo": {
                                "id": 1296269,
                                "name": "octocat/Hello-World",
                                "url": "https://api.github.com/repos/octocat/Hello-World"
                            },
                            "payload": {
                                "action": "started"
                            },
                            "public": true,
                            "created_at": "2022-06-08T23:29:25Z"
                        }
                    ]
                    """)));

        List<RepositoryEventDto> events = githubClientLocal.repoEvents("octocat", "Hello-World");
        events.removeIf(Objects::isNull);
        Assertions.assertEquals(1, events.size());
    }
}
