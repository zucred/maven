package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.enums.Payload;

import java.util.Date;
import java.util.Map;


public class LogEntry {

    public LogConfiguration logger;

    // The payload of the log entry.
    public Payload payload;

    // The level of the log entry.
    public LogLevel logLevel;

    // The signature of the function that issued the log request.
    public String callingFunction;

    // The line within the source file at which the log request was issued.
    public int callingFileLine;


    // The path of the source file containing the calling function that issued
    // the log request.
    public String callingFilePath;

    // A numeric identifier for the calling thread. Note that thread IDs are
    // recycled over time.
    public long callingThreadID;

    // The time at which the `LogEntry` was created.
    public Date timestamp;

    // Map to store miscellaneous data about the log, can be used by formatters and filters etc. Please prefix any keys to help avoid collissions. */
    public Map<String, Object> userInfo;

    public String message;
    public Object value;

    /**
     * `LogEntry` initializer.
     * @param logger
     * @param payload payload The payload of the `LogEntry` being constructed.
     * @param logLevel logLevel The `LogLevel` of the message being logged.
     * @param userInfo
     * @param callingFilePath callingFilePath The path of the source file containing the calling function that issued the log request.
     * @param callingFunction callingFunction The signature of the function that issued the log request.
     * @param callingFileLine callingFileLine The line within the source file at which the log request was issued.
     * @param callingThreadID callingThreadID A numeric identifier for the calling thread. Note that thread IDs are recycled over time.
     * @param timestamp timestamp The time at which the log entry was created. Defaults to the current time if not specified.
     * @param message
     * @param value
     */
    public LogEntry(LogConfiguration logger, Payload payload, LogLevel logLevel, Map<String, Object> userInfo,
                String callingFilePath,String callingFunction,  int callingFileLine, int callingThreadID, Date timestamp, String message , Object value) {
        this.logger = logger;
        this.payload = payload;
        this.logLevel = logLevel;
        this.callingFunction = callingFunction;
        this.callingFilePath = callingFilePath;
        this.callingFileLine = callingFileLine;
        this.callingThreadID = callingThreadID;
        this.timestamp = timestamp;
        this.userInfo = userInfo;
        this.message = message;
        this.value = value;
    }



}
