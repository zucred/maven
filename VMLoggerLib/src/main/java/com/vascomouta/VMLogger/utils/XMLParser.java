package com.vascomouta.VMLogger.utils;


import android.content.Context;
import android.util.Xml;
import com.vascomouta.VMLogger.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is implement for parse Log Configuration from XML File.
 */
public class XMLParser {

    private static final String TAG = XMLParser.class.getSimpleName();

    /**
     * This implementation for read logger configuration from xml file from Assests folder.
     *
     * @param context
     * @return return configuration map
     */
    public static HashMap<Object, Object> readConfigurationFromXml(Context context){
        try {
            final String ns = null;
            InputStream is = context.getAssets().open(Log.LoggerInfoFile + ".xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, ns, "root");
            int eventType = parser.getEventType();
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("map")){
                            return readMap(parser);
                        }
                }
            }

        }catch (XmlPullParserException ex){
            android.util.Log.e(TAG, ex.getMessage());
        }catch (IOException e){
            android.util.Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * This implementation parse 'map' from xml.
     * @param parser
     * @return return Map of custom key and Values
     */
    private static HashMap<Object, Object> readMap(XmlPullParser parser){
        HashMap<Object, Object> map = new HashMap<>();
        try {
            parser.require(XmlPullParser.START_TAG, null, "map");
            String key = "";
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("key")) {
                    key = readKey(parser);
                }else if(name.equals("string")){
                    String value = readText(parser);
                    map.put(key, value);
                } else if (name.equals("array")) {
                    ArrayList<Object> values = readArray(parser);
                    map.put(key, values);
                } else if (name.equals("map")) {
                    HashMap<Object, Object> childMap = readMap(parser);
                    map.put(key, childMap);
                }
            }

        }catch (XmlPullParserException ex){
            android.util.Log.e(TAG, ex.getMessage());
        }catch (IOException e){
            android.util.Log.e(TAG, e.getMessage());
        }
        return map;
    }


    /**
     * This implemetation parse 'key' from xml file
     * @param parser
     * @return return String values of key
     */
    private static String readKey(XmlPullParser parser){
        try {
            parser.require(XmlPullParser.START_TAG, null, "key");
            String title = readText(parser);
            parser.require(XmlPullParser.END_TAG, null, "key");
            return title;
        }catch (XmlPullParserException ex){
            android.util.Log.e(TAG, ex.getMessage());
        }catch (IOException e){
            android.util.Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * This implementation parse 'array' from Xml file
     * @param parser
     * @return return Array of Custom Objects
     */
    private static ArrayList<Object> readArray(XmlPullParser parser){
        ArrayList<Object> list = new ArrayList<>();
        try {
            parser.require(XmlPullParser.START_TAG, null, "array");
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("string")) {
                    list.add(readText(parser));
                } else if (name.equals("array")) {
                    ArrayList<Object> values = readArray(parser);
                    list.add(values);
                } else if (name.equals("map")) {
                    HashMap<Object, Object> childMap = readMap(parser);
                    list.add(childMap);
                }
            }

        }catch (XmlPullParserException ex){
            android.util.Log.e(TAG, ex.getMessage());
        }catch (IOException e){
            android.util.Log.e(TAG, e.getMessage());
        }
        return list;
    }

    /**
     * This implementation parse 'string' values from Xml file
     * @param parser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
