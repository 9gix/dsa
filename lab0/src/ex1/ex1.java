/**
 * Program: ex1, lab0
 * Program Main Execution
 */
package ex1;

import java.util.Scanner;

/**
 * @author Eugene
 *
 */
public class ex1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String raw_input = new String();
        
        // Program Loop, terminate when user input `0`
        while (!raw_input.equals("0")){
            
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter 3 Lengths: (Enter `0` to exit)");
            raw_input = sc.nextLine();

            String[] split_raw_input  = raw_input.split("\\s+");
    
            if (split_raw_input.length == 3){
                double a = 0,
                       b = 0,
                       c = 0;

                // Parsing Triangle Sides
                try {
                    a = (double) Double.parseDouble(split_raw_input[0]);
                    b = (double) Double.parseDouble(split_raw_input[1]);
                    c = (double) Double.parseDouble(split_raw_input[2]);                    
                } catch (NumberFormatException ex){
                    System.out.println("Please enter numeric length");
                }

                // Verify Valid Triangle Sides
                boolean valid_triangle = AreaOfTriangle.validTriangle(a, b, c);
                
                if (valid_triangle){
                    // Calculation of Triangle Area
                    double area = AreaOfTriangle.area(a, b, c);

                    System.out.printf("Area = %.2f\n", area);
                } else {                    
                    System.out.println("Invalid triangle!");
                }
            } else if (split_raw_input.length != 1 && 
                    split_raw_input[0] != "0"){
                
                // Termination Condition only if it is 1 argument and is `0` 
                // Otherwise it is an error.
                System.out.println("Please enter exactly 3 arguments");
            } 
        }
        System.out.println("Thank you for the support");
        System.exit(0);
    }
    // Sample Test Data
    // 12.5 7.8 19.2
    // 876.23 255.71 709.76
    // 10 50 30
}
