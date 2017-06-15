package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.implementation.BaseLogFormatter;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.util.ArrayList;

public class LogReceptacle {


    private DispatchQueue mQueue;

    /**
     * This implementation log message
     * @param logEntry
     */
    public  void log(LogEntry logEntry) {
        boolean synchronous = logEntry.logger.synchronousMode;
        dispatcherForQueue(getQueue(), synchronous, new Runnable() {
            @Override
            public void run() {
                LogConfiguration logger = logEntry.logger;
                LogConfiguration config;
                do {
                    config = logger;
                    if ((logEntry.logLevel.getValue() >= config.effectiveLogLevel.getValue()) || (config.effectiveLogLevel.getValue() == LogLevel.OFF.getValue()
                            && !config.identifier.equals(logEntry.logger.identifier))) {
                        appliedAppender(config.appenders, logEntry, synchronous);
                        logger = config.parent;
                    } else if (!config.identifier.equals(logEntry.logger.identifier)) {
                        logger = config.parent;
                    } else {
                        logger = null;
                    }

                } while (config.additivity && logger != null);
            }
        });

    }

    /**
     * This implementation applied Appender to log message.
     * @param appenders
     * @param logEntry
     * @param synchronous
     */
    private void appliedAppender(ArrayList<LogAppender> appenders, LogEntry logEntry, boolean synchronous){
        for (LogAppender appender : appenders) {
            if (logEntry(logEntry, appender.filters)) {
                dispatcherForQueue(appender.dispatchQueue, synchronous, new Runnable() {
                    @Override
                    public void run() {
                        appliedFormatter(logEntry, appender, synchronous);
                    }
                });
            }
        }
    }

    /**
     * This implementation applied formatter to log and print message via appender
     * @param logEntry
     * @param appender
     * @param synchronous
     */
    private void appliedFormatter(LogEntry logEntry , LogAppender appender, boolean synchronous){
        logEntry.callingThreadID = Thread.currentThread().getId();
        String formatted = BaseLogFormatter.stringRepresentationForPayload(logEntry);
        String formattedMessage = formatted;
        for (LogFormatter formatter : appender.formatters) {
            formattedMessage = formatter.formatLogEntry(logEntry, formatted);
            appender.recordFormatterMessage(formattedMessage, logEntry, appender.dispatchQueue, synchronous);
        }
    }


    /**
     * This method return queue instance , if null then create new instance of Queue.
     * @return
     */
    private DispatchQueue getQueue(){
        if(mQueue == null){
            mQueue = new DispatchQueue();
        }
        return mQueue;
    }


    /**
     * This implementation return if log should be print according to applied filter
     * @param entry
     * @param passesFilters
     * @return
     */
    private boolean logEntry(LogEntry entry, ArrayList<LogFilter> passesFilters ) {
        if(passesFilters == null){
            return true;
        }
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
