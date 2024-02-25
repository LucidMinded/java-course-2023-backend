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
                .withBody("[\n" +
                    "    {\n" +
                    "        \"id\": \"22249084964\",\n" +
                    "        \"type\": \"PushEvent\",\n" +
                    "        \"actor\": {\n" +
                    "            \"id\": 583231,\n" +
                    "            \"login\": \"octocat\",\n" +
                    "            \"display_login\": \"octocat\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"url\": \"https://api.github.com/users/octocat\",\n" +
                    "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=4\"\n" +
                    "        },\n" +
                    "        \"repo\": {\n" +
                    "            \"id\": 1296269,\n" +
                    "            \"name\": \"octocat/Hello-World\",\n" +
                    "            \"url\": \"https://api.github.com/repos/octocat/Hello-World\"\n" +
                    "        },\n" +
                    "        \"payload\": {\n" +
                    "            \"push_id\": 10115855396,\n" +
                    "            \"size\": 1,\n" +
                    "            \"distinct_size\": 1,\n" +
                    "            \"ref\": \"refs/heads/master\",\n" +
                    "            \"head\": \"7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\",\n" +
                    "            \"before\": \"883efe034920928c47fe18598c01249d1a9fdabd\",\n" +
                    "            \"commits\": [\n" +
                    "                {\n" +
                    "                    \"sha\": \"7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\",\n" +
                    "                    \"author\": {\n" +
                    "                        \"email\": \"octocat@github.com\",\n" +
                    "                        \"name\": \"Monalisa Octocat\"\n" +
                    "                    },\n" +
                    "                    \"message\": \"commit\",\n" +
                    "                    \"distinct\": true,\n" +
                    "                    \"url\": \"https://api.github.com/repos/octocat/Hello-World/commits/7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"public\": true,\n" +
                    "        \"created_at\": \"2022-06-09T12:47:28Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"22237752260\",\n" +
                    "        \"type\": \"WatchEvent\",\n" +
                    "        \"actor\": {\n" +
                    "            \"id\": 583231,\n" +
                    "            \"login\": \"octocat\",\n" +
                    "            \"display_login\": \"octocat\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"url\": \"https://api.github.com/users/octocat\",\n" +
                    "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=4\"\n" +
                    "        },\n" +
                    "        \"repo\": {\n" +
                    "            \"id\": 1296269,\n" +
                    "            \"name\": \"octocat/Hello-World\",\n" +
                    "            \"url\": \"https://api.github.com/repos/octocat/Hello-World\"\n" +
                    "        },\n" +
                    "        \"payload\": {\n" +
                    "            \"action\": \"started\"\n" +
                    "        },\n" +
                    "        \"public\": true,\n" +
                    "        \"created_at\": \"2022-06-08T23:29:25Z\"\n" +
                    "    }\n" +
                    "]\n")));

        List<RepositoryEventDto> events = githubClientLocal.repoEvents("octocat", "Hello-World");
        events.removeIf(Objects::isNull);
        Assertions.assertEquals(1, events.size());
    }
}
