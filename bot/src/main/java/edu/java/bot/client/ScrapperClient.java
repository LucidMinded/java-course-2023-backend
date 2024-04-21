package edu.java.bot.client;

import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("tg-chat/{id}")
    void addChat(@PathVariable("id") Long chatId);

    @DeleteExchange("tg-chat/{id}")
    void removeChat(@PathVariable("id") Long chatId);

    @GetExchange("tg-chat/{id}")
    Boolean isChatRegistered(@PathVariable("id") Long chatId);

    @GetExchange("links")
    ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long chatId);

    @PostExchange("links")
    LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest addLinkRequest);

    @DeleteExchange("links")
    LinkResponse removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest removeLinkRequest);
}
