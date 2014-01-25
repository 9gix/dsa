package ex2;

import java.util.Scanner;

public class ex2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter date in UK format: ");
        String uk_date_str = scan.nextLine();
        
        // Convert Date to US
        String us_date_str = DateConvert.convertToUS(uk_date_str);
        
        // Checking Leap Year
        int year = DateConvert.getYear(uk_date_str);
        boolean is_leap_year = DateConvert.isLeapYear(year);
        
        System.out.printf("Date in American format: %s\n", us_date_str);
        String not_leap_msg = (is_leap_year) ? "" : "not ";
        System.out.printf("%d is %sa leap year.", year, not_leap_msg);       
    }
}
