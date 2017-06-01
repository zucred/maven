package com.vascomouta.VMLogger.implementation.appender;

import android.os.Environment;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFilter;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.constant.FileLogAppenderConstant;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A `LogRecorder` implementation that stores log messages in a file.
 **Note:** This implementation provides no mechanism for log file rotation
 * or log pruning. It is the responsibility of the developer to keep the log
 * file at a reasonable size. Use `DailyRotatingLogFileRecorder` instead if you'd
 */
public class FileLogAppender extends BaseLogAppender {

    /** The path of the file to which log messages will be written. */
    public String  filePath;

    private File file;
    private String newlineCharset;


    /**
     * Attempts to initialize a new `FileLogRecorder` instance to use the
     * given file path and log formatters. This will fail if `filePath` could
     *not be opened for writing.
     * @param filePath filePath The path of the file to be written. The containing
     * directory must exist and be writable by the process. If the
     * file does not yet exist, it will be created; if it does exist,
     * new log messages will be appended to the end.
     * @param formatters formatters The `LogFormatter`s to use for the recorder.
     */
    public FileLogAppender(String filePath, ArrayList<LogFormatter> formatters){
        new FileLogAppender(filePath, filePath, formatters, new ArrayList<>());
    }

    public FileLogAppender(String name, String filePath, ArrayList<LogFormatter> formatters, ArrayList<LogFilter> filters){
        File directory  = Environment.getExternalStorageDirectory();
        String nsSt = directory.getPath();
        String fileNamePath = nsSt.concat(filePath);
        File file = new File(fileNamePath);
        if(file != null) {
            this.filePath = fileNamePath;
            this.file = file;
            this.newlineCharset = "/n";
            new BaseLogAppender(name, formatters, filters);
        }
    }


    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        String filePath = (String) configuration.get(FileLogAppenderConstant.FileName);
        if(filePath == null){
            return null;
        }
        LogAppender config = init(configuration);
        if(config == null){
            return null;
        }

         new FileLogAppender(config.name, filePath, config.formatters, config.filters);
        return null;
    }

    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue dispatchQueue, boolean sychronousMode) {
        boolean addNewline = true;
        if (message.length() > 0) {
            int c = message.charAt(message.length() -1);
            //let c = unichar(uniStr[uniStr.index(before: uniStr.endIndex)].value)
           // addNewline = !newlineCharset.contains(UnicodeScalar(c)!)
        }

        String writeStr = message;
        if (addNewline) {
            writeStr += "\n";
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            if(!file.exists()){
               file.createNewFile();
            }
            byte[] contentInBytes = writeStr.getBytes();
            out.write(contentInBytes);
            out.flush();
            out.close();
        }catch (IOException ex){
            Log.printError("Error on write logs on file" + ex.getMessage());
        }
    }
}
