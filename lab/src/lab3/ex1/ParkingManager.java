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
	private LinkedList <Lots> parkingLots;

	public ParkingManager() {
		parkingLots = new LinkedList<Lots>();
	}

	// Add description of the method
	public void addLots(int start, int size) {
		// Fill in the code


	}

	// Add description of the method
	public void removeLots(int start, int size) {
		// Fill in the code


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
			// System.out.println(op + " " + start + " " + size); // For checking
			if (op.equals("ADD")) {
				pm.addLots(start, size);
			}
			else if (op.equals("REMOVE")) {
				pm.removeLots(start, size);
			}
			System.out.println(pm); 
		}
	}
}

