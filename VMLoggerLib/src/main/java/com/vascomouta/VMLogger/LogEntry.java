package com.vascomouta.VMLogger;

import java.util.Date;
import java.util.Map;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class LogEntry {

    public LogConfiguration logger;

    public enum Payload{

    }

    /** The payload of the log entry. */
    public Payload payload;

    /** The level of the log entry. */
    public LogLevel logLevel;

    /** The signature of the function that issued the log request. */
    public String callingFunction;

    /** The line within the source file at which the log request was issued. */
    public int callingFileLine;

    /** The path of the source file containing the calling function that issued
     the log request. */
    public String callingFilePath;

    /** A numeric identifier for the calling thread. Note that thread IDs are
     recycled over time. */
    public int callingThreadID;

    /** The time at which the `LogEntry` was created. */
    public Date timestamp;

    /** Dictionary to store miscellaneous data about the log, can be used by formatters and filters etc. Please prefix any keys to help avoid collissions. */
    public Map<String, Object> userInfo;

    /**
     `LogEntry` initializer.

     :param:     payload The payload of the `LogEntry` being constructed.

     :param:     logLevel The `LogLevel` of the message being logged.

     :param:     callingFunction The signature of the function that issued the
     log request.

     :param:     callingFilePath The path of the source file containing the
     calling function that issued the log request.

     :param:     callingFileLine The line within the source file at which the log
     request was issued.

     :param:     callingThreadID A numeric identifier for the calling thread.
     Note that thread IDs are recycled over time.

     :param:     timestamp The time at which the log entry was created. Defaults
     to the current time if not specified.
     */
    public void init(LogConfiguration logger, Payload payload, LogLevel logLevel, Map<String, Object> userInfo,String callingFunction,
                String callingFilePath,  int callingFileLine, int callingThreadID, Date timestamp) {
        this.logger = logger;
        this.payload = payload;
        this.logLevel = logLevel;
        this.callingFunction = callingFunction;
        this.callingFilePath = callingFilePath;
        this.callingFileLine = callingFileLine;
        this.callingThreadID = callingThreadID;
        this.timestamp = timestamp;
        this.userInfo = userInfo;
    }



}
