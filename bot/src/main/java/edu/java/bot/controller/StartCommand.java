package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class StartCommand implements Command {
    private Service service;

    @Override public String command() {
        return "/start";
    }

    @Override public String description() {
        return "Register";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /start command");
        String responseText;
        Long userId = BotUtils.getUserId(update);
        if (service.isUserRegistered(userId)) {
            responseText = "Nice to see you again!";
        } else {
            service.registerUser(userId);
            responseText = "You have successfully registered!";
        }
        return BotUtils.createSendMessage(update, responseText);
    }
}
