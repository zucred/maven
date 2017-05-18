package com.vascomouta.VMLogger;

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
        LogEntry logEntry = new LogEntry(logger, LogEntry.Payload.TRACE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), "", null);
        receptacle.log(logEntry);
    }

    public void message(LogConfiguration logger, String message, String fileName, String methodName, int lineNumber) {
        int threadID = 0;
        //pthread_threadid_np(nil, &threadID)
        LogEntry logEntry = new LogEntry(logger, LogEntry.Payload.TRACE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), message, null);
        receptacle.log(logEntry);
    }

    public void value(LogConfiguration logger, String value, String fileName, String methodName, int lineNumber)
    {
        int threadID = 0;
        //pthread_threadid_np(nil, &threadID)
        LogEntry logEntry = new LogEntry(logger, LogEntry.Payload.TRACE , severity,new HashMap<>(), fileName, methodName, lineNumber, threadID, new Date(), "", value);
        receptacle.log(logEntry);
    }


}
