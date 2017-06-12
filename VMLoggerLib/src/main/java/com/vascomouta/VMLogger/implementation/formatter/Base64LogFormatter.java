package com.vascomouta.VMLogger.implementation.formatter;

import android.util.Base64;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.implementation.BaseLogFormatter;

import java.util.Map;

public class Base64LogFormatter extends BaseLogFormatter {


    @Override
    public void init(Map<String, Object> configuration) {
        super.init(configuration);
    }

    @Override
    public String formatLogEntry(LogEntry logEntry, String message) {
        String encodedString = Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
           if (encodedString != null) {
                return encodedString;
            }
        return message;
    }
}
