package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.component.TrackedResource;
import edu.java.bot.component.URLParser;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class TrackCommand implements Command {
    private final Service service;
    private final URLParser urlParser;

    @Override public String command() {
        return "/track";
    }

    @Override public String description() {
        return "Start tracking a new resource";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /track command");
        String url = getCommandText(update);

        URLParser.ParsedURL parsedURL = urlParser.parse(url);
        if (parsedURL == null || parsedURL.host() == null) {
            return BotUtils.createSendMessage(update, "Invalid URL");
        }

        boolean added;

        String urlHost = parsedURL.host();
        if (urlHost.equals(TrackedResource.GITHUB.getHost())) {
            added = service.addResource(BotUtils.getUserId(update), url); // for now
            // handle GitHub resource
        } else if (urlHost.equals(TrackedResource.STACKOVERFLOW.getHost())) {
            added = service.addResource(BotUtils.getUserId(update), url); // for now
            // handle StackOverflow resource
        } else {
            return BotUtils.createSendMessage(update, "Unsupported resource");
        }

        if (added) {
            return BotUtils.createSendMessage(update, "Resource is now being tracked");
        } else {
            return BotUtils.createSendMessage(update, "Resource was already being tracked");
        }
    }
}
