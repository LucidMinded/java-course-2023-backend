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

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    List<? extends Command> commandsForHelp;
    List<? extends Command> commands;
    @Mock private BotService botService;
    @Mock private Update update;

    void setUp() {
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        mock(HelpCommand.class);
        mock(ListCommand.class);
        mock(StartCommand.class);
        mock(TrackCommand.class);
        mock(UntrackCommand.class);

        commandsForHelp = List.of(
            new ListCommand(botService),
            new StartCommand(botService),
            new TrackCommand(botService),
            new UntrackCommand(botService)
        );
    }

    @Test
    void handle() {
        // Arrange
        setUp();

        // Act
        String resultMessageText = getMessageText(new HelpCommand(commandsForHelp).handle(update));

        // Assert
        assertEquals(
            "Available commands:\n" +
                "/list - List all tracked resources\n" +
                "/start - Register\n" +
                "/track - Start tracking a new resource\n" +
                "/untrack - Stop tracking a resource\n",
            resultMessageText
        );
    }
}
