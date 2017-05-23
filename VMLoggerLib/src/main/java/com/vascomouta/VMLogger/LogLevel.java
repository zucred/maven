package com.vascomouta.VMLogger;

/**
 * Created by Sourabh Kapoor on 16/05/17.
 */

public enum LogLevel {

    ALL("All", 0),
    VERBOSE("Verbose", 1),
    DEBUG("Debug", 2),
    INFO("Info", 3),
    WARNING("Warning", 4),
    ERROR("Error", 5),
    SEVERE("Severe", 6),
    EVENT("Event", 7),
    OFF("OFF", 8);

    private String level;
    private int value;

    LogLevel(String level, int value){
        this.level = level;
        this.value = value;
    }


    String getLevel(){
      return level;
    }

    int getValue(){
        return value;
    }

    public String getLogLevel(LogLevel level){
        switch (level){
            case ALL:
                return ALL.getLevel();
            case VERBOSE:
                return VERBOSE.getLevel();
            case DEBUG:
                return DEBUG.getLevel();
            case INFO:
                return INFO.getLevel();
            case WARNING:
                return WARNING.getLevel();
            case ERROR:
                return ERROR.getLevel();
            case SEVERE:
                return SEVERE.getLevel();
            case EVENT:
                return EVENT.getLevel();
        }
        return OFF.getLevel();
    }

    public static int getLogLevelValue(LogLevel level){
        switch (level){
            case ALL:
                return ALL.getValue();
            case VERBOSE:
                return VERBOSE.getValue();
            case DEBUG:
                return DEBUG.getValue();
            case INFO:
                return INFO.getValue();
            case WARNING:
                return WARNING.getValue();
            case ERROR:
                return ERROR.getValue();
            case SEVERE:
                return SEVERE.getValue();
            case EVENT:
                return EVENT.getValue();
        }
        return OFF.getValue();
    }

    public String description() {
        switch (this) {
            case ALL:
                return ALL.getLevel();
            case VERBOSE:
                return VERBOSE.getLevel();
            case DEBUG:
                return DEBUG.getLevel();
            case INFO:
                return INFO.getLevel();
            case WARNING:
                return WARNING.getLevel();
            case ERROR:
                return ERROR.getLevel();
            case SEVERE:
                return SEVERE.getLevel();
            case EVENT:
                return EVENT.getLevel();
        }
        return OFF.getLevel();
    }

    public static LogLevel getLogLevel(String level){
        if(level.equals(ALL.getLevel())){
            return ALL;
        }else if(level.equals(VERBOSE.getLevel())){
            return VERBOSE;
        }else if(level.equals(DEBUG.getLevel())){
            return DEBUG;
        }else if(level.equals(INFO.getLevel())){
            return  INFO;
        }else if(level.equals(WARNING.getLevel())){
            return WARNING;
        }else if(level.equals(ERROR.getLevel())){
            return ERROR;
        }else if(level.equals(SEVERE.getLevel())){
            return SEVERE;
        }else if(level.equals(EVENT.getLevel())){
            return EVENT;
        }
        return OFF;
    }
}
