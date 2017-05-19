package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogConfiguration;

import java.util.logging.Logger;

/**
 * Created by Asma on 17/05/17.
 */

public class AppLogger extends Log {

    LogConfiguration logger;

     public  AppLogger(){

     }

    public AppLogger(LogConfiguration log){
       this.logger = log;
    }

    public AppLogger getLogger(String identifier){
        return new AppLogger(super.getLogger(identifier, this));
    }



}
