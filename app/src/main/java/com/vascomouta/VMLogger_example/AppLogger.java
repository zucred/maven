package com.vascomouta.VMLogger_example;

import android.os.Handler;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger_example.utils.DateUtil;

import java.util.Date;


public class AppLogger extends Log {

    static String AppLoggerUI = "TrackUI";
    private static String ForegroundDuration = "ForegroundDuration";
    private static String BackgroundDuration = "BackgroundDuration";
    private static String Terminated = "Terminated";

    private static String AppLoggerInfoFile = "AppLogger-Info";

    private static Date startDate = new Date();
    private static Date eventDate = startDate;
    static Handler handler = new Handler();

    public Log getLogger(String identifier){
        return super.getLogger(identifier);
    }


    public static void appMovedToBackground(){
        handler.post(() -> {
            AppLogger.printVerbose("appMovedToBackground");
            Date endDate = new Date();
            long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
            AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, ForegroundDuration, String.valueOf(timeInterval), null));
            eventDate = new Date();
        });
    }

    public static void appMovedToForeground(){
        handler.post(() -> {
            AppLogger.printVerbose("appMovedToForeground");
            Date endDate = new Date();
            long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
            AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, BackgroundDuration, String.valueOf(timeInterval), null));
            eventDate = new Date();
        });

    }

    public static void appTerminate(){
        handler.post(() -> {
            AppLogger.printVerbose("appTerminate");
            Date endDate = new Date();
            long timeInterval = DateUtil.getTimeInterval(eventDate, endDate);
            AppLogger.printEvent(AppLoggerEvent.createEvent(AppLoggerEvent.UI, Terminated, String.valueOf(timeInterval), null));
        });
    }


    public static void appResignActive(){
        handler.post(() -> AppLogger.printVerbose("appResignActive"));
    }

    public static void appBecomeActive(){
        handler.post(() -> AppLogger.printVerbose("appBecomeActive"));

    }

}
