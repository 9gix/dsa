package lab1.ex1;

import java.awt.Point;

public class OverlapRectangles {
    
    private static Point[] arrangeVertices(Point p1, Point p2){
        int btm_lft_x = (int) Math.min(p1.getX(), p2.getX());
        int btm_lft_y = (int) Math.min(p1.getY(), p2.getY());
        
        int top_rgt_x = (int) Math.max(p1.getX(), p2.getX());
        int top_rgt_y = (int) Math.max(p1.getY(), p2.getY());
        
        Point btm_lft_vertex = new Point(btm_lft_x, btm_lft_y);
        Point top_rgt_vertex = new Point(top_rgt_x, top_rgt_y);
        
        return new Point[]{btm_lft_vertex, top_rgt_vertex};
    }
    
    public static double overlapArea(Point rect1Vertex1, Point rect1Vertex2,
            Point rect2Vertex1, Point rect2Vertex2) {
        
        Point[] rect1 = arrangeVertices(rect1Vertex1, rect1Vertex2);
        Point[] rect2 = arrangeVertices(rect2Vertex1, rect2Vertex2);
        
        // finding "x" Component of the intersection boundary 
        double lft = Math.max(rect1[0].getX(), rect2[0].getX());
        double rgt = Math.min(rect1[1].getX(), rect2[1].getX());
        
        // Finding "y" component of the intersection boundary
        double btm = Math.max(rect1[0].getY(), rect2[0].getY());
        double top = Math.min(rect1[1].getY(), rect2[1].getY());
        
        double width = rgt - lft;
        double height = top - btm;
        double area = width * height;
        
        return Math.max(0, area);
    }
}
