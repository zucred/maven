package com.vascomouta.VMLogger;

import java.util.HashMap;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public interface LogFilter {


    /**
     Called to determine whether the given `LogEntry` should be recorded.
     :param:     entry The `LogEntry` to be evaluated by the filter.
     :returns:   `true` if `entry` should be recorded, `false` if not.
     */
     boolean shouldRecordLogEntry(LogEntry logEntry);

    /**
     constructor to be used by introspection

     - parameter configuration: configuration for the filter

     - returns: if configuration is correct a new LogFilter
     */
     LogFilter init(HashMap<String, Object> configuration);

}
