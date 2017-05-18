package com.vascomouta.VMLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public abstract class LogAppender {

    public String name;
    public LogFormatter formatters;
    public ArrayList<LogFilter> filters;
    public ThreadPoolExecutor threadPool;

    public abstract void recordFormatterMessage(String message, LogEntry logEntry, ThreadPoolExecutor executor, boolean sychronousMode);

    public abstract LogAppender init(HashMap<String,Object> configuration);





}
