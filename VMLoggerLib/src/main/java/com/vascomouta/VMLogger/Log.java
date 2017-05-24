package com.vascomouta.VMLogger;

import com.vascomouta.VMLogger.constant.LogAppenderConstant;
import com.vascomouta.VMLogger.constant.LogConfigConstant;
import com.vascomouta.VMLogger.implementation.appender.ConsoleLogAppender;
import com.vascomouta.VMLogger.implementation.RootLogConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public void enable(HashMap<Object, Object> values){
        LogLevel rootLevel;
        if(BuildConfig.DEBUG){
            rootLevel = LogLevel.DEBUG;
        }else{
            rootLevel = LogLevel.VERBOSE;
        }
        boolean rootSynchronous = false;
        HashMap<String, LogAppender> appenders = new HashMap<>();
        ArrayList<LogAppender> rootAppenders = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> rootChildren = new HashMap<>();
        ArrayList<HashMap<String, Object>> appenderConfig = (ArrayList<HashMap<String,Object>>) values.get(Appenders);
        for(HashMap<String, Object> appender : appenderConfig) {
            String className = (String) appender.get(LogAppenderConstant.Class);
            try {
                Class<?> c = Class.forName(className);
                Constructor<?> cons = c.getConstructor(String.class);
                LogAppender logAppender = (LogAppender) cons.newInstance();
                if (logAppender != null) {
                    LogAppender appender1 = logAppender.init(appender);
                    appenders.put(appender1.name, appender1);
                }
            } catch (ClassNotFoundException ex) {

            } catch (IllegalAccessException exception) {

            } catch (NoSuchMethodException ex1) {

            } catch (InvocationTargetException ex2) {

            } catch (InstantiationException ex3) {

            }
        }


        //Root Appenders
        ArrayList<String> rootAppenderConfig = (ArrayList<String>) values.get(LoggerAppenders);
        if (rootAppenderConfig != null) {
            for (String rootAppender : rootAppenderConfig) {
                if (appenders.get(rootAppender) != null) {
                   // rootAppenders.add(appenders.get(rootAppender));
                    rootAppenders.add(appenders.get(ConsoleLogAppender.CONSOLE_IDENTIFIER));
                }
            }
        }else if(appenders.get(ConsoleLogAppender.CONSOLE_IDENTIFIER) != null){
            rootAppenders.add(appenders.get(ConsoleLogAppender.CONSOLE_IDENTIFIER));
        }

         if(values.containsKey(LoggerLevel)) {
             String rootLogLevel = (String)values.get(LoggerLevel);
             rootLevel =  LogLevel.getLogLevel(rootLogLevel);
         }
         if(values.containsKey(LoggerSynchronous)) {
            rootSynchronous = (boolean) values.get(LoggerSynchronous);
         }
         if(values.containsKey(LoggerConfig)) {
             rootChildren = (HashMap<String, HashMap<String, Object>>) values.get(LoggerConfig);
         }
        RootLogConfiguration root = new RootLogConfiguration(rootLevel, rootAppenders, synchronousMode);
        Iterator it = rootChildren.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry child = (Map.Entry)it.next();
            HashMap<String , Object> configuration = (HashMap<String, Object>) child.getValue();
            if(configuration != null) {
                LogConfiguration currentChild = root.getChildren((String) child.getKey(), this);
                if (currentChild != null && currentChild.parent != null) {
                    LogConfiguration newChild = new Log(currentChild.identifier, parent, appenders, configuration);
                    if (newChild != null) {
                        parent.addChildren(newChild, true);
                    }
                } else {
                    //   Log.warning("Trying to configure ROOT looger in logger childreen configuration. Reserved word, children ignored")
                }
            }else {
                //Log.error("Log configuration for \(logName) is not valid. Dictionary<String, Any> is required")
            }
            }
        enable(root, rootLevel);
        verbose("Log Configuration:\n" + values.toString());

    }



    public static void enable( boolean sychrounousMode){
        LogLevel assignedLevel = LogLevel.VERBOSE;
        ArrayList<LogAppender> appenders = new ArrayList<>();
        appenders.add(new ConsoleLogAppender());
        RootLogConfiguration root = new RootLogConfiguration(RootLogConfiguration.ROOT_IDENTIFIER, assignedLevel, null, appenders, sychrounousMode, false);
        enable(root, LogLevel.VERBOSE);
    }

    public static void enable(RootLogConfiguration root, LogLevel minimumSeverity){
        Log.start(root,  new LogReceptacle(),   minimumSeverity);
    }

    private  static void  start(RootLogConfiguration root, LogReceptacle logReceptacle ) {
        start(root, logReceptacle, LogLevel.VERBOSE);
    }

    private static void start(RootLogConfiguration root, LogReceptacle logReceptacle, LogLevel minimumSeverity) {
        HashMap<String, LogAppender> appenders = new HashMap<>();
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

    public Log(){

    }

    public Log(String identifier, LogConfiguration parent, Map<String, LogAppender> allAppenders, HashMap<String, Object> configuration) {
            super(identifier, (LogLevel) configuration.get(LogConfigConstant.Level), null, (ArrayList<LogAppender>)configuration.get(LogConfigConstant.Appenders),
                    (boolean)configuration.get(LogConfigConstant.Synchronous), (boolean)configuration.get(LogConfigConstant.Additivity));
    }

    public  Log(String identifier, LogLevel assignedLevel, LogConfiguration parent , ArrayList<LogAppender> logAppender,
                     boolean synchronousMode, boolean additivity) {
        super(identifier, assignedLevel, parent, logAppender, synchronousMode, additivity);

    }

    public Log init(String identifier, LogLevel assignedLevel, LogConfiguration parent , ArrayList<LogAppender> logAppender,
                    boolean synchronousMode){
        return  new Log(identifier, assignedLevel, parent, logAppender, synchronousMode, true);
    }

    public void verbose(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        verbose(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void verbose(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        verbose(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void verbose(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        verbose(value,stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void debug(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        debug(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void debug(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        debug(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void debug(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        debug(value,stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void info(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        info(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void info(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        info(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void info(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        info(value, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void warning(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        warning(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void warning(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        warning(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void warning(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        warning(value, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void error(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        error(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void error(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        error(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void error(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        error(value, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void severe(String message){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        severe(message, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void severe(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        severe(stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void severe(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        severe(value, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    public void event(Object value){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        event(value, stackTraceElement.getFileName(), stackTraceElement.getMethodName(),  stackTraceElement.getLineNumber());
    }

    private void verbose(String message , String fileName, String methodName, int lineNumber){
        message(this, LogLevel.VERBOSE, message, fileName, methodName, lineNumber);
    }

    private void verbose( String fileName, String methodName, int lineNumber){
        trace(this, LogLevel.VERBOSE, fileName, methodName, lineNumber);
    }

    private void verbose(Object value , String fileName, String methodName, int lineNumber){
        value(this, LogLevel.VERBOSE, value, fileName, methodName, lineNumber);
    }

    private void debug(String message, String fileName, String methodName, int lineNumber){
        message(this, LogLevel.DEBUG, message, fileName, methodName, lineNumber);
    }

    private void debug(String fileName, String methodName, int lineNumber){
        trace(this, LogLevel.DEBUG, fileName, methodName, lineNumber);
    }

    private void debug(Object value, String fileName, String methodName, int lineNumber){
        value(this, LogLevel.DEBUG, value, fileName, methodName, lineNumber);
    }

    private void info(String message, String fileName, String methodName, int lineNumber){
        message(this, LogLevel.INFO, message, fileName, methodName, lineNumber);
    }

    private void info(String fileName , String methodName , int lineNumber){
        trace(this, LogLevel.INFO, fileName, methodName, lineNumber);
    }

    private  void info(Object value,String fileName,  String methodName,int lineNumber){
        value(this, LogLevel.INFO, value, fileName, methodName, lineNumber);
    }

    private void warning(String message, String fileName, String methodName,int lineNumber ){
        message(this, LogLevel.WARNING, message, fileName, methodName, lineNumber);
    }

    private void warning(String fileName, String methodName,int lineNumber){
        trace(this, LogLevel.WARNING, fileName, methodName, lineNumber);
    }

    private void warning(Object value, String fileName, String methodName,int lineNumber){
        value(this, LogLevel.WARNING, value, fileName, methodName, lineNumber);
    }

    private void error(String message, String fileName, String methodName,int lineNumber ){
        message(this, LogLevel.ERROR, message, fileName, methodName, lineNumber);
    }

    private void error(String fileName, String methodName,int lineNumber){
        trace(this, LogLevel.ERROR, fileName, methodName, lineNumber);
    }

    private void error(Object value, String fileName, String methodName,int lineNumber){
        value(this, LogLevel.ERROR, value, fileName, methodName, lineNumber);
    }

    private void severe(String message, String fileName, String methodName,int lineNumber ){
        message(this, LogLevel.SEVERE, message, fileName, methodName, lineNumber);
    }

    private void severe(String fileName, String methodName,int lineNumber){
        trace(this, LogLevel.SEVERE, fileName, methodName, lineNumber);
    }

    private void severe(Object value, String fileName, String methodName,int lineNumber){
        value(this, LogLevel.SEVERE, value, fileName, methodName, lineNumber);
    }

    private void event(Object value, String fileName, String methodName,int lineNumber){
        value(mLogInstance, LogLevel.EVENT, value, fileName, methodName, lineNumber);
    }

    private void trace(LogConfiguration logger, LogLevel severity , String fileName, String methodName, int lineNumber){
        channelForSeverity(severity).trace(logger,fileName, methodName,lineNumber);
    }


    private void message(LogConfiguration logger, LogLevel severity , String message, String fileName, String methodName, int lineNumber) {
        channelForSeverity(severity).message(logger,message, fileName, methodName, lineNumber);
    }


    private void value(LogConfiguration logger, LogLevel severity, Object value, String fileName, String methodName, int lineNumber) {
        channelForSeverity(severity).value(logger, value, fileName, methodName, lineNumber);
    }

    private LogChannel channelForSeverity(LogLevel severity) {
        switch (severity) {
            case DEBUG:
                return mDebugChannel;
            case INFO:
                return mInfoChannel;
            case WARNING:
                return mWarningChannel;
            case ERROR:
                return mErrorChannel;
            case SEVERE:
                return mSevereChannel;
            case EVENT:
                return mEventChannel;
        }
        return mVerboseChannel;
       }

    public Log getLogger(String identifier){
         return (Log)getInstance().getChildren(identifier, this);
    }

}




