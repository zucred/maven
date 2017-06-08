package com.vascomouta.VMLogger.implementation.formatter;

import com.vascomouta.VMLogger.LogEntry;
import com.vascomouta.VMLogger.constant.PatternLogFormatterConstants;
import com.vascomouta.VMLogger.implementation.BaseLogFormatter;

import java.util.Map;
import java.util.regex.Pattern;

public class PatternLogFormatter  extends BaseLogFormatter{

    public static String defaultLogFormat = "%.30d [%thread] %-7p %-20.-20c - %m";

    public static String lengthPattern = "([-]?\\d{1,2}[.][-]?\\d{1,2}|[.][-]?\\d{1,2}|[-]?\\d{1,2})";

    public static String MDC = "%" + lengthPattern + "?" + "(X)";
    public static String identifier = "%" + lengthPattern + "?" + "(logger|lo|c)";
    public static String level = "%" + lengthPattern + "?" + "(level|le|p)";
    public static String date = "%" + lengthPattern + "?" + "(date|d)";
    public static String message = "%" + lengthPattern + "?" + "(message|msg|m)";

    public static String thread = "%" + lengthPattern + "?" + "(thread|t)";

    public static String caller = "%" + lengthPattern + "?" + "(Caller)";
    public static String function = "%" + lengthPattern + "?" + "(M|Method)";
    public static String file = "%" + lengthPattern + "?" + "(F|file)";
    public static String line = "%" + lengthPattern + "?" + "(L|line)";
    public static String lineSeparator = "%n";

    public static String grouping = "%" + lengthPattern + "[(].{1,}[)]";

    private static String[] patterns = {MDC,identifier,level,date,message,thread,caller,function,file,line,lineSeparator};
    //private static let patterns: [String] = [date]

    String pattern;

    /**
     * Patterns
     * c -> Logger Pattern
     * C -> CLass Name Pattern
     * d -> DatePattern
     * F -> FileLocation
     * l -> FullLocation
     * L -> lineLocation
     * m -> Message
     * n -> LineSeparator
     * M -> MethodLocation
     * p -> LevelPattern
     * r -> RelativeTime Pattern
     * t -> Thread Pattern
     * x -> NDC  Pattern
     * X -> Properties Pattern
     * sn -> SequenceNumber
     * throwable -> ThrowableInformation
     * i -> IntegerPattern
     */


    public PatternLogFormatter(){

    }

    /**
     *Initializes the DefaultLogFormatter using the given settings.
     * @param pattern
     */
    public PatternLogFormatter(String pattern){
        this.pattern = pattern;
    }

    @Override
    public void init(Map<String, Object> configuration) {
        super.init(configuration);
        String pattern = (String)configuration.get(PatternLogFormatterConstants.Pattern);
                if(pattern == null){
                    return;
                }
        new PatternLogFormatter(pattern);
    }

    /**
     *
     * @param logEntry
     * @param message
     * @return
     */
    @Override
    public String formatLogEntry(LogEntry logEntry, String message) {
        super.formatLogEntry(logEntry, message);
        String resultString = pattern;
        Pattern regex = Pattern.compile(PatternLogFormatter.grouping);
        /*if (regex != null) {
            Matcher matcher = regex.matcher(resultString);
            String content = resultString.substring(matcher.start());
            String[] range = content.split("(");
            int replacementTange = content.indexOf(range[0]);
            String subPattern = content.substring(replacementTange);
            subPattern = patternReplacement(logEntry, message, subPattern);
            subPattern = formatSpecifiers(content, subPattern);
           // resultString = resultString.replaceAll(matcher.start(), subPattern);
        }*/
        return  patternReplacement(logEntry, message, resultString);

    }



    public String formatSpecifiers(String expression, String replacement) {
        String newReplacement = replacement;
        Pattern regex = Pattern.compile(PatternLogFormatter.lengthPattern);
        /*if (regex != null) {
            Matcher matches = regex.matches(expression, replacement);
            if(matches.groupCount() > 0) {
                int min;
                int max;
                String specifier = expression.substring(matches[0].range)
                String[] values = specifier.components(separatedBy: ".")
            }*/
        return null;
    }


    public String patternReplacement(LogEntry entry, String message, String pattern){
        int offset = 0;
       /* i orderMatches = [:]
        String details = pattern;
        for(String pat : PatternLogFormatter.patterns) {
            Pattern regex = Pattern.compile(pat);
            if(regex != null){
                let matches = regex.matches(in: details, options:[], range: NSMakeRange(0, pattern.characters.count))
                for match in matches {
                orderMatches[match.range.location] = match
            }
            }
        }*/
       return "";

    }



    public static String getCaller(LogEntry entry){
        String caller = "";
        caller +=  entry.callingFunction ;
        caller += "(" + entry.callingFileLine + ":";
        caller +=  entry.callingFileLine + ")";
        return caller;
    }




}
