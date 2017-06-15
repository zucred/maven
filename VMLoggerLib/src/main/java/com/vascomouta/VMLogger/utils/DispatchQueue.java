package com.vascomouta.VMLogger.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DispatchQueue {

    private ExecutorService syncExecutorService;
    private ExecutorService asyncExecutorService;


    /**
     * This implementation execute single thread in thread pool.
     * @param runnable
     */
     public void sync(Runnable runnable){
         if(syncExecutorService == null) {
             syncExecutorService = Executors.newSingleThreadExecutor();
         }
         syncExecutorService.submit(runnable);

     }


    /**
     * This implementation execute 8 thread in parallel in thread pool
     * @param runnable
     */
     public void async(Runnable runnable){
        if(asyncExecutorService == null){
            asyncExecutorService = Executors.newFixedThreadPool(8);
        }
        asyncExecutorService.submit(runnable);
     }


}
