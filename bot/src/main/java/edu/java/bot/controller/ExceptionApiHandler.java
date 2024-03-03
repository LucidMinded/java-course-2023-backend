package edu.java.bot.controller;

import edu.java.bot.exception.AlreadyRegisteredException;
import edu.java.bot.exception.ChatDoesNotExistException;
import edu.java.bot.exception.LinkIsAlreadyBeingTrackedException;
import edu.java.dto.bot.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(AlreadyRegisteredException.class)
    public ApiErrorResponse handleAlreadyRegisteredException(AlreadyRegisteredException e) {
        return new ApiErrorResponse("Already registered", HttpStatus.CONFLICT.toString(), e);
    }

    @ExceptionHandler(ChatDoesNotExistException.class)
    public ApiErrorResponse handleChatDoesNotExistException(ChatDoesNotExistException e) {
        return new ApiErrorResponse("Chat does not exist", HttpStatus.NOT_FOUND.toString(), e);
    }

    @ExceptionHandler(LinkIsAlreadyBeingTrackedException.class)
    public ApiErrorResponse handleLinkIsAlreadyBeingTrackedException(LinkIsAlreadyBeingTrackedException e) {
        return new ApiErrorResponse("Link is already being tracked", HttpStatus.CONFLICT.toString(), e);
    }

}
