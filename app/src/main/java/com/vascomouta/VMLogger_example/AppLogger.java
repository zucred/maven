package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;


/**
 * Created by Sourabh Kapoor on 17/05/17.
 */

public class AppLogger extends Log {


    private Log log;

    public AppLogger(String identifier){
        super();
        this.log = getLogger(identifier);
    }

    public Log getLogger(String identifier){
        return  super.getLogger(identifier);
    }

    public Log getLogger(){
        return log;
    }

}
