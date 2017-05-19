package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.constant.LogAppenderConstant;
import com.vascomouta.VMLogger.constant.LogFilterConstant;
import com.vascomouta.VMLogger.constant.LogFormatterConstant;
import com.vascomouta.VMLogger.implementation.formatter.DefaultLogFormatter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public  class BaseLogAppender extends LogAppender {


    /** The name of the `LogRecorder`, which is constructed automatically
     based on the `filePath`. */
    public String name;

    /** The `LogFormatter`s that will be used to format messages for
     the `LogEntry`s to be logged. */
    public ArrayList<LogFormatter> formatters;

    /** The list of `LogFilter`s to be used for filtering log messages. */
    public ArrayList<LogFilter> filters;

    /** The GCD queue that should be used for logging actions related to
     the receiver. */
    public ThreadPoolExecutor queue;

    public BaseLogAppender(){

    }

    /**
     Initialize a new `LogRecorderBase` instance to use the given parameters.

     :param:     name The name of the log recorder, which must be unique.

     :param:     formatters The `LogFormatter`s to use for the recorder.
     */
    public BaseLogAppender(String name, ArrayList<LogFormatter> formatters, ArrayList<LogFilter> filters)
    {
        this.name = name;
        this.formatters = formatters;
        formatters.add(new DefaultLogFormatter(true, true, true, true, true, true, true, true, true));
        //this.queue =
        this.filters = filters;
    }

    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        BaseLogAppender config = (BaseLogAppender) configuration.get(name);
        if(config != null){
            return new BaseLogAppender(config.name, config.formatters, config.filters);
        }
        return null;
    }

    public BaseLogAppender  configuration(Dictionary<String, Object> configuration,String name ,ArrayList<LogFormatter> formatters,  ArrayList<LogFilter> filters) {
         name = (String)configuration.get(LogAppenderConstant.Name);
        if(name != null) {
            BaseLogAppender returnConfir = new BaseLogAppender();
            returnConfir.name = name;

            returnConfir.formatters = new ArrayList<>();

            HashMap<String, Object> encodersConfig = (HashMap<String, Object>) configuration.get(LogAppenderConstant.Encoder);
            //ArrayList<String> patternsConfig = ArrayList<String> configuration.get(LogAppenderConstant.P)
            //TODO PatternLogFormatter

            ArrayList<HashMap<String, Object>> customFormatterConfig = (ArrayList<HashMap<String, Object>>) encodersConfig.get(LogAppenderConstant.Formatters);
            for (HashMap<String, Object> formatterConfig : customFormatterConfig) {
                String className = (String) formatterConfig.get(LogFormatterConstant.class);
                if (className != null) {
                    try {
                        Class<?> c = Class.forName(className);
                        Constructor<?> cons = c.getConstructor(String.class);
                        LogFormatter formatter = (LogFormatter) cons.newInstance();
                        if (formatter != null) {
                            formatter.init(formatterConfig);
                            returnConfir.formatters.add(formatter);
                        }
                    } catch (ClassNotFoundException ex) {

                    } catch (IllegalAccessException exception) {

                    } catch (NoSuchMethodException ex1) {

                    } catch (InvocationTargetException ex2) {

                    } catch (InstantiationException ex3) {

                    }
                } else {
                    returnConfir.formatters.add(new DefaultLogFormatter());
                }

                //Appender filter
                ArrayList<HashMap<String, Object>> filtersConfig = (ArrayList<HashMap<String, Object>>) configuration.get(LogAppenderConstant.Filters);
                if (filtersConfig != null) {
                    for (HashMap<String, Object> filterConfig : filtersConfig) {
                        String filterClassName = (String) filterConfig.get(LogFilterConstant.class);
                        if (filterClassName != null) {
                            try {
                                Class<?> c = Class.forName(className);
                                Constructor<?> cons = c.getConstructor(String.class);
                                LogFilter filter = (LogFilter) cons.newInstance();
                                if (filter != null) {
                                    //TODO initilize filter
                                    // formatter.init(formatterConfig);
                                    returnConfir.filters.add(filter);
                                }
                            } catch (ClassNotFoundException ex) {

                            } catch (IllegalAccessException exception) {

                            } catch (NoSuchMethodException ex1) {

                            } catch (InvocationTargetException ex2) {

                            } catch (InstantiationException ex3) {

                            }
                        }
                    }
                }

            }
            return returnConfir;
        }
       return null;
    }



    /**
     This implementation does nothing. Subclasses must override this function
     to provide actual log recording functionality.

     **Note:** This function is only called if one of the `formatters`
     associated with the receiver returned a non-`nil` string.

     :param:     message The message to record.

     :param:     entry The `LogEntry` for which `message` was created.

     :param:     currentQueue The GCD queue on which the function is being
     executed.

     :param:     synchronousMode If `true`, the receiver should record the
     log entry synchronously. Synchronous mode is used during
     debugging to help ensure that logs reflect the latest state
     when debug breakpoints are hit. It is not recommended for
     production code.
     */
    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, boolean sychronousMode) {
        //precondition(false, "Must override this")
    }


}
