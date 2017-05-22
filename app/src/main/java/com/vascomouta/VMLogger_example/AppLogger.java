package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;


/**
 * Created by Sourabh Kapoor on 17/05/17.
 */

public class AppLogger extends Log {

    Log logger;

     public  AppLogger(){

     }

    public AppLogger(Log log){
       this.logger = log;
    }

    public AppLogger getLogger(String identifier){
        return new AppLogger(super.getLogger(identifier, this));
    }



}
