package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogConfiguration;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public class BaseLogConfiguration implements LogConfiguration {


    @Override
    public void addChildren(LogConfiguration childConfiguration) {

    }

    @Override
    public LogConfiguration getChildren() {
        return null;
    }

    @Override
    public String getFullName() {
        return null;
    }


}
