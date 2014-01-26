import java.awt.Point;
import java.util.Scanner;

// CS1020 (AY2013/4 Semester 2)
// Take-home lab #1
// Name: Eugene 
// Matric. No.: A0116631N
// Lab group: 7
/**
 * A program to compute the overlap area between two rectangles.
 */
public class OverlapRectangles {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 2 opposite vertices of 1st rectangle: ");
        Point rect1Vertex1 = new Point(sc.nextInt(), sc.nextInt());
        Point rect1Vertex2 = new Point(sc.nextInt(), sc.nextInt());

        System.out.print("Enter 2 opposite vertices of 2nd rectangle: ");
        Point rect2Vertex1 = new Point(sc.nextInt(), sc.nextInt());
        Point rect2Vertex2 = new Point(sc.nextInt(), sc.nextInt());

        double area = OverlapRectangles.overlapArea(rect1Vertex1, rect1Vertex2, rect2Vertex1, rect2Vertex2);
        printOverlapArea(area);
    }
    
    /**
     * This method will normalize the vertices, 
     * by using bottom left and top right as the point of interest
     * @param p1
     * @param p2
     * @return {array Point} Bottom Left Vertex and Top Right Vertex  
     */
    private static Point[] arrangeVertices(Point p1, Point p2){
        int btm_lft_x = (int) Math.min(p1.getX(), p2.getX());
        int btm_lft_y = (int) Math.min(p1.getY(), p2.getY());
        
        int top_rgt_x = (int) Math.max(p1.getX(), p2.getX());
        int top_rgt_y = (int) Math.max(p1.getY(), p2.getY());
        
        Point btm_lft_vertex = new Point(btm_lft_x, btm_lft_y);
        Point top_rgt_vertex = new Point(top_rgt_x, top_rgt_y);
        
        return new Point[]{btm_lft_vertex, top_rgt_vertex};
    }
    
    /**
     * This method will calculate the overlapArea between vertices 
     * @param rect1Vertex1 
     * @param rect1Vertex2
     * @param rect2Vertex1
     * @param rect2Vertex2
     * @return {double} area
     */
    private static double overlapArea(Point rect1Vertex1, Point rect1Vertex2,
            Point rect2Vertex1, Point rect2Vertex2) {
        
        Point[] rect1 = arrangeVertices(rect1Vertex1, rect1Vertex2);
        Point[] rect2 = arrangeVertices(rect2Vertex1, rect2Vertex2);
        
        // finding "x" Component of the intersection boundary 
        double lft = Math.max(rect1[0].getX(), rect2[0].getX());
        double rgt = Math.min(rect1[1].getX(), rect2[1].getX());
        
        // Finding "y" component of the intersection boundary
        double btm = Math.max(rect1[0].getY(), rect2[0].getY());
        double top = Math.min(rect1[1].getY(), rect2[1].getY());
        
        // Calculate the area of the intersection
        double width = rgt - lft;
        double height = top - btm;
        double area = width * height;
        
        return Math.max(0, area);
    }

    /**
     * This methods will print the answer of the overlap area
     * @param area
     */
    public static void printOverlapArea(double area){
        System.out.printf("Overlap area = %.0f", area);
    }

    // This method is provided for your testing.
    // To print the 2 opposite vertices of each of the 2 rectangles.
    public static void printAllVertices(Point r1V1, Point r1V2, Point r2V1,
            Point r2V2) {
        System.out.println("1st rectangle vertex 1: " + r1V1);
        System.out.println("1st rectangle vertex 2: " + r1V2);
        System.out.println("2nd rectangle vertex 1: " + r2V1);
        System.out.println("2nd rectangle vertex 2: " + r2V2);
    }

}
