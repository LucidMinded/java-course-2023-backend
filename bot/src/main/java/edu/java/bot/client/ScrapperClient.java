package edu.java.bot.client;

import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("tg-chat/{id}")
    void addChat(@PathVariable("id") Long id);

    @DeleteExchange("tg-chat/{id}")
    void removeChat(@PathVariable("id") Long id);

    @GetExchange("links")
    ListLinksResponse getLinks(Long tgChatId);

    @PostExchange("links")
    LinkResponse addLink(String url);

    @DeleteExchange("links")
    LinkResponse removeLink(String url);

}
