package com.vascomouta.VMLogger_example;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks{


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppLogger.appBecomeActive();
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppLogger.appMovedToForeground();
    }


    @Override
    public void onActivityResumed(Activity activity) {
        AppLogger.appResignActive();
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        AppLogger.appMovedToBackground();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }



    @Override
    public void onActivityDestroyed(Activity activity) {
       AppLogger.appTerminate();
    }
}
