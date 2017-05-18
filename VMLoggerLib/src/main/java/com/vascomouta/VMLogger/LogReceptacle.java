package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.utils.BackgroundExecutor;

import java.util.ArrayList;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class LogReceptacle {



    public  void log(LogEntry logEntry) {
        int appendersCount = 0;
        LogConfiguration logger = logEntry.logger;
        boolean synchronous = logger.synchronousMode();

        //print messgae through appender
        System.out.print(logEntry.callingFileLine);

    }

        private boolean logEntry(LogEntry entry, ArrayList<LogFilter> passesFilters ) {
            for(LogFilter filter : passesFilters) {
            /*if (!filter.shouldRecordLogEntry(entry) {
                return false
            }*/
           }
            return true;
        }

    private  void dispatcherForQueue( boolean  synchronous , Thread thread) {
        if(synchronous) {
             BackgroundExecutor.getInstance().execute(thread);
        } else {
            BackgroundExecutor.getInstance().execute(thread);
        }
    }



}
