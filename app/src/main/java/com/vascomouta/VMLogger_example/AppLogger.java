package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogConfiguration;


public class AppLogger extends Log {



    public Log getLogger(String identifier){
        return super.getLogger(identifier);
    }


}
