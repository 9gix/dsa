package lab3.ex1;

// CS1020 (AY2013/4 Semester 2)
// Take-home lab #3
// Name: Eugene
// Matric. No.: A0116631N
// Lab group: 7
/**
 * Parking Manager Apps
 * 
 * Management of Occupied Parking Lots numbered from 1 to 1000.
 * 
 * Operations:
 * 1. ADD(start, size)
 * 2. REMOVE(start, size)
 */

import java.util.*;

public class ParkingManager {


    private static final int LAST_LOT = 1000;
    private LinkedList<Lots> parkingLots;

    public ParkingManager() {
        parkingLots = new LinkedList<Lots>();
    }

    public void addLots(int start, int size){
        
        // RANGE (start ... end)
        // `start` to `end` is the new lots that will be added
        int end = start + size;

        
        int parking_lot_size = parkingLots.size();
        
        // Parking Lot MAX Range
        if (end > LAST_LOT){
            return;
        }
        
        // ADD Operation cases 
        if (parkingLots.isEmpty()){
            parkingLots.add(new Lots(start, size));
        } else {
            
            // We need to find the right lots to insert the new lots to.
            // So we have to iterate the entire parking lot
            for (int i = 0; i < parking_lot_size; i++){
                
                // currently iterated on parkingLots
                Lots lot = parkingLots.get(i);
                
                // currently iterated parkingLots range [start..end]
                // `lot_start` and `lot_end` is the currently iterated lots
                // we will refer to these to know the range the lots
                int lot_start = lot.getLotStart();
                int lot_end = lot_start + lot.getLotSize();
                
                // true if currently iterated parkingLots is the last lots
                boolean is_last_lot = i == parking_lot_size - 1;
                
                if (lot_end < start && is_last_lot ){
                    // Case 1: The Last Parking Lots.
                    // e.g. Add [10..15] into [1..5]
                    // The currently iterated parking lots 
                    // is smaller then the new lots
                    // So Add new lot to the last parking Lots
                    parkingLots.addLast(new Lots(start, size));
                    return;
                } else if (lot_end < start){
                    // Case 2: Not in range, SKIP!!
                    continue; // Continue to next iteration
                } else if (lot_end == start){
                    // Case 3: Merge Head
                    // e.g. ADD [6..7] into [1..6] then [1..7] 
                    Lots next_lot = null;
                    try {
                        next_lot = parkingLots.get(i+1);
                    } catch (IndexOutOfBoundsException e) {
                        // Since this is the last element,
                        // then just set the size
                        lot.setLotSize(end-lot_start);
                    } finally {
                        if (next_lot != null){
                            int next_lot_start = next_lot.getLotStart();
                            int next_lot_end = next_lot_start + next_lot.getLotSize();
                            if (next_lot_start > end){
                                // Just merge one side
                                lot.setLotSize(end - lot_start);
                            } else if (next_lot_start == end) {
                                // Case 4: Merge BOTH HEAD & TAIL
                                // E.g. ADD [6..8] into [1..6], [8..9]
                                // RESULT: [1..9]
                                // Merge Both Side & remove the next lots
                                lot.setLotSize(next_lot_end - lot_start);
                                parkingLots.remove(next_lot);
                            } 
                        }
                    }
                    return;
                } else if (end > lot_start || 
                        (lot_start <= start && start <= lot_end)){
                    // Invalid Case: Lot is occupied or Overlapped
                    // E.g. ADD [6..7] into [1..9] or similar
                    return;
                } else if (end < lot_start){
                    
                    // Case 5: Insert Lots
                    // E.g. ADD [1..2] into [8..9]
                    // RESULT: [1..2], [8..9]
                    
                    // Inserting lots before the currently iterated lots
                    parkingLots.add(i, new Lots(start, size));
                    return;
                } else if (end == lot_start){
                    // Case 6: Merge Tail
                    // E.g. ADD [6..8] into [8..9]
                    // RESULT: [6..9]
                    lot.setLotStart(start);
                    lot.setLotSize(lot_end - start);
                    return;
                }       
            }
        }
    }
    
    private Lots _getOccupiedLot(int start, int size){
        int end = start + size;
        int parking_lot_size = parkingLots.size();
        for (int i = 0; i < parking_lot_size; i++) {
            Lots lot = parkingLots.get(i);
            int lot_start = lot.getLotStart();
            int lot_end = lot_start + lot.getLotSize();
            if (lot_start <= start && end <= lot_end) {
                return lot;
            } else if (lot_start > start){
                return null;
            }
        }
        return null;
    }

    // Add description of the method
    public void removeLots(int start, int size) {
        // Remove Lot from Start until End
        int end = start + size; 
        
        Lots occupied_lots = this._getOccupiedLot(start, size);
        
        if (occupied_lots == null) return;
        
        int occupied_lots_start = occupied_lots.getLotStart();
        int occupied_lots_end = occupied_lots_start + occupied_lots.getLotSize();
        
        // Only Remove Valid Lot Range
        if (start <= occupied_lots_end && end <= occupied_lots_end){

            // Case 1: Remove Lots in the middle of another Lots.
            if (occupied_lots_start < start && start < occupied_lots_end && occupied_lots_end != end){
                occupied_lots.setLotSize(start - occupied_lots_start);
                this.addLots(end, occupied_lots_end - end);
            } else if ((occupied_lots_start == start) && (occupied_lots_end == end)){
                // Case 2: Remove whole lots
                parkingLots.remove(occupied_lots);
            } else if (occupied_lots_end == end){
                // Case 3: Remove Lots in the next edge of another lots
                occupied_lots.setLotSize(start - occupied_lots_start);
            } else if (occupied_lots_start == start){
                // Case 4: Remove Lots in the previous edge of another lots
                occupied_lots.setLotStart(end);
                occupied_lots.setLotSize(occupied_lots_end - end);
            }
            
            
        }
        
        
    }

    // Returns the string representation of the linked list
    public String toString() {
        String str = "";
        Iterator itr = parkingLots.iterator();
        if (itr.hasNext())
            str += itr.next();
        while (itr.hasNext()) {
            str += "->" + itr.next();
        }
        return str;
    }

    public static void main(String[] args) {

        ParkingManager pm = new ParkingManager();
        Scanner sc = new Scanner(System.in);
        String op;
        int start, size;

        while (sc.hasNext()) {
            op = sc.next();
            start = sc.nextInt();
            size = sc.nextInt();
            // System.out.println(op + " " + start + " " + size);
            // For checking
            if (op.equals("ADD")) {
                pm.addLots(start, size);
            } else if (op.equals("REMOVE")) {
                pm.removeLots(start, size);
            }
            System.out.println(pm);
        }
    }
}
