package com.vascomouta.VMLogger.implementation;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.LogLevel;
import com.vascomouta.VMLogger.utils.ObjectType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


public class BaseLogFormatter implements LogFormatter {


    protected DateFormat dateFormate;
    protected int severityTagLength;
    protected int identityTagLength;

    protected BaseLogFormatter(){

    }

    public BaseLogFormatter(DateFormat dateFormatter, int severityTagLength, int identityTagLength){
        this.dateFormate = dateFormatter;
        this.severityTagLength = severityTagLength;
        this.identityTagLength = identityTagLength;
    }

    @Override
    public void init(Map<String, Object> configuration) {
        new BaseLogFormatter(timeStampFormatter(), 0, 0);
    }

    private DateFormat timeStampFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz" , Locale.US);
    }


    /**
     *  Returns a formatted representation of the given `LogEntry`.
     *
     * @param logEntry entry The `LogEntry` being formatted.
     * @param message
     * @return The formatted representation of `entry`. This particular implementation will never return `nil`.
     */
    public String formatLogEntry( LogEntry logEntry, String message){
       // precondition(false, "Must override this");
        return null;
    }


    /**
     * Returns a string representation for a calling file and line.
     * This implementation is used by the `DefaultLogFormatter` for creating string representations of a `LogEntry`'s
     * `callingFilePath` and `callingFileLine` properties.
     * @param filePath filePath The full file path of the calling file.
     * @param line line The line number within the calling file.
     * @return The string representation of `filePath` and `line`.
     */
    public static String stringRepresentationForCallingFile(String filePath, int line) {
        String file = filePath != null ? filePath : "(unknown)";
        return file + ":" + line;
    }


    /**
     * Returns a string representation for a calling file and line.
     * This implementation is used by the `DefaultLogFormatter` for creating
     * string representations of a `LogEntry`'s `callingFilePath` and callingFileLine` properties.
     * @param filePath filePath The full file path of the calling file.
     * @return The string representation of `filePath`
     */
    protected static String stringRepresentationForFile(String filePath){
        return filePath != null ? filePath : "(unknown)";
    }

    /**
     * Returns a string representation of an arbitrary optional value.
     * This implementation is used by the `DefaultLogFormatter` for creating
     *   string representations of `LogEntry` payloads.
     * @param entry entry The `LogEntry` whose payload is desired in string form.
     * @return The string representation of `entry`'s payload.
     */
    public static String stringRepresentationForPayload(LogEntry entry) {
        switch (entry.payload){
            case MESSAGE:
                return entry.message;
            case VALUE:
                return stringRepresentationForValuePayload(entry.value);
        }
        return  "";
    }


    /**
     * Returns a string representation of an arbitrary optional value.
     *
     * This implementation is used by the `DefaultLogFormatter` for creating
     * string representations of `LogEntry` instances containing `.Value` payloads.
     *
     * @param value  value The value for which a string representation is desired
     * @return If value is `nil`, the string "`(nil)`" is returned; otherwise, the return value of
     * `stringRepresentationForValue(Any)` is returned.
     */
    private static String stringRepresentationForValuePayload(@Nullable Object value){
            if(value != null){
               return stringRepresentationForValue(value);
            }
                return "(null)";
    }


    public static String stringRepresentationForExec() {
        //closure()
        return "(Executed)";
    }

    protected static String stringRepresentationForMDC() {
        boolean isUiThread = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? Looper.getMainLooper().isCurrentThread()
                : Thread.currentThread() == Looper.getMainLooper().getThread();
        if(isUiThread) {
             return  "[main] ";
        }else {
            long threadName =  Thread.currentThread().getId();
            if(threadName != 0) {
                return "[" + threadName + "] ";
            }else {
                return "[" + String.format("%d", Thread.currentThread().getId() + "] ");
            }
        }
    }

    /**
     * Returns a string representation of an arbitrary value.
     * This implementation is used by the `DefaultLogFormatter` for creating
     *  string representations of `LogEntry` instances containing `.Value` payloads.
     * @param value  value The value for which a string representation is desired.
     * @return A string representation of `value`.
     */
    private static String stringRepresentationForValue(@NonNull Object value){
        String type = ObjectType.getType(value);
        String desc= value.toString();
        if(value.toString()== null){
            desc = "(no description)";
        }
       return "<" + type + " : " + desc + " >";
   }


    /**
     *  Returns a string representation of a given `LogSeverity` value.
     *  This implementation is used by the `DefaultLogFormatter` for creating
     *  string representations for representing the `severity` value of `LogEntry` instances.
     * @param string severity The `LogSeverity` for which a string representation  is desired.
     * @param length
     * @param right
     * @return A string representation of the `severity` value.
     */
    private  static String stringRepresentation(String string, int length, boolean right) {
        if(length > 0) {
            String str = string;
            if(str.length() < length) {
                while(str.length() < length) {
                    if(right) {
                        str = str + " ";
                    } else {
                        str = " " + str;
                    }
                }
            } else {
                int index = str.indexOf(0, length);
                str = string.substring(index);
            }
            return str;
        }
        return string;
    }


    /**
     *  Returns a string representation of a given `LogSeverity` value.
     *
     *  This implementation is used by the `DefaultLogFormatter` for creating
     *  string representations for representing the `severity` value of
     *   `LogEntry` instances.
     * @param severity severity The `LogSeverity` for which a string representation is desired.
     * @return  A string representation of the `severity` value.
     */
    protected String stringRepresentationOfSeverity(LogLevel severity) {
        return BaseLogFormatter.stringRepresentation(severity.description(), severityTagLength, false);
    }


    /**
     * Returns a string representation of a given `LogSeverity` value.
     * This implementation is used by the `DefaultLogFormatter` for creating
     * string representations for representing the `severity` value of
     * `LogEntry` instances.
     * @param identity severity The `LogSeverity` for which a string representation  is desired.
     * @return  A string representation of the `severity` value.
     */
    protected String stringRepresentationOfIdentity(String identity) {
        return BaseLogFormatter.stringRepresentation(identity, severityTagLength, false);
    }


    /**
     * Returns a string representation of an `` timestamp.
     * This implementation is used by the `DefaultLogFormatter` for creating
     * string representations of a `LogEntry`'s `timestamp` property.
     * @param timestamp timestamp The timestamp.
     * @return The string representation of `timestamp`.
     */
    protected String stringRepresentationOfTimestamp(Date timestamp) {
        if(dateFormate == null){
            dateFormate = timeStampFormatter();
        }
        return dateFormate.format(timestamp);
    }


    /**
     * Returns a string representation of a thread identifier.
     * This implementation is used by the `DefaultLogFormatter` for creating
     * string representations of a `LogEntry`'s `callingThreadID` property.
     * @param threadID  threadID The thread identifier.
     * @return The string representation of `threadID`.
     */
    protected   static String stringRepresentationOfThreadID(long threadID){
        return String.format("%08X", threadID);
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
