package com.epam.ynairlineepam.service.exception;

public class ServiceLoginException extends Exception{
    public ServiceLoginException() {
        super();
    }

    public ServiceLoginException(String message) {
        super(message);
    }

    public ServiceLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceLoginException(Throwable cause) {
        super(cause);
    }

    protected ServiceLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
