package com.vascomouta.VMLogger.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sourabh Kapoor on 17/05/17.
 */

public class BackgroundExecutor {

    private static BackgroundExecutor _bgExecutor;
    private static ExecutorService _exService;

    private BackgroundExecutor() {}

    public static BackgroundExecutor getInstance() {
        if(_bgExecutor == null) {
            _bgExecutor = new BackgroundExecutor();
        }
        if(_exService == null) {
            _exService = Executors.newFixedThreadPool(8);
        }

        return _bgExecutor;
    }

    public void execute(Runnable runnable) {
        if(_exService == null) {
            _exService = Executors.newFixedThreadPool(8);
        }

        _exService.submit(runnable);
    }

    public void stop() {
        if (_exService!=null) {
            _exService.shutdownNow();
            _exService=null;
        }
    }

}
