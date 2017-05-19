package com.vascomouta.VMLogger.implementation.appender;


import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;


/**
 * Created by Sourabh kapoor on 18/05/17.
 */

public class ConsoleLogAppender extends BaseLogAppender {


    public static String CONSOLE_IDENTIFIER = "console";


    public ConsoleLogAppender() {
       // self.init(name: ConsoleLogAppender.CONSOLE_IDENTIFIER)
    }


    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, boolean sychronousMode) {
        System.out.println(message);
    }

}
