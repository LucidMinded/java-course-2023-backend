package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.BotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.testutils.TestUtils.getMessageText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @Mock private BotService botService;
    @Mock private Update update;

    @Test
    void handle_registered() {
        // Arrange
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        // Act
        String resultMessageText = getMessageText(new StartCommand(botService).handle(update));

        // Assert
        assertEquals("You have successfully registered!", resultMessageText);
    }

    @Test void handle_notRegistered() {
        // Arrange
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        // Act
        String resultMessageText = getMessageText(new StartCommand(botService).handle(update));

        // Assert
        assertEquals("You have successfully registered!", resultMessageText);
    }

}
