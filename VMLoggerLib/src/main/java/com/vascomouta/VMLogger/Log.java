package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.implementation.RootLogConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private static HashMap<String, LogAppender> mAppenders;

    private static LogChannel mEventChannel;
    private static LogChannel mSevereChannel;
    private static LogChannel mErrorChannel;
    private static LogChannel mWarningChannel;
    private static LogChannel mInfoChannel;
    private static LogChannel mDebugChannel;
    private static LogChannel mVerboseChannel;

    public static RootLogConfiguration getInstance(){
        if(mLogInstance == null){
           start(new RootLogConfiguration(), new LogReceptacle());
        }
        return mLogInstance;
    }

    public void enableFromFile(){

    }


    public void enableFromMainBundleFile(){

    }

    public void enable(){

    }

    public void enable(LogLevel logLevel, boolean sychrounousMode){

    }

    public void enable(LogConfiguration logConfiguration, LogLevel minimumSeverity){

    }

    private  static void  start(RootLogConfiguration root, LogReceptacle logReceptacle ) {
        start(root, new HashMap<>(), logReceptacle, LogLevel.INFO);
    }

    private static void start(RootLogConfiguration root, HashMap<String, LogAppender> appenders, LogReceptacle logReceptacle, LogLevel minimumSeverity) {
        start(root, appenders,createLogChannelWithSeverity(LogLevel.EVENT, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.SEVERE, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.ERROR, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.WARNING, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.INFO, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.DEBUG, logReceptacle, minimumSeverity),
                createLogChannelWithSeverity(LogLevel.VERBOSE, logReceptacle, minimumSeverity));
    }


    private static void start(RootLogConfiguration root, HashMap<String, LogAppender> appenders, LogChannel eventChannel, LogChannel severeChannel,
                       LogChannel errorChannel, LogChannel warningChannel, LogChannel infoChannel, LogChannel debugChannel, LogChannel verboseChannel){
        mLogInstance = root;
        mVerboseChannel = verboseChannel;
        mDebugChannel = debugChannel;
        mInfoChannel =  infoChannel;
        mWarningChannel = warningChannel;
        mErrorChannel = errorChannel;
        mSevereChannel = severeChannel;
        mEventChannel = eventChannel;
        mAppenders = appenders;
    }



    private static LogChannel createLogChannelWithSeverity(LogLevel severity,LogReceptacle receptacle,LogLevel minimumSeverity) {
        if (severity.getValue() >= minimumSeverity.getValue()) {
                return new LogChannel(severity, receptacle);
        }
        return null;
    }


    public void init(String identifier, LogConfiguration parent, Map<String, LogAppender> allAppenders, HashMap<String, Object> configuration) {

    }

    public void init(String identifier, LogLevel assignedLevel, LogConfiguration parent , ArrayList<LogAppender> logAppender,
                     boolean synchronousMode, boolean additivity){

    }

    public void verbose(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        verbose(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    private void verbose(String message , String fileName, String methodName, int lineNumber){
        message(mLogInstance, LogLevel.VERBOSE,message, fileName, methodName, lineNumber);
    }


    private void trace(LogConfiguration logger, LogLevel severity , String fileName, String methodName, int lineNumber){

    }


    private void message(LogConfiguration logger, LogLevel severity , String message, String fileName, String methodName, int lineNumber) {
        channelForSeverity(severity).message(logger,message, fileName, methodName, lineNumber);
    }


    private void value(LogConfiguration logger, LogLevel severity, Object value, String fileName, String methodName, int lineNumber)
    {

    }



    private LogChannel channelForSeverity(LogLevel severity) {
        LogChannel logChannel = mVerboseChannel;
        switch (severity) {
            case DEBUG:
                logChannel = mDebugChannel;
                break;
            case INFO:
                logChannel = mInfoChannel;
            case WARNING:
                logChannel = mWarningChannel;
            case ERROR:
                logChannel = mErrorChannel;
            case SEVERE:
                logChannel = mSevereChannel;
            case EVENT:
                logChannel = mEventChannel;
                break;
        }
        return logChannel;
       }

    public LogConfiguration  getLogger(String identifier){
        return mLogInstance.getChildren(identifier, this);
    }

}




