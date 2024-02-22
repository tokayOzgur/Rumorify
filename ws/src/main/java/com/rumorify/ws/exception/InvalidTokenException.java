package com.rumorify.ws.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.rumorify.ws.shared.Messages;

/**
 * @author: tokay
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super(Messages.getMessageForLocale("rumorify.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
}
