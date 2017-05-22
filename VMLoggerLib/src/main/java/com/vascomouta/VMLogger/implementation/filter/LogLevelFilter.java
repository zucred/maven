package com.vascomouta.VMLogger.implementation.filter;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogLevel;
import com.vascomouta.VMLogger.constant.LogLevelFilterConstant;

import java.util.HashMap;

/**
 * Created by Sourabh Kapoor on 22/05/17.
 */

public class LogLevelFilter implements LogFilter {


    /** Returns the `LogSeverity` associated with the receiver. */
    public LogLevel severity;

    /**
     Initializes a new `LogSeverityFilter` instance.
     :param:     severity Specifies the `LogSeverity` that the filter will
     use to determine whether a given `LogEntry` should be
     recorded. Only those log entries with a severity equal to
     or more severe than this value will pass through the filter.
     */
    public LogLevelFilter(LogLevel severity)
    {
        this.severity = severity;
    }


    @Override
    public LogLevelFilter init(HashMap<String, Object> configuration) {
        String level = (String)configuration.get(LogLevelFilterConstant.Level);
        if(level != null){
           return new LogLevelFilter(LogLevel.getLogLevel(level));
        }
            return null;
    }

    @Override
    public boolean shouldRecordLogEntry(LogEntry logEntry) {
        return logEntry.logLevel == severity;
    }

}
