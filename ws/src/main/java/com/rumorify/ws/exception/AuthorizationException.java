package com.rumorify.ws.exception;

public class AuthorizationException extends RuntimeException {
    // TODO 1: set message to "Authorization failed"
    public AuthorizationException(String message) {
        super(message);
    }
}
