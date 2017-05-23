package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.enums.Payload;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class LogChannel {

    /** The `LogSeverity` of this `LogChannel`, which determines the severity
     of the `LogEntry` instances it creates. */
    public LogLevel severity;

    /** The `LogReceptacle` into which this `LogChannel` will deposit
     the `LogEntry` instances it creates. */
    public LogReceptacle receptacle;


    /***
     Initializes a new `LogChannel` instance using the specified parameters.
     :param:     severity The `LogSeverity` to use for log entries written to the
     receiving channel.
     :param:     receptacle A `LogFormatter` instance to use for formatting log
     entries.
     */

    /**
     *
     */
    public LogChannel(LogLevel severity ,LogReceptacle receptacle )
    {
        this.severity = severity;
        this.receptacle = receptacle;
    }


    public void trace(LogConfiguration logger, String fileName, String methodName, int lineNumber) {
        int threadID = 0;
        //pthread_threadid_np(nil, &threadID)
        LogEntry logEntry = new LogEntry(logger, Payload.TRACE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), "", null);
        receptacle.log(logEntry);
    }

    public void message(LogConfiguration logger, String message, String fileName, String methodName, int lineNumber) {
        int threadID = 0;
        //pthread_threadid_np(nil, &threadID)
        LogEntry logEntry = new LogEntry(logger, Payload.MESSAGE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), message, null);
        receptacle.log(logEntry);
    }

    public void value(LogConfiguration logger, Object value, String fileName, String methodName, int lineNumber)
    {
        int threadID = 0;
        //pthread_threadid_np(nil, &threadID)
        LogEntry logEntry = new LogEntry(logger, Payload.VALUE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), "", value);
        receptacle.log(logEntry);
    }


}
