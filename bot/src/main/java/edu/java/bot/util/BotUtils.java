package edu.java.bot.util;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BotUtils {
    public SendMessage createSendMessage(Update update, String text) {
        return new SendMessage(getUserId(update), text);
    }

    public long getUserId(Update update) {
        return update.message().chat().id();
    }
}
