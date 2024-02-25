package edu.java.client.github;

import edu.java.client.github.dto.RepositoryEventDto;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GithubClient {
    @GetExchange("/repos/{owner}/{repo}/events")
    List<RepositoryEventDto> repoEvents(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
