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
    public void recordFormatterMessage(String message, LogEntry logEntry, Thread thread, boolean sychronousMode) {
        //execute this code in thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(message);
            }
        }).start();

    }

}
