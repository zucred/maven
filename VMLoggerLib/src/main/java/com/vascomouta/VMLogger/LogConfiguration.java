package com.vascomouta.VMLogger;


/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public interface LogConfiguration {


    public void addChildren(LogConfiguration childConfiguration);

    public LogConfiguration getChildren();

    public String getFullName();

}
