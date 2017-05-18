package com.vascomouta.VMLogger.implementation.appender;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;

import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Asma on 18/05/17.
 */

public class ConsoleLogAppender extends LogAppender {


    public static String CONSOLE_IDENTIFIER = "console";


    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, ThreadPoolExecutor executor, boolean sychronousMode) {

    }

    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        return null;
    }
}
