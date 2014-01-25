package ex2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConvert {
    
    /** 
     * Simple pattern Matching. 
     * 
     * @param date
     * @return String[] {String day, String month, String year}
     */
    @Deprecated
    private static String[] simpleTokenizeDate(String date){
        date = date.replaceAll(",", "");
        String[] date_token = date.split("\\s+");
        String month = date_token[0];
        String day = date_token[1];
        String year = date_token[2];
        return new String[]{day, month, year};
    }
    
    /** 
     * Improved date pattern Matching
     * 
     * @param date
     * @return String[] {String day, String month, String year}
     */
    private static String[] tokenizeDate(String date){
        Pattern uk_date_pattern = Pattern.compile(
                // Beginning with Month - followed by whitespace
                "^(?<MONTH>\\w+)\\s+"
                // matching 1 or 2 digit of day followed by a comma separator -
                // with or without space
                + "(?<DAY>\\d{1,2}),\\s*"
                // ending with 4 digit year
                + "(?<YEAR>\\d{4})$"); // Match the year
        Matcher m = uk_date_pattern.matcher(date);
        
        String day = null,
               month = null,
               year = null;
        while (m.find()){
            day = m.group("DAY");
            month = m.group("MONTH");
            year = m.group("YEAR");
        }
        return new String[]{day, month, year};
    }

    /**
     * Converting UK Date format (December 23, 2013) 
     * into US Date Format (23 December 2013)
     * 
     * @param uk_date_str
     * @return String us_date_formatted
     */
    public static String convertToUS(String uk_date_str) {
        Object[] us_date = tokenizeDate(uk_date_str);
        return String.format("%s %s %s", us_date);
    }
    
    public static boolean isLeapYear(int year){
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    }
    
    /**
     * Helper Methods to get the year part of a date string
     * 
     * @param uk_date_str
     * @return int year
     */
    protected static int getYear(String uk_date_str){
        String[] us_date = tokenizeDate(uk_date_str);
        return Integer.parseInt((String) us_date[2]); 
    }
}
