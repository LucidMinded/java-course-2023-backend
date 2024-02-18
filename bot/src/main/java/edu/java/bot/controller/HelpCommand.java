package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.BotUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class HelpCommand implements Command {
    private final List<? extends Command> commands;

    @Override public String command() {
        return "/help";
    }

    @Override public String description() {
        return "List of commands with descriptions";
    }

    @Override public SendMessage handle(Update update) {
        log.info("Handling /help command");
        StringBuilder responseText = new StringBuilder("Available commands:\n");
        for (Command command : commands) {
            responseText.append(command.command()).append(" - ").append(command.description()).append("\n");
        }
        return BotUtils.createSendMessage(update, responseText.toString());
    }
}
