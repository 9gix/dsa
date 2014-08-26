
//CS1020 (AY2013/4 Semester 2)
//Name: Eugene
//Matric. No.: A0116631N
//Lab group: 7
/**
 * Class to Calculate the area of a valid triangle. 
 * It can be used to check the validity of a triangle too)
 */
import java.util.*;

public class AreaOfTriangle {

    /**
     * Calculate the Perimeter of the Triangle
     * 
     */
    public static double perimeter(double a, double b, double c) {
        return a + b + c;
    }

    /**
     * Check the validity of a triangle
     */
    public static boolean validTriangle(double a, double b, double c) {
        if (a < (b + c) && b < (a + c) && c < (a + b)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate the area of a triangle given all the length of the sides.
     * 
     */
    public static double area(double a, double b, double c) {
        if (AreaOfTriangle.validTriangle(a, b, c)) {
            double half_perimeter = AreaOfTriangle.perimeter(a, b, c) / 2;
            double result = Math.sqrt(half_perimeter * (half_perimeter - a)
                    * (half_perimeter - b) * (half_perimeter - c));
            return result;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        double a, b, c; // lengths of sides of triangle
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 3 lengths: ");
        String raw_input = sc.nextLine();

        String[] split_raw_input = raw_input.split("\\s+");

        a = (double) Double.parseDouble(split_raw_input[0]);
        b = (double) Double.parseDouble(split_raw_input[1]);
        c = (double) Double.parseDouble(split_raw_input[2]);

        boolean triangle_validity = AreaOfTriangle.validTriangle(a, b, c);

        if (triangle_validity) {
            double area = AreaOfTriangle.area(a, b, c);

            System.out.printf("Area = %.2f\n", area);
        } else {
            System.out.println("Invalid triangle!");
        }
    }
}
