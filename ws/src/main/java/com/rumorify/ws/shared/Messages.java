package com.rumorify.ws.shared;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    public static String getMessageForLocale(String key, Locale locale) {
        return ResourceBundle.getBundle("messages", locale).getString(key);
    }
}
