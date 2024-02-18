package edu.java.bot.component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.Command;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor @Slf4j public class UserMessageProcessorImpl implements UserMessageProcessor {
    @Getter private final List<? extends Command> commands;
    private final Service service;

    public SendMessage process(Update update) {
        if (!service.isUserRegistered(BotUtils.getUserId(update))
            && !update.message().text().trim().startsWith("/start")) {
            log.info("User is not registered. Ignoring the message.");
            return BotUtils.createSendMessage(update, "You are not registered. Please, use /start command to register");
        }

        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        log.info("Encountered unknown command");
        return BotUtils.createSendMessage(update, "Unknown command");
    }
}
