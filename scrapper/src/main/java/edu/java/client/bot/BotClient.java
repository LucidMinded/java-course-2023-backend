package edu.java.client.bot;

import edu.java.dto.bot.request.LinkUpdateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {
    @PostExchange("/updates")
    void updates(@RequestBody LinkUpdateRequest linkUpdateRequest);
}
