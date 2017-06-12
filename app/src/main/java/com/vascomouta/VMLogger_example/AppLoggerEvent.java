package com.vascomouta.VMLogger_example;

import android.support.annotation.NonNull;
import java.util.HashMap;

public class AppLoggerEvent {



    public static String GROUP =  "Group";
    public static String TYPE = "Type";
    public static String PARAMS = "Params";

    public static String ERROR = "Error";

    public static String EVENT = "Event";
    public static String LOG = "Log";
    public static String UI = "UI";
    public static String UI_SCREENDISPLAY = "Display";
    public static String UI_SCREENDISPLAYDURATION = "DisplayDuration";

    public static String NAME = "name";
    public static String LABEL = "label";
    public static String VALUE = "value";

    public static String CLASS = "class";


    private HashMap<String, Object> requestValues = new HashMap<>();

    public static AppLoggerEvent createViewEvent(String name){
        return createEvent(AppLoggerEvent.UI, UI_SCREENDISPLAY,  name, null);
    }

    public static AppLoggerEvent createViewEvent(String name, HashMap<String, Object> params) {
        AppLoggerEvent appEvent = createEvent(AppLoggerEvent.UI,  UI_SCREENDISPLAY, params);
        appEvent.set(name, AppLoggerEvent.LABEL);
        return appEvent;
    }

    public static AppLoggerEvent createViewDurationEvent(String name,double duration) {
        return createEvent(AppLoggerEvent.UI,  UI_SCREENDISPLAYDURATION,  name, String.valueOf(duration));
    }

    public static AppLoggerEvent createViewDurationEvent(String name, double duration,HashMap<String, Object> params) {
        AppLoggerEvent appEvent = createEvent(AppLoggerEvent.UI, UI_SCREENDISPLAYDURATION, params);
        appEvent.set(name, AppLoggerEvent.LABEL);
        appEvent.set(String.valueOf(duration) , AppLoggerEvent.VALUE);
        return appEvent;
    }


    /**
     *  Returns a GAIDictionaryBuilder object with parameters specific to an event hit.
     * @param category <#category description#>
     * @param action  <#action description#>
     * @param label   <#label description#>
     * @param value   <#value description#>
     * @return
     */
    public static AppLoggerEvent createEvent(String category, String action,String label, String value){
        return new AppLoggerEvent(category, action, label, value);
    }

    public static AppLoggerEvent createEvent(String group,String type,HashMap<String, Object> params) {
        return new AppLoggerEvent(group, type, params);
    }

    public  AppLoggerEvent(){
        super();
    }


    public AppLoggerEvent(String group, String type,HashMap<String, Object> params) {
        super();
        requestValues.put(AppLoggerEvent.GROUP, group);
        requestValues.put(AppLoggerEvent.TYPE, type);
        setAllParams(params);
    }

    public AppLoggerEvent(String category,String action, String label,@NonNull String value) {
        super();
        requestValues.put(AppLoggerEvent.GROUP, category);
        requestValues.put(AppLoggerEvent.TYPE, action);
        if(label != null){
            set(label,  AppLoggerEvent.LABEL);
        }

        if(value != null) {
            set(value,  AppLoggerEvent.VALUE);
        }
    }


    public AppLoggerEvent set(String value,String forKey) {
        HashMap<String, Object> paramDic = (HashMap<String, Object>) requestValues.get(AppLoggerEvent.PARAMS);

        if(paramDic == null){
            return null;
        }
        paramDic.put(forKey, value);
        setAllParams(paramDic);
        return this;
    }

    /*
     * Copies all the name-value pairs from params into this object, ignoring any
     * keys that are not String and any values that are neither String or
     * null.
     */
    public AppLoggerEvent setAll(HashMap<String, Object> values) {
        requestValues = values;
        return this;
    }

    /*
     * Copies all the name-value pairs from params into this object, ignoring any
     * keys that are not String and any values that are neither String or
     * null.
     */
    public AppLoggerEvent setAllParams(HashMap<String, Object> params){
        requestValues.put(AppLoggerEvent.PARAMS, params);
        return this;
    }


    /*
    * Returns the value for the input parameter paramName, or null if paramName
    * is not present.
    */
    public String get(String paramName){
        String value = (String) requestValues.get(paramName);
        if(value == null){
            HashMap<String, Object> paramDic = (HashMap<String, Object>) requestValues.get(AppLoggerEvent.PARAMS);
            if(paramDic == null){
                return null;
            }
            String paramvalue = (String)paramDic.get(paramName);
            if(paramvalue == null){
                return  null;
            }
            return paramvalue;
        }
        return value;
    }

    /*
     * Return an Map object with all the parameters set in this
     */
    public HashMap<String, Object> build(){
        return requestValues;
    }






}
