package com.vascomouta.VMLogger.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class DispatchQueue {

    private ExecutorService syncExecuterService;
    private ExecutorService asyncExecuterService;


     public void sync(Runnable runnable){
         if(syncExecuterService == null) {
             syncExecuterService = Executors.newSingleThreadExecutor();
         }
         syncExecuterService.submit(runnable);
     }


     public void async(Runnable runnable){
        if(asyncExecuterService == null){
            asyncExecuterService = Executors.newFixedThreadPool(8);
        }
        asyncExecuterService.submit(runnable);
     }


}
