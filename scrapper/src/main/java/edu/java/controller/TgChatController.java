package edu.java.controller;

import edu.java.dto.scrapper.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/tg-chat")
public class TgChatController {
    @Operation(summary = "Зарегистрировать чат", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping("{id}")
    public void addChat(@PathVariable("id") Long id) {
        // add chat
    }

    @Operation(summary = "Удалить чат", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),

        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping("{id}")
    public void removeChat(@PathVariable("id") Long id) {
        // remove chat
    }
}
