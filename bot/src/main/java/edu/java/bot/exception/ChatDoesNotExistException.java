package edu.java.bot.exception;

public class ChatDoesNotExistException extends RuntimeException {
    public ChatDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
