package ex1;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class AreaOfTriangleTest {

    @Test
    public void testTriangeArea() {
        double smallArea = AreaOfTriangle.area(12.5, 7.8, 19.2);
        double largeArea = AreaOfTriangle.area(876.23, 255.71, 709.76);
        double invalidArea = AreaOfTriangle.area(10, 50, 30);
        org.junit.Assert.assertEquals("Small Value - Area Not Equal", String.format("%.2f", smallArea), "30.68");
        org.junit.Assert.assertEquals("Large Value - Area Not Equal", String.format("%.2f", largeArea), "75953.81");
        org.junit.Assert.assertEquals("Invalid Triangle - Area Not Equal", String.format("%.2f", invalidArea), "0.00");
    }
    
    @Test
    public void testValidTriangle(){
        boolean smallTriangle = AreaOfTriangle.validTriangle(12.5, 7.8, 19.2);
        boolean largeTriangle = AreaOfTriangle.validTriangle(876.23, 255.71, 709.76);
        boolean invalidTriangle = AreaOfTriangle.validTriangle(10, 50, 30);
        org.junit.Assert.assertTrue(smallTriangle);
        org.junit.Assert.assertTrue(largeTriangle);
        org.junit.Assert.assertFalse(invalidTriangle);
    }

}
