package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.constant.LogAppenderConstant;
import com.vascomouta.VMLogger.constant.LogFilterConstant;
import com.vascomouta.VMLogger.constant.LogFormatterConstant;
import com.vascomouta.VMLogger.implementation.formatter.DefaultLogFormatter;
import com.vascomouta.VMLogger.implementation.formatter.PatternLogFormatter;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;


public  class BaseLogAppender extends LogAppender {


    protected BaseLogAppender(){

    }

    protected BaseLogAppender(String name){
      this.name = name;

    }

    /**
     * Initialize a new `LogRecorderBase` instance to use the given parameters.
     * @param name The name of the log recorder, which must be unique.
     * @param formatters formatters The `LogFormatter`s to use for the recorder.
     */
    public BaseLogAppender(String name, ArrayList<LogFormatter> formatters, ArrayList<LogFilter> filters){
        this.name = name;
        this.formatters = formatters;
        formatters.add(new DefaultLogFormatter(true, true, true, true, true, true, true, true, true));
        this.dispatchQueue = new DispatchQueue();
        this.filters = filters;
    }


    public BaseLogAppender(String name, ArrayList<LogFormatter> formatters, DispatchQueue dispatchQueue, ArrayList<LogFilter> filters) {
        this.name = name;
        this.formatters = formatters;
        formatters.add(new DefaultLogFormatter(true, true, true, true, true, true, true, true, true));
        this.dispatchQueue = new DispatchQueue();
        this.filters = new ArrayList<>();
    }

    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        LogAppender config = configuration(configuration);
        if(config != null){
            return new BaseLogAppender(config.name, config.formatters, config.dispatchQueue, config.filters);
        }
        return null;
    }

    //TODO check method on custom  configurations
    public LogAppender configuration(HashMap<String, Object> configuration) {
        String name;
        ArrayList<LogFormatter> formatters;
        ArrayList<LogFilter> filters;
        name = (String)configuration.get(LogAppenderConstant.Name);
        if(name != null) {
            BaseLogAppender returnConfig = new BaseLogAppender();
            returnConfig.name = name;
            returnConfig.formatters = new ArrayList<>();

            HashMap<String, Object> encodersConfig = (HashMap<String, Object>) configuration.get(LogAppenderConstant.Encoder);
            if (encodersConfig != null) {
                //TODO chnage to PatternLogFormatterConstants.Pattern
                ArrayList<String> patternConfig = (ArrayList<String>) encodersConfig.get(LogAppenderConstant.Encoder);
                if (patternConfig != null) {
                    for (String pattern : patternConfig) {
                        if (pattern.isEmpty()) {
                            returnConfig.formatters.add(new PatternLogFormatter());
                        } else {
                            returnConfig.formatters.add(new PatternLogFormatter(pattern));
                        }
                    }
                }

                ArrayList<HashMap<String, Object>> customFormatterConfig = (ArrayList<HashMap<String, Object>>) encodersConfig.get(LogAppenderConstant.Formatters);
                if (customFormatterConfig != null) {
                    for (HashMap<String, Object> formatterConfig : customFormatterConfig) {
                        String className = (String) formatterConfig.get(LogFormatterConstant.class);
                        if (className != null) {
                            try {
                                Class<?> c = Class.forName(className);
                                Constructor<?> cons = c.getConstructor(String.class);
                                LogFormatter formatter = (LogFormatter) cons.newInstance();
                                if (formatter != null) {
                                    formatter.init(formatterConfig);
                                    returnConfig.formatters.add(formatter);
                                }
                            } catch (Exception ex) {
                                Log.printError("Error on get formatter name from custom configurations" + ex.getMessage());
                            }
                        }
                    }
                }
            } else {
                returnConfig.formatters.add(new DefaultLogFormatter());
            }

            //Appender filter
            ArrayList<HashMap<String, Object>> filtersConfig = (ArrayList<HashMap<String, Object>>) configuration.get(LogAppenderConstant.Filters);
            if (filtersConfig != null) {
                for (HashMap<String, Object> filterConfig : filtersConfig) {
                    String filterClassName = (String) filterConfig.get(LogFilterConstant.class);
                    if (filterClassName != null) {
                        try {
                            Class<?> c = Class.forName(filterClassName);
                            Constructor<?> cons = c.getConstructor(String.class);
                            LogFilter filter = (LogFilter) cons.newInstance();
                            if (filter != null) {
                                //TODO initialize formatter
                                // formatter.init(formatterConfig);
                                returnConfig.filters.add(filter);
                            }
                        } catch (Exception ex) {
                            Log.printError("Error on get filter name from custom configurations" + ex.getMessage());
                        }
                    }
                }
            }

            return returnConfig;
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
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue dispatchQueue, boolean synchronousMode) {
       //TODO
        //precondition(false, "Must override this")
    }


}
