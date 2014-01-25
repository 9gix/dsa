package ex2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DateConvertTest {

    private String date1;
    private String date2;
    private int leap_year;
    private int non_leap_year;

    @Before
    public void setUp() throws Exception {
        this.date1 = "December 25, 2012";
        this.date2 = "March 7, 2013";
        
        this.leap_year = 2012;
        this.non_leap_year = 2013;
    }

    @Test
    public void testConvertToUS() {
        
        String us_date1 = DateConvert.convertToUS(this.date1);
        String us_date2 = DateConvert.convertToUS(this.date2);
        
        assertEquals(us_date1, "25 December 2012");
        assertEquals(us_date2, "7 March 2013");
    }
    
    @Test
    public void testLeapYear() {
        assertTrue(DateConvert.isLeapYear(this.leap_year));
        assertFalse(DateConvert.isLeapYear(this.non_leap_year));
    }

}
