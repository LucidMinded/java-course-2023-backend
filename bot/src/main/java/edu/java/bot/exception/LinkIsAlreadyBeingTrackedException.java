package edu.java.bot.exception;

public class LinkIsAlreadyBeingTrackedException extends RuntimeException {
    public LinkIsAlreadyBeingTrackedException(String message, Throwable cause) {
        super(message, cause);
    }
}
