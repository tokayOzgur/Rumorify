package com.rumorify.ws.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.rumorify.ws.shared.Messages;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

/**
 * @author: tokay
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super(Messages.getMessageForLocale("rumorify.resource.notfound.error.message", LocaleContextHolder.getLocale()));
    }
}
