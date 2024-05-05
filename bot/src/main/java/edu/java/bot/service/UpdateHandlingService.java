package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.component.MyTelegramBot;
import edu.java.dto.bot.request.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHandlingService {
    private final MyTelegramBot myTelegramBot;
    private final BotDeadLetterQueueProducer botDeadLetterQueueProducer;

    public void handleLinkUpdate(LinkUpdateRequest linkUpdateRequest) {
        try {
            String description = linkUpdateRequest.getDescription();
            List<Long> chatIds = linkUpdateRequest.getTgChatIds();
            String url = linkUpdateRequest.getUrl();

            for (Long chatId : chatIds) {
                // send message to chat with chatId
                myTelegramBot.execute(new SendMessage(
                    chatId,
                    "There is an update at " + url + "\n\nDetails:\n" + description
                ));
            }
        } catch (Exception e) {
            botDeadLetterQueueProducer.send(linkUpdateRequest);
        }
    }
}
