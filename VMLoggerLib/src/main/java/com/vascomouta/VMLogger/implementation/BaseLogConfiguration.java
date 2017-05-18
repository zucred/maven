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

public class BaseLogConfiguration implements LogConfiguration {


    public  String identifier;
    public boolean additivity;
    public LogLevel assignedLevel;
    public LogLevel effectiveLevel;
    public ArrayList<LogAppender> logAppender;
    public  boolean synchronousMode;
    public LogConfiguration parent;
    public ArrayList<LogConfiguration> children ;

    private HashMap<String, LogConfiguration> childMap;

    public BaseLogConfiguration(String identifier,LogConfiguration parent , ArrayList<LogAppender> appenders,boolean synchronousMode, boolean additivity) {
        BaseLogConfiguration(identifier, null, parent, appenders, synchronousMode, additivity);
    }


    public BaseLogConfiguration(String identifier, LogLevel assignedLevel,LogConfiguration parent, ArrayList<LogAppender> appenders,
                                boolean synchronousMode, boolean additivity)
    {
        this.identifier = identifier;
        this.additivity = additivity;
        this.assignedLevel = assignedLevel;
        this.logAppender = appenders;
        this.synchronousMode = synchronousMode;
        this.parent = parent;
        this.effectiveLevel = parent != null ? parent.getEffectiveLogLevel() : LogLevel.INFO;
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
        childMap.put(childConfiguration.getIdentifier(), childConfiguration);
        LogConfiguration oldConfiguration = childMap.get(childConfiguration.getIdentifier());
        if(oldConfiguration != null && copyGrandChildren){
             for(LogConfiguration grandChildren : oldConfiguration.getChildren()){
                childConfiguration.addChildren(grandChildren, copyGrandChildren);
             }
        }
    }

    public LogConfiguration getChildren(String name) {
        return childMap.get(name);
    }


    public  void setParent(LogConfiguration parent) {
        this.parent = parent;
    }

    @Override
    public ArrayList<LogConfiguration> getChildren() {
        return children;
    }

    public String fullName(){
       // fatalError("Needs to be redifined");
    }

    public String details(){
        String details = "\n";
        LogLevel assgined = assignedLevel;
        if(assgined != null) {
            details = details + assgined.description() + " - " + effectiveLevel.description() + "-"
                    + getFullName();
        }else{
            details = details + "null - " + effectiveLevel.description() + " - " + getFullName();
        }
       /* for (_, child) in self.childrenDic {
            details += child.details()
        }*/
        return details;
    }


    @Override
    public LogConfiguration parent() {
        return null;
    }

    @Override
    public String details() {
        return null;
    }
}
