package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFormatter;

import java.util.Map;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class BaseLogFormatter implements LogFormatter {


    @Override
    public void init(Map<String, Object> configuration) {

    }

    @Override
    public String formatLogEntry(LogEntry entry, String message) {
        return null;
    }
}
