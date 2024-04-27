package edu.java.bot.component;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.controller.Command;
import edu.java.bot.controller.HelpCommand;
import edu.java.bot.controller.ListCommand;
import edu.java.bot.controller.StartCommand;
import edu.java.bot.controller.TrackCommand;
import edu.java.bot.controller.UntrackCommand;
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
class UserMessageProcessorImplTest {
    List<? extends Command> commandsForHelp;
    List<? extends Command> commands;
    @Mock private BotService botService;
    @Mock private Update update;

    private UserMessageProcessorImpl userMessageProcessor;

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

        commands = List.of(
            new HelpCommand(commandsForHelp),
            new ListCommand(botService),
            new StartCommand(botService),
            new TrackCommand(botService),
            new UntrackCommand(botService)
        );

        userMessageProcessor = new UserMessageProcessorImpl(commands, botService);
    }

    @Test
    void process_registered_unknown() {
        // Arrange
        setUp();
        when(update.message().text()).thenReturn("/unknown");
        when(botService.isChatRegistered(1L)).thenReturn(true);

        // Act
        String resultMessageText = getMessageText(userMessageProcessor.process(update));

        // Assert
        assertEquals("Unknown command", resultMessageText);
    }

    @Test
    void process_unregistered_start() {
        // Arrange
        setUp();
        when(update.message().text()).thenReturn("/start");
        when(botService.isChatRegistered(1L)).thenReturn(false);

        // Act
        String resultMessageText = getMessageText(userMessageProcessor.process(update));

        // Assert
        assertEquals("You have successfully registered!", resultMessageText);
    }

    @Test
    void process_unregistered_other() {
        // Arrange
        setUp();
        when(update.message().text()).thenReturn("/track");
        when(botService.isChatRegistered(1L)).thenReturn(false);

        // Act
        String resultMessageText = getMessageText(userMessageProcessor.process(update));

        // Assert
        assertEquals("You are not registered. Please, use /start command to register", resultMessageText);
    }

}
