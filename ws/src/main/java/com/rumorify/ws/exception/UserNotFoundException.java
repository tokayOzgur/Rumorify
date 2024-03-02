package com.rumorify.ws.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.rumorify.ws.shared.Messages;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

/**
 * @author: tokay
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super(Messages.getMessageForLocale("rumorify.user.notfound.error.message",
                LocaleContextHolder.getLocale(), id));
    }

    public UserNotFoundException(String username) {
        super(Messages.getMessageForLocale("rumorify.user.notfound.error.message",
                LocaleContextHolder.getLocale(), username));
    }
}
