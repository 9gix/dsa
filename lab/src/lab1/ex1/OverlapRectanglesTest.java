package lab1.ex1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class OverlapRectanglesTest {

    @Parameters
    public static Collection<Object[]> getTestParameters(){
        return Arrays.asList(new Object[][]{
                // expected, rectangle1, rectangle2
                {24, "3 -1 15 6", "19 2 9 10"},
                {24, "15 6 3 -1", "9 2 19 10"},
                {0, "-5 5 -1 1", "1 2 11 12"},
                {10, "-2 -5 8 0", "-10 -9 0 4"},
                {200, "70 60 50 0", "20 20 100 10"}
        });
    }
    
    private int expected;
    private Point r1p1;
    private Point r1p2;
    private Point r2p1;
    private Point r2p2;
    
    public OverlapRectanglesTest(int expected, String rect1, String rect2){
        this.expected = expected;
        String[] rect1_raw_input = rect1.split("\\s+");
        String[] rect2_raw_input = rect2.split("\\s+");
        
        this.r1p1 = new Point((int)Integer.parseInt(rect1_raw_input[0]), 
                Integer.parseInt(rect1_raw_input[1]));
        this.r1p2 = new Point((int)Integer.parseInt(rect1_raw_input[2]), 
                Integer.parseInt(rect1_raw_input[3]));
        this.r2p1 = new Point((int)Integer.parseInt(rect2_raw_input[0]), 
                Integer.parseInt(rect2_raw_input[1]));
        this.r2p2 = new Point((int)Integer.parseInt(rect2_raw_input[2]), 
                Integer.parseInt(rect2_raw_input[3]));
    }
    
    @Test
    public void testOverlapArea(){
        int area = (int) OverlapRectangles.overlapArea(
                this.r1p1, this.r1p2, this.r2p1, this.r2p2);
        assertEquals(this.expected, area);
    }
}
