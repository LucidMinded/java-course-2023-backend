package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.URLParser;
import edu.java.bot.service.BotService;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class UntrackCommand implements Command {
    private final BotService botService;

    @Override public String command() {
        return "/untrack";
    }

    @Override public String description() {
        return "Stop tracking a resource";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /untrack command");
        Long userId = update.message().chat().id();
        String url = getCommandText(update);

        URLParser.ParsedURL parsedURL = URLParser.parse(url);
        if (parsedURL == null || parsedURL.host() == null) {
            return BotUtils.createSendMessage(update, "Invalid URL");
        }

        try {
            botService.removeLink(userId, url);
        } catch (Exception e) {
            return BotUtils.createSendMessage(update, "Exception occurred while trying to remove link");
        }
        return BotUtils.createSendMessage(update, "Resource is no longer being tracked");
    }
}
