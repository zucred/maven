package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogConfiguration;
import com.vascomouta.VMLogger.LogLevel;
import com.vascomouta.VMLogger.implementation.appender.ConsoleLogAppender;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public class RootLogConfiguration extends BaseLogConfiguration {


    public static String ROOT_IDENTIFIER = "root";
    public static String DOT = ".";

    public RootLogConfiguration() {
        super(RootLogConfiguration.ROOT_IDENTIFIER, LogLevel.INFO, null, new ArrayList<>(), false);
    }

    public RootLogConfiguration(LogLevel assignedLevel, ArrayList<LogAppender> appenders, boolean synchronousMode) {
        super(RootLogConfiguration.ROOT_IDENTIFIER, assignedLevel, null, appenders, synchronousMode);
    }

    public RootLogConfiguration(String identifier, LogLevel assignedLevel, LogConfiguration parent, ArrayList<LogAppender> appenders, boolean synchronousMode, boolean additivity) {
        super(identifier, assignedLevel, parent, appenders, synchronousMode);
    }

    private boolean isRootLogger() {
        // only the root logger has a null parent
        return parent == null;
    }


    private ArrayList<LogAppender> getLogAppender() {
        ArrayList<LogAppender> logAppenders = new ArrayList<>();
        logAppenders.add(new ConsoleLogAppender());
        return logAppenders;
    }

    public String fullName() {
        String name;
        if (parent.identifier != RootLogConfiguration.ROOT_IDENTIFIER) {
            LogConfiguration parent = this.parent;
            name = parent.fullName() + RootLogConfiguration.DOT + this.identifier;
        } else {
            name = this.identifier;
        }
        return name;
    }

    public<T extends LogConfiguration>  T getChildren(String identifier, T type) {
        String name = identifier;
        LogConfiguration parent = this;
        while (true) {
            if (parent.getChildren(name) != null) {
                return (T)parent.getChildren(name);
            } else {
                String tree = null;
                String[] range = name.split(Pattern.quote(RootLogConfiguration.DOT));
                if(range != null){
                    /*tree = name.substring(range[0]);
                    name = name.substring(range[1]);*/
                    tree = range[0];
                    name = range[1];
                    if(parent.getChildren(name) != null){
                        parent = parent.getChildren(name);
                        name = tree != null ? tree : name;
                        continue;
                    }
               }
              LogConfiguration child = new BaseLogConfiguration(name, null, parent, new ArrayList<>(), synchronousMode);
                parent.addChildren(child, false);
                if(tree != null){
                    return (T)child;
                }
                parent = child;
                name = tree;
        }

    }

}

}
