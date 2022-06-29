package com.epam.ynairlineepam.command.exception;

public class CommandInitializationException extends RuntimeException{
    public CommandInitializationException() {
        super();
    }

    public CommandInitializationException(String message) {
        super(message);
    }

    public CommandInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandInitializationException(Throwable cause) {
        super(cause);
    }

    protected CommandInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
