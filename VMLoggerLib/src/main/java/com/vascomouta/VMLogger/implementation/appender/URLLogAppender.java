package com.vascomouta.VMLogger.implementation.appender;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.constant.URLLogAppendContants;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.util.ArrayList;
import java.util.HashMap;


public class URLLogAppender extends BaseLogAppender {


    public String url;

    public HashMap<String, String> headers;

    public String method;

    public String parameter;

    public URLLogAppender(){

    }

    /**
     * Attempts to initialize a new `FileLogRecorder` instance to use the
     * given file path and log formatters. This will fail if `filePath` could
     * not be opened for writing.
     * @param url
     * @param method
     * @param parameter
     * @param headers apiKey
     * @param formatters formatters The `LogFormatter`s to use for the recorder.
     */
    public  URLLogAppender(String url, String method, String  parameter, HashMap<String, String> headers, ArrayList<LogFormatter> formatters) {
      new URLLogAppender("URLLogRecorder[" + url + "]", url, method, parameter, headers, formatters, new ArrayList<>());
    }


    /**
     * Attempts to initialize a new `FileLogRecorder` instance to use the
     * given file path and log formatters. This will fail if `filePath` could
     * not be opened for writing.
     * @param name
     * @param url
     * @param method
     * @param parameter
     * @param headers
     * @param formatters formatters The `LogFormatter`s to use for the recorder.
     * @param filters
     */
    public URLLogAppender(String name,String  url, String method,String  parameter, HashMap<String, String> headers, ArrayList<LogFormatter> formatters, ArrayList<LogFilter> filters)
    {
        super(name, formatters, filters);
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.parameter = parameter;

    }

    public  URLLogAppender( HashMap<String, Object> configuration) {
        URLLogConfiguration config =  URLLogConfiguration(configuration);
        if(config == null){
            return;
        }
        new URLLogAppender(config.name, config.url, config.method, config.parameter, config.headers, config.formatters,config.filters);
    }


    public URLLogConfiguration URLLogConfiguration(HashMap<String, Object> configuration){

        LogAppender config = configuration(configuration);
        if(config == null){
            return null;
        }

        String url = (String)configuration.get(URLLogAppendContants.ServerUrl);
        if(url == null){
            return null;
        }


        HashMap<String, String> headers = (HashMap<String, String>) configuration.get(URLLogAppendContants.Headers);
        if(headers == null){
            return null;
        }

        String method = (String) configuration.get(URLLogAppendContants.Method);
        if(method == null){
            return null;
        }

        String parameter = (String) configuration.get(URLLogAppendContants.Parameter);
        if(parameter == null){
            return null;
        }

        return new URLLogConfiguration(config.name, url, method, parameter, headers, config.formatters, config.filters);
    }

    public class URLLogConfiguration{
        String name;
        String url;
        String method;
        String parameter;
        HashMap<String, String> headers;
        ArrayList<LogFormatter> formatters;
        ArrayList<LogFilter> filters;

        public URLLogConfiguration(String name, String url , String method, String parameter, HashMap<String, String> headers,
                                   ArrayList<LogFormatter> formatters, ArrayList<LogFilter> filters){
            this.name = name;
            this.method = method;
            this.parameter = parameter;
            this.headers = headers;
            this.formatters = formatters;
            this.filters = filters;
        }
    }


    /**
     * /**
     Called by the `LogReceptacle` to record the specified log message.

     **Note:** This function is only called if one of the `formatters`
     * associated with the receiver returned a non-`nil` string.
     * @param message message The message to record.
     * @param logEntry  entry The `LogEntry` for which `message` was created.
     * @param dispatchQueue urrentQueue The GCD queue on which the function is being  executed.
     * @param synchronousMode synchronousMode If `true`, the receiver should record the
     * log entry synchronously. Synchronous mode is used during
     * debugging to help ensure that logs reflect the latest state
     * when debug breakpoints are hit. It is not recommended for production code.
     */
    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue dispatchQueue, boolean synchronousMode) {


        String url = (this.method == "GET" ? this.url : this.url);
        //ToDO Api calling
    }

    public void setRequestHeaders () {
        //TODO add headers on reqest

    }

}
