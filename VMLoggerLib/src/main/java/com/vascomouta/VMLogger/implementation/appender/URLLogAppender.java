package com.vascomouta.VMLogger.implementation.appender;

import android.util.Log;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.constant.URLLogAppendConstants;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;
import com.vascomouta.VMLogger.utils.DispatchQueue;
import com.vascomouta.VMLogger.webservice.HttpUrlConnectionUtil;
import com.vascomouta.VMLogger.webservice.Response;

import java.util.ArrayList;
import java.util.HashMap;


public class URLLogAppender extends BaseLogAppender {


    private String url;
    private HashMap<String, String> headers;
    private String method;
    private String parameter;

    public URLLogAppender(){

    }
    /**
     * Attempts to initialize a new `UrlLogAppender` instance to use the
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

    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        URLLogConfiguration config =  URLLogConfiguration(configuration);
        if(config == null){
            return null;
        }
      return  new URLLogAppender(config.name, config.url, config.method, config.parameter, config.headers, config.formatters,config.filters);
    }



    public URLLogConfiguration URLLogConfiguration(HashMap<String, Object> configuration){

        LogAppender config = configuration(configuration);
        if(config == null){
            return null;
        }

        String url = (String)configuration.get(URLLogAppendConstants.ServerUrl);

        HashMap<String, String> headers = (HashMap<String, String>) configuration.get(URLLogAppendConstants.Headers);
        if(headers == null){
            headers = new HashMap<>();
        }

        String method = (String) configuration.get(URLLogAppendConstants.Method);
        if(method == null){
            method = "POST";
        }

        String parameter = (String) configuration.get(URLLogAppendConstants.Parameter);
        if(parameter == null){
            parameter = "";
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
     * Called by the `LogReceptacle` to record the specified log message.
     * *Note:** This function is only called if one of the `formatters`
     * associated with the receiver returned a non-`nil` string.
     * @param message message The message to record.
     * @param logEntry  entry The `LogEntry` for which `message` was created.
     * @param dispatchQueue currentQueue The GCD queue on which the function is being  executed.
     * @param synchronousMode synchronousMode If `true`, the receiver should record the
     * log entry synchronously. Synchronous mode is used during
     * debugging to help ensure that logs reflect the latest state
     * when debug breakpoints are hit. It is not recommended for production code.
     */
    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue dispatchQueue, boolean synchronousMode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpUrlConnectionUtil.request("http://192.168.1.126:8080/UMS/sendLog",message, method, headers);
                if(response != null){
                    Log.d("URLLogAppender", "Log post to server sucessfully");
                }
            }
        }).start();

    }

}
