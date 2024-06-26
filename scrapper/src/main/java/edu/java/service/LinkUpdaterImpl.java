package edu.java.service;

import edu.java.GithubPathParser;
import edu.java.StackoverflowPathParser;
import edu.java.TrackedLink;
import edu.java.URLParser;
import edu.java.client.github.GithubClient;
import edu.java.client.github.dto.RepositoryEventDto;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.client.stackoverflow.dto.StackoverflowResponseDto;
import edu.java.configuration.ApplicationConfig;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.dto.LinkDto;
import edu.java.dto.bot.request.LinkUpdateRequest;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterImpl implements LinkUpdater {
    private final ApplicationConfig applicationConfig;
    private final GithubClient githubClient;
    private final StackoverflowClient stackoverflowClient;
    private final LinkService linkService;
    private final ChatService chatService;
    private final SendUpdateService sendUpdateService;

    private final Map<String, BiFunction<LinkDto, URLParser.ParsedURL, Boolean>> updaters = Map.of(
        TrackedLink.GITHUB.getHost(), this::githubUpdate,
        TrackedLink.STACKOVERFLOW.getHost(), this::stackoverflowUpdate
    );

    @Override
    public int update() {
        Collection<LinkDto> updatedLongAgo = linkService.listAllByUpdatedBefore(
            OffsetDateTime.now().minus(applicationConfig.getUpdateRequestInterval()));

        int cntUpdated = 0;
        for (LinkDto link : updatedLongAgo) {
            URLParser.ParsedURL parsedURL = URLParser.parse(link.getUrl());
            if (parsedURL == null || parsedURL.host() == null) {
                log.info("Failed to parse URL: {}", link.getUrl());
                continue;
            }

            try {
                var updater = updaters.get(parsedURL.host());
                if (updater != null) {
                    cntUpdated += updater.apply(link, parsedURL) ? 1 : 0;
                } else {
                    log.info("Unsupported resource: {}", parsedURL.host());
                }
            } catch (Exception e) {
                log.error("Failed to update link: {}", link, e);
            }
            link.setUpdatedAt(OffsetDateTime.now());
            linkService.update(link);
        }
        return cntUpdated;
    }

    private boolean stackoverflowUpdate(LinkDto link, URLParser.ParsedURL parsedURL) {
        StackoverflowPathParser.ParsedPath parsedPath = StackoverflowPathParser.parse(parsedURL.path());
        if (parsedPath == null) {
            log.info("Failed to parse StackOverflow path: {}", parsedURL.path());
            return false;
        }
        StackoverflowResponseDto responseDto = stackoverflowClient.getQuestionResponse(parsedPath.id());
        if (responseDto != null) {
            log.info("Received stackoverflow response: {}", responseDto);
            OffsetDateTime responseLastActivity = responseDto.getQuestions().getFirst().getLastActivityDate();
            if (responseLastActivity.isAfter(link.getLastActivity())) {
                sendUpdateService.sendUpdate(new LinkUpdateRequest(
                    link.getId(),
                    link.getUrl(),
                    responseDto.toString(),
                    chatService.listAllByLinkId(link.getId()).stream().map(ChatDto::getId).toList()
                ));
                link.setLastActivity(responseLastActivity);
                return true;
            }
        }
        return false;
    }

    private boolean githubUpdate(LinkDto link, URLParser.ParsedURL parsedURL) {
        GithubPathParser.ParsedPath parsedPath = GithubPathParser.parse(parsedURL.path());
        if (parsedPath == null) {
            log.info("Failed to parse GitHub path: {}", parsedURL.path());
            return false;
        }

        List<RepositoryEventDto> repoEvents = githubClient.repoEvents(parsedPath.owner(), parsedPath.repo());
        if (repoEvents != null) {
            repoEvents.removeIf(Objects::isNull);
            for (RepositoryEventDto event : repoEvents) {
                if (event == null) {
                    continue;
                }
                if (event.getCreatedAt().isAfter(link.getLastActivity())) {
                    log.info("Received github event: {}", event);
                    sendUpdateService.sendUpdate(new LinkUpdateRequest(
                        link.getId(),
                        link.getUrl(),
                        event.toString(),
                        chatService.listAllByLinkId(link.getId()).stream().map(ChatDto::getId).toList()
                    ));
                    link.setLastActivity(event.getCreatedAt());
                    return true;
                }
            }
        }
        return false;
    }
}
