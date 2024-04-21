package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.URLParser;
import edu.java.bot.service.BotService;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class TrackCommand implements Command {
    private final BotService botService;

    @Override public String command() {
        return "/track";
    }

    @Override public String description() {
        return "Start tracking a new resource";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /track command");
        String url = getCommandText(update);

        URLParser.ParsedURL parsedURL = URLParser.parse(url);
        if (parsedURL == null || parsedURL.host() == null) {
            return BotUtils.createSendMessage(update, "Invalid URL");
        }

        try{
            botService.addLink(BotUtils.getUserId(update), url);
        } catch (Exception e){
            return BotUtils.createSendMessage(update, "Exception occurred while trying to add link");
        }
        return BotUtils.createSendMessage(update, "Resource is now being tracked");
    }
}
