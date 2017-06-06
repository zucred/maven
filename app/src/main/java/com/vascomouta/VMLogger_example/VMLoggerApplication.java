package com.vascomouta.VMLogger_example;

import android.app.Application;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogLevel;


public class VMLoggerApplication extends Application {


    public Log applogger;

    private static VMLoggerApplication mInstance;

    public VMLoggerApplication() {
        mInstance = this;
    }

    public static VMLoggerApplication getInstance() {
        if(mInstance == null) {
            mInstance = new VMLoggerApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // AppLogger.enable(LogLevel.VERBOSE, false);
        AppLogger.enableFromFile(getApplicationContext(), null);
        applogger = new AppLogger().getLogger(VMLoggerApplication.class.getCanonicalName());
        applogger.verbose("Message from Application class");
    }
}
