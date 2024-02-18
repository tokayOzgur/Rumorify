package com.rumorify.ws.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import com.rumorify.ws.shared.Messages;

/**
 * @author: tokay
 */
public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("rumorify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String, String> getValidationErrors() {
        return Collections.singletonMap("email", Messages.getMessageForLocale("rumorify.constraints.email.notunique",
                LocaleContextHolder.getLocale()));
    }
}
