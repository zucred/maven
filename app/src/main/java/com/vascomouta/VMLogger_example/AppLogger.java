package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogConfiguration;


/**
 * Created by Sourabh Kapoor on 17/05/17.
 */

public class AppLogger extends Log {


    public AppLogger getLogger(String identifier){
         super.getLogger(identifier);
        return this;
    }

}
