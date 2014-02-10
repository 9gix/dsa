
// CS1020 (AY2013/4 Semester 2)
// Take-home lab #2
// Name: Eugene
// Matric. No.: A0116631N
// Lab group: 7
// The MyMap class consists of 2 two-dimensional arrays,
// the character grid representing the map, and an integer array
// that stores the number of neighbouring mines in each mine-free location.

import java.util.*;

class MyMap {
    private char[][] charMap; // Map containing the characters (labels)
    private int[][] intMap; // Map containing the gold values
    private int nRow; // number of rows
    private int nCol; // number of columns

    public MyMap() {
    };

    public MyMap(Scanner sc) {
        nRow = sc.nextInt();
        nCol = sc.nextInt();
        charMap = new char[nRow][nCol];
        intMap = new int[nRow][nCol];
        String line;
        for (int i = 0; i < nRow; i++) {
            line = sc.next();
            for (int j = 0; j < nCol; j++) {
                setLabel(i, j, line.charAt(j));
                setGoldVal(i, j, 0);
            }
        }
    }

    public int getNumRow() {
        return nRow;
    }

    public int getNumCol() {
        return nCol;
    }

    public char getLabel(int row, int col) {
        return charMap[row][col];
    }

    public int getGoldVal(int row, int col) {
        return intMap[row][col];
    }

    public void setLabel(int row, int col, char label) {
        charMap[row][col] = label;
    }

    public void setGoldVal(int row, int col, int val) {
        intMap[row][col] = val;
    }

    // To print the character map
    public void display() {
        String result = "";

        for (int row = 0; row < this.getNumRow(); row++) {

            char[] cols = this.charMap[row];

            for (int col = 0; col < this.getNumCol(); col++) {
                char mine_count = cols[col];
                // Not a good Practice (Less Readability), forgive me.
                // to improve, please use "if-else" statement
                result += (mine_count == -1) ? "*" : mine_count;
            }

            // If it is the last row don't add a new line,
            // because it break the test case.
            if (row < this.getNumRow() - 1) {
                result += "\n";
            }
        }

        // This is not a good practice. (It make testing harder,
        // due to side effect to the sysout)
        // To make this method much cleaner and testable,
        // it should return the string instead of printing it.
        // thus the methods should be called generateCharacterMap
        // and return the string
        // return result;
        System.out.print(result);
    }

    // Return true if row, col are within valid range,
    // otherwise return false.
    // Unfortunately, I don't know what in scenario to use this methods.
    public boolean indexWithinRange(int row, int col) {
        // Assume this.intMap is not empty array (or 0 length).
        int max_row = this.intMap.length;

        // Assuming that it is not a dynamic array size
        int max_col = this.intMap[0].length;

        // if row smaller than maximum row size
        // and col is smaller than maximum column size,
        // then it should be true otherwise false
        return row < max_row && col < max_col;

    }
}
