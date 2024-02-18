package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.Service;
import edu.java.bot.util.BotUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.testutils.TestUtils.getMessageText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) public class ListCommandTest {

    @Mock private Service service;
    @Mock private Update update;

    @Test public void handle_formatting_twoLinks() {
        List<String> resources = List.of("https://github.com/", "https://stackoverflow.com/");

        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        when(service.getResources(1L)).thenReturn(resources);

        SendMessage expectedSendMessage = BotUtils.createSendMessage(
            update,
            "You are tracking the following resources:\n" + resources.get(0) + "\n" +
                resources.get(1) + "\n"
        );
        assertEquals(
            getMessageText(expectedSendMessage),
            getMessageText(new ListCommand(service).handle(update))
        );

    }

    @Test public void handle_noTrackedResources() {
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        when(service.getResources(1L)).thenReturn(List.of());

        SendMessage expectedSendMessage = BotUtils.createSendMessage(
            update,
            "You are not tracking any resources"
        );
        assertEquals(
            getMessageText(expectedSendMessage),
            getMessageText(new ListCommand(service).handle(update))
        );
    }
}
