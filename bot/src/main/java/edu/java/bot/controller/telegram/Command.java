package edu.java.bot.controller.telegram;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().trim().startsWith(command());
    }

    default String getCommandText(Update update) {
        return update.message().text().trim().substring(command().length()).trim();
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

}
