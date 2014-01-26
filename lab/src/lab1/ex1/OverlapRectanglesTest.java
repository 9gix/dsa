package lab1.ex1;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class OverlapRectanglesTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testOverlapArea() {
        // Input 1:
        // 3 -1 15 6
        // 19 2 9 10
        int area1 = (int) OverlapRectangles.overlapArea(
                new Point(3, -1), new Point(15, 6),
                new Point(19, 2), new Point(9, 10));
        // Output 1: 24
        assertEquals(24, area1);
        
        // Input 2:
        // 15 6 3 -1
        // 9 2 19 10
        int area2 = (int) OverlapRectangles.overlapArea(
                new Point(15, 6), new Point(3, -1),
                new Point(9, 2), new Point(19, 10));
        // Out: 24
        assertEquals(24, area2);
        // Input 3:
        // -5 5 -1 1
        // 1 2 11 12
        int area3 = (int) OverlapRectangles.overlapArea(
                new Point(-5, 5), new Point(-1, 1),
                new Point(1, 2), new Point(11, 12));
        // output 3: 0
        assertEquals(0, area3);
        
        
        // input 4:
        // -2 -5 8 0
        // -10 -9 0 4
        int area4 = (int) OverlapRectangles.overlapArea(
                new Point(-2, -5), new Point(8, 0),
                new Point(-10, -9), new Point(0, 4));
        // output 4: 10
        assertEquals(10, area4);
        
        // input 5:
        // 70 60 50 0
        // 20 20 100 10
        int area = (int) OverlapRectangles.overlapArea(
                new Point(70, 60), new Point(50, 0),
                new Point(20, 20), new Point(100, 10));
        // output 5: 200
        assertEquals(200, area);
    }

}
