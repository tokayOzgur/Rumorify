package com.rumorify.ws.shared;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    public static String getMessageForLocale(String key, String locale) {
        return ResourceBundle.getBundle("messages", new Locale(locale)).getString(key);
    }
}
