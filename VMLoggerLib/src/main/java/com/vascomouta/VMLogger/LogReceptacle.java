package com.vascomouta.VMLogger;

import android.util.*;

import com.vascomouta.VMLogger.implementation.BaseLogFormatter;
import com.vascomouta.VMLogger.implementation.filter.LogLevelFilter;
import com.vascomouta.VMLogger.implementation.filter.MaximumLogLevelFilter;
import com.vascomouta.VMLogger.implementation.filter.MinimumLogLevelFilter;
import com.vascomouta.VMLogger.implementation.formatter.DefaultLogFormatter;
import com.vascomouta.VMLogger.utils.BackgroundExecutor;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class LogReceptacle {



    public  void log(LogEntry logEntry) {
        int appendersCount = 0;
        LogConfiguration logger = logEntry.logger;
        boolean synchronous = logger.synchronousMode;

        //TODO add in queue
        LogConfiguration config;
        do {
            config = logger;
            if ((logEntry.logLevel.getValue() >= config.effectiveLogLevel.getValue()) || (config.effectiveLogLevel.getValue() == LogLevel.OFF.getValue()
                    && config.identifier != logEntry.logger.identifier)) {
                for (LogAppender appender : config.appenders) {
                    ArrayList<LogFilter> filters = new ArrayList<>();
                    filters.add(new MinimumLogLevelFilter(LogLevel.VERBOSE));
                    /*filters.add(new LogLevelFilter(LogLevel.DEBUG));
                    filters.add(new LogLevelFilter(LogLevel.INFO));*/
                    ArrayList<LogFormatter> formatters = new ArrayList<>();
                    formatters.add(new DefaultLogFormatter(true, true, true, true, true, true, true, true, true));

                  //  if (logEntry(logEntry, appender.filters)) {
                    if (logEntry(logEntry, filters)) {
                        //TODO log message into queue
                        String formatted = BaseLogFormatter.stringRepresentationForPayload(logEntry);
                        String formattedMessage = formatted;
                       // for (LogFormatter formatter :  appender.formatters) {
                        for(LogFormatter formatter : formatters){
                             formattedMessage =  formatter.formatLogEntry(logEntry, formatted);
                        }

                        //   for(BaseLogFormatter formatter : appender.formatters){
                        //     formatted.fo
                        appender.recordFormatterMessage(formattedMessage, logEntry, synchronous);
                        appendersCount = appendersCount + 1;
                    }
                }
                logger = config.parent;
            } else if (!config.identifier.equals(logEntry.logger.identifier)) {
                logger = config.parent;
            } else {
                logger = null;
            }

        }while (config.additivity && logger != null) ;
    }


    private boolean logEntry(LogEntry entry, ArrayList<LogFilter> passesFilters ) {
            for(LogFilter filter : passesFilters) {
                if (!filter.shouldRecordLogEntry(entry)) {
                    return false;
                }
           }
            return true;
        }

    private  void dispatcherForQueue( boolean  synchronous , Thread thread) {
        if(synchronous) {
             BackgroundExecutor.getInstance().execute(thread);
        } else {
            BackgroundExecutor.getInstance().execute(thread);
        }
    }



}
