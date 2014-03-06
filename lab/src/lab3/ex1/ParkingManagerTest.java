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
    
    @Test
    public void testRemoveLot(){
        parking_lot.addLots(100, 100);
        parking_lot.removeLots(150, 10);
        assertEquals("[100;50]->[160;40]", parking_lot.toString());
    }
    
    @Test
    public void testRemoveMoreLot(){
        parking_lot.addLots(110, 40);
        parking_lot.addLots(160, 30);
        parking_lot.removeLots(120, 8);
        assertEquals("[110;10]->[128;22]->[160;30]", parking_lot.toString());
    }
    
    @Test
    public void testRemoveLotInvalid(){
        parking_lot.addLots(100, 50);
        parking_lot.addLots(160, 40);
        parking_lot.removeLots(140, 50);
        assertEquals("[100;50]->[160;40]", parking_lot.toString());
    }
    
    @Test
    public void testRemoveLotPrevEdge(){
        parking_lot.addLots(100, 50);
        parking_lot.addLots(160, 40);
        parking_lot.removeLots(100, 10);
        assertEquals("[110;40]->[160;40]", parking_lot.toString());
    }
    
    @Test
    public void testRemoveLotNextEdge(){
        parking_lot.addLots(110, 40);
        parking_lot.addLots(160, 40);
        parking_lot.removeLots(190, 10);
        assertEquals("[110;40]->[160;30]", parking_lot.toString());
    }
    
    @Test
    public void testRemoveWholeLot(){
        parking_lot.addLots(110, 40);
        parking_lot.addLots(190, 10);
        parking_lot.addLots(10, 10);
        parking_lot.removeLots(110, 40);
        assertEquals("[10;10]->[190;10]", parking_lot.toString());
        parking_lot.removeLots(190, 10);
        assertEquals("[10;10]", parking_lot.toString());
        parking_lot.removeLots(10, 10);
        assertEquals("", parking_lot.toString());
    }
    
    @Test
    public void testAddEdgeCase1(){
        parking_lot.addLots(190, 10);
        parking_lot.addLots(300, 300);
        parking_lot.addLots(600, 12);
        assertEquals("[190;10]->[300;312]", parking_lot.toString());
    }
}
