package com.vascomouta.VMLogger.implementation.appender;

import android.os.Environment;

import com.vascomouta.VMLogger.Log;
import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.implementation.BaseLogAppender;
import com.vascomouta.VMLogger.utils.DispatchQueue;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A `LogRecorder` implementation that maintains a set of daily rotating log
 *files, kept for a user-specified number of days.
 *                 *Important:**
 * The `DailyRotatingLogFileRecorder` is expected to have full
 * control over the `directoryPath` with which it was instantiated. Any file not
 * explicitly known to be an active log file may be removed during the pruning
 * process. Therefore, be careful not to store anything in the `directoryPath`
 * that you wouldn't mind being deleted when pruning occurs.
 */
public class DailyRotatingLogFileAppender extends BaseLogAppender {


    /**
     * The number of days for which the receiver will retain log files
     * before they're eligible for pruning.
     */
    public int daysToKeep;

    /**
     * The filesystem path to a directory where the log files will be
     * stored.
     */
    public String directoryPath;

    private Date mostRecentLogTime;
    private FileLogAppender currentFileRecorder;


    private static DateFormat fileNameFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd'.txt'");
    }

    public DailyRotatingLogFileAppender(int daysToKeep, String directoryPath, ArrayList<LogFormatter> formatters) {
        super("DailyRotatingLogFileRecorder[" + directoryPath +"]", formatters, new ArrayList<>());
        this.daysToKeep = daysToKeep;
        this.directoryPath = directoryPath;
        // try to create the directory that will contain the log files
        File file = new File(directoryPath);
        file.mkdir();
    }


    @Override
    public LogAppender init(HashMap<String, Object> configuration) {
        Assert.fail("init(configuration:) has not been implemented");
        return null;
    }

    /**
     * Returns a string representing the filename that will be used to store logs
     * recorded on the given date.
     * @param date date The `NSDate` for which the log file name is desired.
     * @return The filename.
     */
    public String logFilenameForDate(Date date){
        return fileNameFormatter().format(date);
    }

    private FileLogAppender fileLogRecordedForDate(Date date, String directoryPath, ArrayList<LogFormatter> formatters){
            String fileName = logFilenameForDate(date);
            String filePath =  "logs/" + fileName;
        return new FileLogAppender(filePath, filePath, formatters, new ArrayList<>());
    }


    private FileLogAppender fileLogRecorderForDate(Date date) {
        return fileLogRecordedForDate(date, directoryPath,  formatters);
    }

    private boolean isDate(Date firstDate, Date secondDate){
        String firstDateStr = logFilenameForDate(firstDate);
        String secondDateStr = logFilenameForDate(secondDate);
        return firstDateStr.equals(secondDateStr);
    }

    /**
     * Called by the `LogReceptacle` to record the specified log message.
     *    *Note:**
     * This function is only called if one of the `formatters`
     * associated with the receiver returned a non-`nil` string.
     * @param message message The message to record.
     * @param logEntry entry The `LogEntry` for which `message` was created.
     * @param dispatchQueue currentQueue The GCD queue on which the function is being executed.
     * @param synchronousMode  If `true`, the receiver should record the
     *                         log entry synchronously. Synchronous mode is used during
     *                         debugging to help ensure that logs reflect the latest state
     *                         when debug breakpoints are hit. It is not recommended for production code.
     */
    @Override
    public void recordFormatterMessage(String message, LogEntry logEntry, DispatchQueue dispatchQueue, boolean synchronousMode) {

        if(mostRecentLogTime == null || !isDate(logEntry.timestamp, mostRecentLogTime)){
            prune();
            currentFileRecorder = fileLogRecorderForDate(logEntry.timestamp);
        }
        mostRecentLogTime = logEntry.timestamp;
        currentFileRecorder.recordFormatterMessage(message, logEntry, dispatchQueue, synchronousMode);
    }

    /**
     *
     */
    public void  prune()
    {
        // figure out what files we'd want to keep, then nuke everything else
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        Set<String> filesToKeep = new HashSet<>();
        for (int i = 0; i < daysToKeep; i++) {
            String fileName = logFilenameForDate(date);
            filesToKeep.add(fileName);
            cal.add(Calendar.DAY_OF_MONTH, -1);
          }

            File file = new File(directoryPath);
            ArrayList<File> pathToRemove = new ArrayList<>();
            for(File filename : file.listFiles()) {
                if (!file.getPath().startsWith("/.")) {
                    if (!filesToKeep.contains(filename)) {
                        pathToRemove.add(filename);
                    }
                }
            }
            for (File removeFile : pathToRemove) {
                try {
                    removeFile.delete();
                } catch (Exception ex) {
                    Log.printError("Error attempting to delete the unneeded file" +  removeFile.getPath() + ex.getMessage());
                }
            }

    }


}


