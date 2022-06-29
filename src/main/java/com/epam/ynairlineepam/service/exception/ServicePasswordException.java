package com.epam.ynairlineepam.service.exception;

public class ServicePasswordException extends Exception{
    public ServicePasswordException() {
        super();
    }

    public ServicePasswordException(String message) {
        super(message);
    }

    public ServicePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServicePasswordException(Throwable cause) {
        super(cause);
    }

    protected ServicePasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
