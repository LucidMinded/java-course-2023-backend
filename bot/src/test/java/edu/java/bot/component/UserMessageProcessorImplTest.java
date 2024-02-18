package edu.java.bot.component;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.Command;
import edu.java.bot.controller.HelpCommand;
import edu.java.bot.controller.ListCommand;
import edu.java.bot.controller.StartCommand;
import edu.java.bot.controller.TrackCommand;
import edu.java.bot.controller.UntrackCommand;
import edu.java.bot.service.Service;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
    @Mock private Service service;
    @Mock private Update update;

    @BeforeEach
    void setUp() {
        when(update.message()).thenReturn(mock(Message.class));
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(1L);

        mock(HelpCommand.class);
        mock(ListCommand.class);
        mock(StartCommand.class);
        mock(TrackCommand.class);
        mock(UntrackCommand.class);

        URLParser urlParser = new URLParser();

        commandsForHelp = List.of(
            new ListCommand(service),
            new StartCommand(service),
            new TrackCommand(service, urlParser),
            new UntrackCommand(service, urlParser)
        );

        commands = List.of(
            new HelpCommand(commandsForHelp),
            new ListCommand(service),
            new StartCommand(service),
            new TrackCommand(service, urlParser),
            new UntrackCommand(service, urlParser)
        );

    }

    @Test
    void process_registered_unknown() {
        when(update.message().text()).thenReturn("/unknown");
        when(service.isUserRegistered(1L)).thenReturn(true);
        UserMessageProcessorImpl userMessageProcessor = new UserMessageProcessorImpl(commands, service);
        SendMessage result = userMessageProcessor.process(update);

        assertEquals("Unknown command", getMessageText(result));
    }

    @Test
    void process_unregistered_start() {
        when(update.message().text()).thenReturn("/start");
        when(service.isUserRegistered(1L)).thenReturn(false);
        UserMessageProcessorImpl userMessageProcessor = new UserMessageProcessorImpl(commands, service);
        SendMessage result = userMessageProcessor.process(update);

        assertEquals("You have successfully registered!", getMessageText(result));
    }

    @Test
    void process_unregistered_other() {
        when(update.message().text()).thenReturn("/track");
        when(service.isUserRegistered(1L)).thenReturn(false);
        UserMessageProcessorImpl userMessageProcessor = new UserMessageProcessorImpl(commands, service);
        SendMessage result = userMessageProcessor.process(update);

        assertEquals("You are not registered. Please, use /start command to register", getMessageText(result));
    }

}
