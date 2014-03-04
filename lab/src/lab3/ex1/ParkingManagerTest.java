package lab3.ex1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParkingManagerTest {
    ParkingManager parking_lot;

    @Before
    public void setUp() throws Exception {
        parking_lot = new ParkingManager();
    }

    @Test
    public void testAddFirstLot() {
        parking_lot.addLots(12, 2);
        
        assertEquals("[12;2]", parking_lot.toString());
    }
    
    @Test
    public void testAddMultipleLot() {
        parking_lot.addLots(12, 2);
        parking_lot.addLots(2, 3);
        assertEquals("[2;3]->[12;2]", parking_lot.toString());
    }
    
    @Test
    public void testAddMultipleLotWithMergedOneSide() {
        parking_lot.addLots(12, 2);
        parking_lot.addLots(2, 3);
        parking_lot.addLots(8, 4);
        assertEquals("[2;3]->[8;6]", parking_lot.toString());
    }
    
    @Test
    public void testAddInvalidLot() {
        parking_lot.addLots(2, 3);
        parking_lot.addLots(8, 6);
        parking_lot.addLots(6, 3); // Invalid & Ignore
        assertEquals("[2;3]->[8;6]", parking_lot.toString());
    }
    
    @Test
    public void testAddMoreLotInbetween() {
        parking_lot.addLots(8, 6);
        parking_lot.addLots(2, 3);
        parking_lot.addLots(16,5);
        assertEquals("[2;3]->[8;6]->[16;5]", parking_lot.toString());
        parking_lot.addLots(6,1);
        assertEquals("[2;3]->[6;1]->[8;6]->[16;5]", parking_lot.toString());
    }
    
    @Test
    public void testAddMergeBothSide() {
        parking_lot.addLots(8, 6);
        parking_lot.addLots(2, 3);
        parking_lot.addLots(16,5); // [2;3]->[8;6]->[16;5]
        parking_lot.addLots(5,3);
        assertEquals("[2;12]->[16;5]", parking_lot.toString());
    }
}
