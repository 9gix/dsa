/**
 * Triangle Class.
 * Used for calculation of triangle area and perimeter or
 * checking the validity of a triangle. 
 */
package ex1;

/**
 * @author Eugene
 */
public class AreaOfTriangle {

    public static double perimeter(double a, double b, double c){
        return a + b + c;
    }
    
    public static boolean validTriangle(double a, double b, double c){
        if (a < (b + c) && b < (a + c) && c < (a + b)){
            return true;
        } else {
            return false;
        }
    }

    public static double area(double a, double b, double c) {
        if (AreaOfTriangle.validTriangle(a, b, c)){
            double half_perimeter = AreaOfTriangle.perimeter(a, b, c) / 2;
            double result = Math.sqrt(half_perimeter * (half_perimeter - a)
                    * (half_perimeter - b) * (half_perimeter - c));
            return result;
        } else {
            return 0;         
        }
    }
}
