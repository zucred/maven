package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.implementation.RootLogConfiguration;

import java.util.HashMap;


/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class Log extends RootLogConfiguration {

    private String LoggerInfoFile = "VMLogger-Info";
    private String LoggerConfig  = "LOGGER_CONFIG";
    private String LoggerAppenders = "LOGGER_APPENDERS";
    private String LoggerLevel = "LOGGER_LEVEL";
    private String LoggerSynchronous = "LOGGER_SYNCHRONOUS";
    private String Appenders = "APPENDERS";

    private static RootLogConfiguration mLogInstance;
    private HashMap<String, LogAppender> mAppenders;

    private LogChannel mEventChannel;
    private LogChannel mSevereChannel;
    private LogChannel mErrorChannel;
    private LogChannel mWarningChannel;
    private LogChannel mInfoChannel;
    private LogChannel mDebugChannel;
    private LogChannel mVerboseChannel;

    public static RootLogConfiguration getInstance(){
        if(mLogInstance == null){
           //initialize instance
        }
        return mLogInstance;
    }



}
