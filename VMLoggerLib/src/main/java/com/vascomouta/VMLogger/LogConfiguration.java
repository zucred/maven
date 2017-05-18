package com.vascomouta.VMLogger;


import java.util.ArrayList;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public interface LogConfiguration {


    String getIdentifier();

    boolean isAdditivity();

    LogLevel getAssignedLogLevel();

    LogLevel getEffectiveLogLevel();

    ArrayList<LogAppender> getAppender();

    boolean synchronousMode();

    LogConfiguration parent();

    LogConfiguration children();

    void addChildren(LogConfiguration childConfiguration, boolean copyGrandChildren);

    ArrayList<LogConfiguration> getChildren();

    String getFullName();

    void setParent(LogConfiguration parent);

    String details();

}
