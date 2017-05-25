package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.implementation.BaseLogFormatter;
import com.vascomouta.VMLogger.implementation.filter.MinimumLogLevelFilter;
import com.vascomouta.VMLogger.implementation.formatter.Base64LogFormatter;
import com.vascomouta.VMLogger.implementation.formatter.DefaultLogFormatter;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.util.ArrayList;


public class LogReceptacle {


    private DispatchQueue mQueue;

    public  void log(LogEntry logEntry) {
        boolean synchronous = logEntry.logger.synchronousMode;
        dispatcherForQueue(getQueue(), synchronous, new Runnable() {
            @Override
            public void run() {
                int appenderCount = 0;
                LogConfiguration logger = logEntry.logger;
                LogConfiguration config;
                do {
                    config = logger;
                    if ((logEntry.logLevel.getValue() >= config.effectiveLogLevel.getValue()) || (config.effectiveLogLevel.getValue() == LogLevel.OFF.getValue()
                            && !config.identifier.equals(logEntry.logger.identifier))) {
                        for (LogAppender appender : config.appenders) {
                            ArrayList<LogFilter> filters = new ArrayList<>();
                            filters.add(new MinimumLogLevelFilter(LogLevel.VERBOSE));
                            ArrayList<LogFormatter> formatters = new ArrayList<>();
                            formatters.add(new DefaultLogFormatter(true, true, true, true, true, true, true, true, true));
                            formatters.add(new Base64LogFormatter());
                             /* ArrayList<String> types = new ArrayList<>();
                                types.add("Map");
                                filters.add(new ValueTypeFilter(types));

                                Set<String> fileSet = new HashSet<>();
                                fileSet.add("MainActivity.java");
                                filters.add(new FileNameFilter(fileSet, false, false));
                                filters.add(new LogLevelFilter(LogLevel.DEBUG));
                                filters.add(new LogLevelFilter(LogLevel.INFO));*/
                                //TODO need to call dynamic filter
                            //  if (logEntry(logEntry, appender.filters)) {
                                if (logEntry(logEntry, filters)) {
                                    //TODO log message into queue
                                    //TODO need to call dynamic formatter
                                    String formatted = BaseLogFormatter.stringRepresentationForPayload(logEntry);
                                    String formattedMessage = formatted;
                                    // for (LogFormatter formatter :  appender.formatters) {
                                    for(LogFormatter formatter : formatters){
                                        formattedMessage =  formatter.formatLogEntry(logEntry, formatted);
                                        appender.recordFormatterMessage(formattedMessage, logEntry, appender.dispatchQueue, synchronous);
                                    }

                                    //   for(BaseLogFormatter formatter : appender.formatters){
                                    //     formatted.fo
                                    //   appender.recordFormatterMessage(formattedMessage, logEntry, synchronous);
                                    appenderCount++;
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
        });

    }

    private DispatchQueue getQueue(){
        if(mQueue == null){
            mQueue = new DispatchQueue();
        }
        return mQueue;
    }


    private boolean logEntry(LogEntry entry, ArrayList<LogFilter> passesFilters ) {
            for(LogFilter filter : passesFilters) {
                if (!filter.shouldRecordLogEntry(entry)) {
                    return false;
                }
           }
            return true;
        }

    private  void dispatcherForQueue(DispatchQueue dispatchQueue,  boolean  synchronous , Runnable thread) {
            if (synchronous) {
                dispatchQueue.sync(thread);
            } else {
                dispatchQueue.async(thread);
            }
    }



}
