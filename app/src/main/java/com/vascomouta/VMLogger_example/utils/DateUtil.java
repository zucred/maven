package com.vascomouta.VMLogger_example.utils;


import java.util.Date;

public class DateUtil {

    public static final String TAG = DateUtil.class.getSimpleName();

    public static long getTimeInterval(Date startDate , Date endDate){
        return endDate.getTime() - startDate.getTime();
    }


}
