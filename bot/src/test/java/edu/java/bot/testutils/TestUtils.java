package edu.java.bot.testutils;

import com.pengrad.telegrambot.request.SendMessage;

public class TestUtils {
    public static String getMessageText(SendMessage sendMessage) {
        return (String) sendMessage.getParameters().get("text");
    }
}
