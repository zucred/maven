package com.vascomouta.VMLogger;

import android.animation.ObjectAnimator;

import java.util.Map;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public interface LogFormatter {

    /**
     Called to create a string representation of the passed-in `LogEntry`.
     :param:     entry The `LogEntry` to attempt to convert into a string.
     :returns:   A `String` representation of `entry`, or `nil` if the
     receiver could not format the `LogEntry`.
     */
    String formatLogEntry(LogEntry entry, String message);


    /**
     constructor to be used by introspection
     - parameter configuration: configuration for the formatter
     - returns: if configuration is correct a new LogFormatter
     */
    void init(Map<String, Object> configuration);

}
