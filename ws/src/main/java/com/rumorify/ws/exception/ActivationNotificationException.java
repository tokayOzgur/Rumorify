package com.rumorify.ws.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.rumorify.ws.shared.Messages;

public class ActivationNotificationException extends RuntimeException {
    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("rumorify.create.user.email.failure", LocaleContextHolder.getLocale()));
    }

}
