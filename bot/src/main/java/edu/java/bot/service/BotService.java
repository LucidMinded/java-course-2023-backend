package edu.java.bot.service;

import edu.java.bot.client.ScrapperClient;
import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;

@Service
@AllArgsConstructor
public class BotService {
    private final ScrapperClient scrapperClient;

    public void registerChat(Long id) {
        scrapperClient.addChat(id);
    }

    public void unregisterChat(Long id) {
        scrapperClient.removeChat(id);
    }

    public boolean isChatRegistered(Long id) {
        return scrapperClient.isChatRegistered(id);
    }

    public void addLink(Long chatId, String link) {
        scrapperClient.addLink(chatId, new AddLinkRequest(link));
    }

    public void removeLink(Long chatId, String link) {
        scrapperClient.removeLink(chatId, new RemoveLinkRequest(link));
    }

    public Iterable<String> getLink(Long chatId) {
        return scrapperClient.getLinks(chatId).getLinks().stream().map(LinkResponse::getUrl).toList();
    }
}
