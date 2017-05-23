package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogAppender;
import com.vascomouta.VMLogger.LogConfiguration;
import com.vascomouta.VMLogger.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public class BaseLogConfiguration extends LogConfiguration {

    private HashMap<String, LogConfiguration> childMap = new HashMap<>();

    public BaseLogConfiguration(String identifier, LogConfiguration parent , ArrayList<LogAppender> appenders, boolean synchronousMode, boolean additivity) {
        this(identifier, null, parent, appenders, synchronousMode);
    }


    public BaseLogConfiguration(String identifier, LogLevel assignedLevel,LogConfiguration parent, ArrayList<LogAppender> appenders,
                                boolean synchronousMode)
    {
        this.identifier = identifier;
        this.additivity = true;
        this.assignedLogLevel = assignedLevel;
        this.appenders = appenders;
        this.synchronousMode = synchronousMode;
        this.parent = parent;
        this.effectiveLogLevel = parent != null ? parent.effectiveLogLevel : LogLevel.VERBOSE;
    }

    public BaseLogConfiguration init(String identifier, LogLevel assignedLevel, LogConfiguration parent , ArrayList<LogAppender> logAppender,
                                     boolean synchronousMode){
        return  new BaseLogConfiguration(identifier, assignedLevel, parent, logAppender, synchronousMode);
    }

    public void setChildren(){
        children = new ArrayList<>();
        Iterator it = childMap.entrySet().iterator();
        while (it.hasNext()){
            Set childEntrySet = childMap.entrySet();
            Iterator childIterator = childEntrySet.iterator();
            while(childIterator.hasNext()){
                Map.Entry childMe = (Map.Entry)childIterator.next();
                children.add((LogConfiguration) childMe.getValue());
            }
        }
    }


    @Override
    public void addChildren(LogConfiguration childConfiguration, boolean copyGrandChildren) {
        childConfiguration.setParent(this);
        childMap.put(childConfiguration.identifier, childConfiguration);
        LogConfiguration oldConfiguration = childMap.get(childConfiguration.identifier);
        if(oldConfiguration != null && copyGrandChildren){
             for(LogConfiguration grandChildren : oldConfiguration.children){
                childConfiguration.addChildren(grandChildren, copyGrandChildren);
             }
        }
    }

    @Override
    public LogConfiguration getChildren(String name) {
        return childMap.get(name);
    }


    public  void setParent(LogConfiguration parent) {
        this.parent = parent;
    }

    @Override
    public String fullName() {
        return "Log";
    }

    @Override
    public String details() {
        String details = "\n";
        LogLevel assigned = assignedLogLevel;
        if(assigned != null) {
            details = details + assigned.description() + " - " + effectiveLogLevel.description() + "-"
                    + fullName();
        }else{
            details = details + "null - " + effectiveLogLevel.description() + " - " + fullName();
        }
       /* for (_, child) in self.childrenDic {
            details += child.details()
        }*/
        return details;
    }
}
