package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.BotService;
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

    @Mock private BotService botService;
    @Mock private Update update;

    @Test
    public void handle_formatting_twoLinks() {
        // Arrange
        List<String> resources = List.of("https://github.com/", "https://stackoverflow.com/");
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);
        when(botService.getLink(1L)).thenReturn(resources);

        // Act
        String resultMessageText = getMessageText(new ListCommand(botService).handle(update));

        // Assert
        assertEquals(
            "You are tracking the following links:\n" + resources.get(0) + "\n\n" +
                resources.get(1) + "\n\n",
            resultMessageText
        );
    }

    @Test public void handle_noTrackedResources() {
        // Arrange
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);
        when(botService.getLink(1L)).thenReturn(List.of());

        // Act
        String resultMessageText = getMessageText(new ListCommand(botService).handle(update));

        // Assert
        assertEquals(
            "You are not tracking any links",
            resultMessageText
        );
    }
}
