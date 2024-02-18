package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.component.URLParser;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class UntrackCommand implements Command {
    private final Service service;
    private final URLParser urlParser;

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
        URLParser.ParsedURL parsedURL = urlParser.parse(url);
        if (parsedURL == null) {
            return BotUtils.createSendMessage(update, "Invalid URL");
        }
        boolean removed;
        switch (parsedURL.host()) {
            case "github.com":
                removed = service.removeResource(userId, url); // for now
                // handle GitHub resource
                break;
            case "stackoverflow.com":
                removed = service.removeResource(userId, url); // for now
                // handle StackOverflow resource
                break;
            default:
                return BotUtils.createSendMessage(update, "Unsupported resource");
        }
        if (removed) {
            return BotUtils.createSendMessage(update, "Resource is no longer being tracked");
        } else {
            return BotUtils.createSendMessage(update, "Resource was not being tracked");
        }
    }
}
