package com.vascomouta.VMLogger_example;

import android.app.Application;

/**
 * Created by Sourabh Kapoor on 17/05/17.
 */

public class VMLoggerApplication extends Application {


    public AppLogger applogger;

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
        AppLogger.enable(true);
        applogger = new AppLogger().getLogger(VMLoggerApplication.class.getCanonicalName());
        applogger.verbose("Message from Application class");
    }
}
