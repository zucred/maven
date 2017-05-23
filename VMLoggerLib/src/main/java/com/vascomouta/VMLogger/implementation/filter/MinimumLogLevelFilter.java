package com.vascomouta.VMLogger.implementation.filter;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogLevel;

/**
 * Created by Sourabh Kapoor on 23/05/17.
 */

public class MinimumLogLevelFilter extends LogLevelFilter{

    public MinimumLogLevelFilter(LogLevel logLevel){
        super(logLevel);
    }

    /**
     Called to determine whether the given `LogEntry` should be recorded.
     :param:     entry The `LogEntry` to be evaluated by the filter.
     :returns:   `true` if `entry.severity` is as or more severe than the
     receiver's `severity` property; `false` otherwise.
     */
    @Override
    public boolean shouldRecordLogEntry(LogEntry logEntry) {
        return LogLevel.getLogLevelValue(logEntry.logLevel) >= LogLevel.getLogLevelValue(severity);

    }
}
