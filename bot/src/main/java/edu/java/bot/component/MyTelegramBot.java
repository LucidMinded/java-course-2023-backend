package edu.java.bot.component;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.controller.Command;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @Slf4j public class MyTelegramBot implements UpdatesListener, ExceptionHandler {
    private final ApplicationConfig applicationConfig;
    private final TelegramBot telegramBotSDK;
    private final UserMessageProcessor userMessageProcessor;

    public MyTelegramBot(
        ApplicationConfig applicationConfig,
        UserMessageProcessor userMessageProcessor
    ) {
        this.telegramBotSDK = new TelegramBot(applicationConfig.telegramToken());
        this.applicationConfig = applicationConfig;
        this.userMessageProcessor = userMessageProcessor;

        telegramBotSDK.setUpdatesListener(this, this);

        List<BotCommand> myBotCommands = new ArrayList<>();

        for (Command command : userMessageProcessor.getCommands()) {
            myBotCommands.add(new BotCommand(command.command(), command.description()));
        }

        telegramBotSDK.execute(new SetMyCommands(myBotCommands.toArray(new BotCommand[0])));
    }

    @Override
    public int process(List<Update> updates) {
        log.info("Received {} updates", updates.size());
        updates.forEach(update -> {
            if (update.message() != null) {
                log.info("Received message: {}", update.message().text());
                telegramBotSDK.execute(userMessageProcessor.process(update));
            } else {
                log.info("Received non-message update");
                // todo: handle other types of updates
            }
        });

        // process updates
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void onException(TelegramException e) {
        if (e.response() != null) {
            // got bad response from telegram
            log.error(
                "TelegramException occurred. Error code: {}. Description: {}",
                e.response().errorCode(),
                e.response().description()
            );
        } else {
            // probably network error
            log.error("TelegramException occurred. No response");
        }
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> R execute(BaseRequest<T, R> request) {
        return telegramBotSDK.execute(request);
    }
}
