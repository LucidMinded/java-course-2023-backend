package edu.java.bot.exception;

public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
