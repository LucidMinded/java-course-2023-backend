package edu.java.bot.component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.Command;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> getCommands();

    SendMessage process(Update update);
}
