package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;

/**
 * Created by Asma on 17/05/17.
 */

public class AppLogger extends Log {



    public AppLogger getLogger(String identifier){
        return  (AppLogger) super.getLogger(identifier, this);
    }



}
