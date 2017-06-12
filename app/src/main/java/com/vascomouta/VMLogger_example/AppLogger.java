package com.vascomouta.VMLogger_example;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogConfiguration;
import com.vascomouta.VMLogger_example.utils.DateUtil;

import java.util.Date;


public class AppLogger extends Log {

    static String AppLoggerUI = "TrackUI";
    private static String ForgroundDuration = "ForgroundDuration";
    private static String BackgroundDuration = "BackgroundDuration";
    private static String Teriminated = "Terminated";

    private static String AppLoggerInfoFile = "AppLogger-Info";

    private static Date startDate = new Date();
    private static Date eventDate = startDate;



    public Log getLogger(String identifier){
        return super.getLogger(identifier);
    }


    public void onCreate(){
        AppLogger.printVerbose("onCreate");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onStart(){
       AppLogger.printVerbose("onStart");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onResume(){
        AppLogger.printVerbose("onResume");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onPause(){
        AppLogger.printVerbose("onPause");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onStop(){
        AppLogger.printVerbose("onStop");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onRestart(){
        AppLogger.printVerbose("onRestart");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }

    public void onDestroy(){
        AppLogger.printVerbose("onDestroy");
        Date endDate = new Date();
        long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
        AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForgroundDuration, String.valueOf(timeInterval), null));
        eventDate = new Date();
    }


}
