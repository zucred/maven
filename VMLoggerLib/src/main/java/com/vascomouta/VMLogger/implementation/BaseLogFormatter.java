package com.vascomouta.VMLogger.implementation;

import android.os.Build;
import android.os.Looper;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.LogFormatter;
import com.vascomouta.VMLogger.LogLevel;
import com.vascomouta.VMLogger.enums.Payload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public class BaseLogFormatter implements LogFormatter {


    public DateFormat dateFormate;
    public int severityTagLength;
    public int identityTagLength;

    public BaseLogFormatter(){

    }

    public BaseLogFormatter(DateFormat dateFormatter, int severityTagLenght, int identityTagLenght){
        this.dateFormate = dateFormatter;
        this.severityTagLength = severityTagLenght;
        this.identityTagLength = identityTagLenght;
    }

    @Override
    public void init(Map<String, Object> configuration) {
        new BaseLogFormatter(timeStampFormatter(), 0, 0);
    }

    private DateFormat timeStampFormatter(){
        //toto apply DateFormatter
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz" , Locale.US);
        return dateFormat;
    }


    /**
     Returns a formatted representation of the given `LogEntry`.

     :param:         entry The `LogEntry` being formatted.

     :returns:       The formatted representation of `entry`. This particular
     implementation will never return `nil`.
     */
    public String formatLogEntry( LogEntry logEntry, String message){
       // precondition(false, "Must override this");
        return null;
    }


    /**
     Returns a string representation for a calling file and line.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of a `LogEntry`'s `callingFilePath` and
     `callingFileLine` properties.

     :param:     filePath The full file path of the calling file.

     :param:     line The line number within the calling file.

     :returns:   The string representation of `filePath` and `line`.
     */
    public static String stringRepresentationForCallingFile(String filePath, int line) {
        String file = filePath != null ? filePath : "(unknown)";
        return file + ":" + line;
    }

    /**
     Returns a string representation for a calling file and line.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of a `LogEntry`'s `callingFilePath` and
     `callingFileLine` properties.

     :param:     filePath The full file path of the calling file.

     :param:     line The line number within the calling file.

     :returns:   The string representation of `filePath` and `line`.
     */
    public static String stringRepresentationForFile(String filePath){
        return filePath != null ? filePath : "(unknown)";
    }


    /**
     Returns a string representation of an arbitrary optional value.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of `LogEntry` payloads.

     :param:     entry The `LogEntry` whose payload is desired in string form.

     :returns:   The string representation of `entry`'s payload.
     */
    public static String stringRepresentationForPayload(LogEntry entry) {
        switch (entry.payload){
            case MESSAGE:
                return entry.message;
            case VALUE:
                //String representation for value
                return entry.callingFunction;
        }
        return  entry.message;
    }


    /**
     Returns a string representation of an arbitrary optional value.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of `LogEntry` instances containing `.Value` payloads.

     :param:     value The value for which a string representation is desired.

     :returns:   If value is `nil`, the string "`(nil)`" is returned; otherwise,
     the return value of `stringRepresentationForValue(Any)` is
     returned.
     */
    public static String stringRepresentationForValue(Object object){
        String value = stringRepresentationForValue(object);
            if(value != null){
                stringRepresentationForValue(value);
            }else{
                return "(null)";
            }
        return value;
    }

    public static String stringRepresentationForExec()
    {
        //closure()
        return "(Executed)";
    }

    public static String stringRepresentationForMDC() {
        boolean isUiThread = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? Looper.getMainLooper().isCurrentThread()
                : Thread.currentThread() == Looper.getMainLooper().getThread();
        if(isUiThread) {
             return  "[main] ";
        }else {
            String threadName =  Thread.currentThread().getName();
            if(threadName != "") {
                return "[" + threadName + "] ";
            }else {
                return "[" + String.format("%p", Thread.currentThread() + "] ");
            }
        }
    }


        /**
         Returns a string representation of an arbitrary value.

         This implementation is used by the `DefaultLogFormatter` for creating
         string representations of `LogEntry` instances containing `.Value` payloads.

         :param:     value The value for which a string representation is desired.

         :returns:   A string representation of `value`.
         */
       /* public static String stringRepresentationForValue(Object object){

           return "value";

         }*/


    /**
     Returns a string representation of a given `LogSeverity` value.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations for representing the `severity` value of
     `LogEntry` instances.

     :param:     severity The `LogSeverity` for which a string representation
     is desired.

     :returns:   A string representation of the `severity` value.
     */
    private  static String stringRepresentation(String string, int lenght, boolean right) {
        if(lenght > 0) {
            String str = string;
            String[] characters = str.split(" ");
            if(characters.length < lenght)
            {
                while(characters.length < lenght) {
                    if(right == true) {
                        str = str + " ";
                    } else {
                        str = " " + str;
                    }
                }
            } else {
               // int index = characters[string.startIndex, offsetBy: lenght)];
               // str = string.substring(index , );
            }
        }
        return string;
    }

    /**
     Returns a string representation of a given `LogSeverity` value.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations for representing the `severity` value of
     `LogEntry` instances.

     :param:     severity The `LogSeverity` for which a string representation
     is desired.

     :returns:   A string representation of the `severity` value.
     */
    public String stringRepresentationOfSeverity(LogLevel severity) {
        return BaseLogFormatter.stringRepresentation(severity.description(), severityTagLength, false);
    }


    /**
     Returns a string representation of a given `LogSeverity` value.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations for representing the `severity` value of
     `LogEntry` instances.

     :param:     severity The `LogSeverity` for which a string representation
     is desired.

     :returns:   A string representation of the `severity` value.
     */
    public String stringRepresentationOfIdentity(String identity) {
        return BaseLogFormatter.stringRepresentation(identity, severityTagLength, false);
    }


    /**
     Returns a string representation of an `` timestamp.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of a `LogEntry`'s `timestamp` property.

     :param:     timestamp The timestamp.

     :returns:   The string representation of `timestamp`.
     */
    public String stringRepresentationOfTimestamp(Date timestamp) {
        if(dateFormate == null){
            dateFormate = timeStampFormatter();
        }
        return dateFormate.format(timestamp);
    }

    /**
     Returns a string representation of a thread identifier.

     This implementation is used by the `DefaultLogFormatter` for creating
     string representations of a `LogEntry`'s `callingThreadID` property.

     :param:     threadID The thread identifier.

     :returns:   The string representation of `threadID`.
     */
    public  static String stringRepresentationOfThreadID(int threadID){
        return String.format("%08X", threadID);
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
