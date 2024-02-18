package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class ListCommand implements Command {
    private final Service service;

    @Override public String command() {
        return "/list";
    }

    @Override public String description() {
        return "List all tracked resources";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /list command");
        Iterable<String> trackedResources = service.getResources(BotUtils.getUserId(update));
        if (!trackedResources.iterator().hasNext()) {
            return BotUtils.createSendMessage(update, "You are not tracking any resources");
        }
        StringBuilder responseText = new StringBuilder("You are tracking the following resources:\n");
        for (String resource : trackedResources) {
            responseText.append(resource).append("\n");
        }
        return BotUtils.createSendMessage(update, responseText.toString());
    }
}
