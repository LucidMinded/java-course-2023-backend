package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.component.MyTelegramBot;
import edu.java.dto.bot.request.LinkUpdateRequest;
import edu.java.dto.bot.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final MyTelegramBot myTelegramBot;

    @Operation(summary = "Отправить обновление", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping("/updates")
    public void updates(@Valid @RequestBody LinkUpdateRequest linkUpdateRequest) {
        String description = linkUpdateRequest.getDescription();
        List<Long> chatIds = linkUpdateRequest.getTgChatIds();
        String url = linkUpdateRequest.getUrl();

        for (Long chatId : chatIds) {
            // send message to chat with chatId
            myTelegramBot.execute(new SendMessage(chatId, description + " " + url));
        }
    }
}
