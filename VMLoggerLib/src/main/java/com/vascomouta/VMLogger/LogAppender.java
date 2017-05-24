package com.vascomouta.VMLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public abstract class LogAppender {

    public String name;
    public ArrayList<LogFormatter> formatters = new ArrayList<>();
    public ArrayList<LogFilter> filters = new ArrayList<>();
    public Thread dispatchQueue;

    public abstract void recordFormatterMessage(String message, LogEntry logEntry, Thread dispatchQueue , boolean sychronousMode);

    public abstract LogAppender init(HashMap<String,Object> configuration);





}
