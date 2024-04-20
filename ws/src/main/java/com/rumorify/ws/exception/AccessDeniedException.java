package com.rumorify.ws.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rumorify.ws.shared.Messages;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super(Messages.getMessageForLocale("rumorify.access.denied.error.message", LocaleContextHolder.getLocale()));
    }

}
