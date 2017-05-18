package com.vascomouta.VMLogger;


import java.util.ArrayList;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public abstract class LogConfiguration {


    public String identifier;

    public boolean additivity;

    public LogLevel assignedLogLevel;

    public LogLevel effectiveLogLevel;

    public ArrayList<LogAppender> appenders;

    public boolean synchronousMode;

    public LogConfiguration parent;

    public ArrayList<LogConfiguration> children;

    public abstract void addChildren(LogConfiguration childConfiguration, boolean copyGrandChildren);

    public abstract LogConfiguration getChildren(String name);

    public abstract String fullName();

    public abstract void setParent(LogConfiguration parent);

    public abstract String details();

}
