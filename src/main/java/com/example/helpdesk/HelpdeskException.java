package com.example.helpdesk;

@SuppressWarnings("serial")
public class HelpdeskException extends RuntimeException {

    public HelpdeskException() { }

    public HelpdeskException(String message) {
        super(message);
    }

    public HelpdeskException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelpdeskException(Throwable cause) {
        super(cause);
    }

}
