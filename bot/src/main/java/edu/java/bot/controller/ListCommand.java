package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.BotService;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class ListCommand implements Command {
    private final BotService botService;

    @Override public String command() {
        return "/list";
    }

    @Override public String description() {
        return "List all tracked resources";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /list command");
        try {
            Iterable<String> trackedResources = botService.getLink(BotUtils.getUserId(update));
            if (!trackedResources.iterator().hasNext()) {
                return BotUtils.createSendMessage(update, "You are not tracking any links");
            }
            StringBuilder responseText = new StringBuilder("You are tracking the following links:\n");
            for (String resource : trackedResources) {
                responseText.append(resource).append("\n\n");
            }
            return BotUtils.createSendMessage(update, responseText.toString());
        } catch (Exception e) {
            return BotUtils.createSendMessage(update, "Exception occurred while trying to list tracked links");
        }
    }
}
