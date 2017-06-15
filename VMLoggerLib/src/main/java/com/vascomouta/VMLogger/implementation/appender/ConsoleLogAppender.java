package com.vascomouta.VMLogger.implementation.appender;


import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.util.ArrayList;
import java.util.HashMap;


public class ConsoleLogAppender extends BaseLogAppender {

    public static String CONSOLE_IDENTIFIER = "console";


    public ConsoleLogAppender() {
       super(ConsoleLogAppender.CONSOLE_IDENTIFIER, new ArrayList<LogFormatter>(),new ArrayList<LogFilter>());
    }

    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        return super.init(configuration);
    }

    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue thread, boolean synchronousMode) {
        System.out.println(message);
    }

}
