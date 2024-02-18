package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.component.URLParser;
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
public class StartCommandTest {
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
    void handle_registered() {
        when(service.isUserRegistered(1L)).thenReturn(true);
        assertEquals("Nice to see you again!", getMessageText(new StartCommand(service).handle(update)));
    }

    @Test void handle_notRegistered() {
        when(service.isUserRegistered(1L)).thenReturn(false);
        assertEquals("You have successfully registered!", getMessageText(new StartCommand(service).handle(update)));
    }

}
