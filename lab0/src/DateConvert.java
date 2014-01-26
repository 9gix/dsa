import java.util.Scanner;

// CS1020 (AY2012/3 Semester 2)
// Name: Eugene
// Matric. No.: A0116631N 
// Lab group: 7
/**
 * Program to convert 
 * from UK Date format (December 23, 2013) 
 * to US Date format (23 December 2013)
 * It will also display the Leap Year status.
 */

public class DateConvert {
    
    /**
     * Function to check if a given year a leap year.
     * 
     * @param year
     * @return boolean true if it is a leap year, else false.
     */
    public static boolean isLeapYear(int year){
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    }
    
    /**
     * Build a string for printing, it takes in 3 elements of array.
     * 
     * @param date_token {Day, Month, Year}
     * @return String us_date_str
     */
    public static String prettyPrintUSDate(String[] date_token){
        // date_token is array of 3 String 
        // {String DAY, String MONTH, String YEAR}
        return String.format("%s %s %s", (Object[]) date_token);
    }
    
    /**
     * Represent date using array of 3 String elements.
     * 
     * 
     * @param uk_date_str
     * @return array containing {Day, Month, Year}
     */
    public static String[] tokenizeDate(String uk_date_str){
        String date = uk_date_str.replaceAll(",", "");
        String[] date_token = date.split("\\s+");
        String month = date_token[0];
        String day = date_token[1];
        String year = date_token[2];
        return new String[]{day, month, year};
    }

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter date in UK format: ");
        String uk_date_str = scan.nextLine();
        
        String[] date_token = DateConvert.tokenizeDate(uk_date_str);
        
        String us_date_str = DateConvert.prettyPrintUSDate(date_token);
        
        System.out.print("Date in American format: ");
        System.out.println(us_date_str);
        
        int year = Integer.parseInt((String) date_token[2]);
        if (DateConvert.isLeapYear(year)){
            System.out.printf("%s is a leap year.", year);
        } else {
            System.out.printf("%s is not a leap year.", year);
        }
        
        
    }
}
