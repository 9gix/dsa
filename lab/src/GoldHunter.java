// CS1020 (AY2013/4 Semester 2)
// Take-home lab #2
// Name: Eugene
// Matric. No.: A0116631N
// Lab group: 7
/**
 * This apps will provide a Gold Value mapping for each cell.
 * An increase of Gold value when the bomb is nearby.
 */

import java.util.*;

public class GoldHunter {

    private static final int GOLD_VALUE_INCREMENT = 1;
    private static final char BOMB_LABEL = '*';

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MyMap map = new MyMap(sc);

        // Avoid static class and create an instance of our program instead.
        GoldHunter gh = new GoldHunter();

        // Generate the GOLD VALUE
        map = gh.generateGoldMap(map);

        // Build the String to be displayed
        String gold_map = gh.buildGoldMapString(map);
        System.out.print(gold_map);
    }

    /**
     * Helper function as a String Builder, to prepare for the presentation.
     * 
     * @param map
     * @return String
     */
    private String buildGoldMapString(MyMap map) {
        String result = "";

        for (int row = 0; row < map.getNumRow(); row++) {

            for (int col = 0; col < map.getNumCol(); col++) {

                if (this.isBomb(row, col, map)) {
                    result += BOMB_LABEL;
                } else {
                    int gold_value = map.getGoldVal(row, col);
                    result += gold_value;
                }
            }
            result += "\n";
        }

        return result;
    }

    /**
     * Generate the entire map with the gold values
     * 
     * Gold values is depends on the neighboring bombs. The more bombs nearby,
     * the more gold it has.
     * 
     * @param map
     * @return map
     */
    private MyMap generateGoldMap(MyMap map) {
        int max_col = map.getNumCol();
        int max_row = map.getNumRow();

        // Iterate each row and column
        for (int row = 0; row < max_row; row++) {
            for (int col = 0; col < max_col; col++) {

                // Check whether the current row, col is a bomb
                if (this.isBomb(row, col, map)) {

                    // if location row, col is a bomb
                    // increase its neighbor gold value
                    map = this.increaseNeighborGoldValue(row, col, map);
                }
            }
        }

        return map;
    }

    /**
     * Increase the Gold Value of the neighbor
     * 
     * As long as its neighbor is not a bomb, the gold value will increase.
     * Please note however, this function does not validate whether the provided
     * row, col in the parameter is a bomb. Therefore, if the program need to
     * have this restriction, do the validation on a separate method before
     * calling this method
     * 
     * as long as you call this method is called the neighbour gold value
     * increase by the GOLD_VALUE_INCREMENT constant
     * 
     * @param row
     * @param col
     * @param map
     * @return map
     */
    private MyMap increaseNeighborGoldValue(int row, int col, MyMap map) {
        // Top neighbor with restriction of the top boundary
        int top = Math.max(0, row - 1);
        // Bottom neighbor with restriction of the bottom boundary
        int bottom = Math.min(row + 1, map.getNumRow() - 1);
        // Left neighbor with restriction of the left boundary
        int left = Math.max(0, col - 1);
        // Right neighbor with restriction of the right boundary
        int right = Math.min(col + 1, map.getNumCol() - 1);

        // Iterate Neighbors (r: iterating row, c: iterating column)
        for (int r = top; r <= bottom; r++) {
            for (int c = left; c <= right; c++) {
                // Exclude non-neighbor
                if (r != row || c != col) {
                    // if NOT a bomb
                    if (!this.isBomb(r, c, map)) {
                        // increase the Gold Value
                        int gold_value = map.getGoldVal(r, c);
                        gold_value += GOLD_VALUE_INCREMENT;
                        map.setGoldVal(r, c, gold_value);
                    }
                }
            }
        }
        return map;
    }

    /**
     * We don't have to worry about the bomb character anymore, This method
     * abstract the bomb character.
     * 
     * @param character
     * @return boolean
     */
    private boolean isBomb(int row, int col, MyMap map) {

        // Check if the current row, col label is a BOMB_LABEL
        return map.getLabel(row, col) == BOMB_LABEL;
    }
}
