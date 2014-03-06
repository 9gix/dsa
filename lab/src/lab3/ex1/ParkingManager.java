package lab3.ex1;

// CS1020 (AY2013/4 Semester 2)
// Take-home lab #3
// Name: 
// Matric. No.: 
// Lab group: 
// Write the program description below.
// It is mandatory to write program description at the top of every program.
// Marks will be awarded for this in sit-in labs.
// Please remove this line and its preceding 3 lines.

import java.util.*;

public class ParkingManager {


    private static final int LAST_LOT = 1000;
    private LinkedList<Lots> parkingLots;

    public ParkingManager() {
        parkingLots = new LinkedList<Lots>();
    }

    // Add description of the method
    public void addLots(int start, int size) {
        Lots lot = new Lots(start, size);

        
        if (parkingLots.isEmpty()) {
            parkingLots.add(lot);
        } else {
            int next_occupied_lot_index = this._getNextOccupiedIndex(start);
            int prev_occupied_lot_index = this._getPrevOccupiedIndex(start);

            Lots next_occupied_lot = parkingLots.get(next_occupied_lot_index);
            Lots prev_occupied_lot = parkingLots.get(prev_occupied_lot_index);

            boolean is_merged_next_lot = false;
            boolean is_merged_prev_lot = false;

            // Merge to Next Lot
            if (lot.getLotStart() + lot.getLotSize() == next_occupied_lot
                    .getLotStart()) {
                next_occupied_lot.setLotStart(lot.getLotStart());
                next_occupied_lot.setLotSize(lot.getLotSize()
                        + next_occupied_lot.getLotSize());
                is_merged_next_lot = true;
            }

            // Merge to Prev Lot
            if (lot.getLotStart() == prev_occupied_lot.getLotStart()
                    + prev_occupied_lot.getLotSize()) {
                if (is_merged_next_lot) {
                    next_occupied_lot.setLotStart(prev_occupied_lot
                            .getLotStart());
                    next_occupied_lot.setLotSize(prev_occupied_lot.getLotSize()
                            + next_occupied_lot.getLotSize());
                    parkingLots.remove(prev_occupied_lot);
                } else {
                    prev_occupied_lot.setLotSize(lot.getLotSize()
                            + prev_occupied_lot.getLotSize());
                }
                is_merged_prev_lot = true;
            }

            // Add Lots
            if (!is_merged_prev_lot || !is_merged_next_lot) {
                if (prev_occupied_lot.getLotStart()
                        + prev_occupied_lot.getLotSize() < lot.getLotStart()
                        && lot.getLotStart() + lot.getLotSize() < next_occupied_lot
                                .getLotStart()) {
                    parkingLots.add(next_occupied_lot_index, lot);
                } else if (prev_occupied_lot.getLotStart() > lot.getLotStart()
                        + lot.getLotSize()) {
                    parkingLots.addFirst(lot);
                } else if (next_occupied_lot.getLotStart()
                        + next_occupied_lot.getLotSize() < lot.getLotStart()) {
                    parkingLots.addLast(lot);
                }
            }
        }
    }

    private int _getPrevOccupiedIndex(int start) {
        // TODO: Implement a divide and conquer to resolve the index faster.

        // Currently is using lookup from start of parking lot
        // Worst Case when the start lot is the last parking lot
        int parking_lot_size = parkingLots.size();
        for (int i = 0; i < parking_lot_size; i++) {
            Lots lot = parkingLots.get(i);
            if (start <= lot.getLotStart()) {
                return i <= 0 ? 0 : i - 1;
            } else {
                continue;
            }
        }
        return parking_lot_size == 0 ? 0 : parking_lot_size - 1;
    }

    private int _getNextOccupiedIndex(int start) {
        // TODO: Implement a divide and conquer to resolve the index faster.

        // Currently is using lookup from start of parking lot
        // Worst Case when the start lot is the last parking lot
        int parking_lot_size = parkingLots.size();
        for (int i = 0; i < parking_lot_size; i++) {
            Lots lot = parkingLots.get(i);
            if (start <= lot.getLotStart()) {
                return i;
            } else {
                continue;
            }
        }

        // next occupied index for empty parking lot is 0 instead of -1
        return parking_lot_size == 0 ? 0 : parking_lot_size - 1;
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
