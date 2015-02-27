import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class SpaceshipMomentum {

	/***
	 * Default Constructor
	 */
	public SpaceshipMomentum() {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));

		int T, B;
		String V, M;
		T = sc.nextInt();

		for (int i = 1; i <= T; ++i) {
			B = sc.nextInt();
			sc.nextLine();
			V = sc.nextLine();
			M = sc.nextLine();

			String P = multiply(V, M, B);

			pw.write(trimZeros(P));
			pw.write("\n");
		}
		pw.close(); // do not forget to use this
		sc.close();
	}
	
	
	/***
	 * Multiplication of Two long numbers
	 * 
	 * @param a1 operand1 with any real value
	 * @param a2 operand2 with any real value
	 * @param radix or base
	 * @return result multiplication string 
	 */
	public String multiply(String a1, String a2, int radix) {

		int decimal_point = this.getDecimalPointPosition(a1) + this.getDecimalPointPosition(a2);

		a1 = this.reverseString(this.removeDecimalPoint(a1));
		a2 = this.reverseString(this.removeDecimalPoint(a2));

		String result = this.mult(a1.toCharArray(), a2.toCharArray(), radix);

		return this.reconstructResult(result, decimal_point);

	}
	
	/***
	 * Find a decimal point position.
	 * Return 0 if not found
	 * 
	 * @param str
	 * @return decimal point index
	 */
	private int getDecimalPointPosition(String str){
		int decimal_point_index = str.indexOf('.');
		int position = 0;
		if (decimal_point_index != -1) {
			position += str.substring(decimal_point_index).length() - 1;
		}
		return position;
	}
	
	/***
	 * Reverse String
	 * 
	 * @param str
	 * @return reversed string
	 */
	private String reverseString(String str){
		return new StringBuilder(str).reverse().toString();
	}
	
	/***
	 * Remove decimal character
	 * 
	 * @param str
	 * @return string without decimal character
	 */
	private String removeDecimalPoint(String str){
		return str.replace(".", "");
	}

	/***
	 * Reconstructing the decimal Precision and reversing the result.
	 * @param result
	 * @param decimal_point
	 * @return
	 */
	private String reconstructResult(String result, int decimal_point) {
		StringBuilder sb = new StringBuilder(result);
		sb.insert(decimal_point, ".");
		sb.reverse();
		return sb.toString();
	}
	
	/**
	 * Long Multiplication Algorithm
	 * 
	 * @param a1 operand1 with reverse order and without decimal point
	 * @param a2 operand2 with reverse order and without decimal point
	 * @param radix arithmetic base
	 * @return multiplication result string without decimal precision
	 */
	private String mult(char[] a1, char[] a2, int radix){
		int n = Math.max(a1.length, a2.length);
		int max_digit = a1.length + a2.length; // multiplied digit are not more then a1.length + a2.length digits. 
		
		int x = 0; // Carry Variable between each result digits
		
		char[] result = new char[max_digit]; // result digits

		// iterate for each of the result digits   
		for (int i = 0; i <= (max_digit - 1); i++) {
			
			// Do cross multiply for each operand1 and operand2 characters
			for (int j = Math.max(0, i + 1 - n); j <= Math.min(i, n - 1); j++) {
				// j: index of operand1
				// k: index of operand2
				int k = i - j;
				
				try {
					x = x + (parseDigit(a1[j]) * parseDigit(a2[k]));
				} catch (ArrayIndexOutOfBoundsException e) {
					// nothing to multiply so don't care and move on.
				}
			}
			result[i] = toDigit(x % radix); // Store the Result
			x = x / radix; // Carry x to the next digit.
		}
		
		return String.valueOf(result);
	}
	
	/**
	 * Use to trim leading and trailing zeros on a result string.
	 */
	private String trimZeros(String input) {
		int left = 0;
		int right = input.length() - 1;
		int fp = input.indexOf('.');
		if (fp == -1) {
			fp = input.length();
		}

		while (left < fp - 1) {
			if (input.charAt(left) != '0')
				break;
			left++;
		}

		while (right >= fp) {
			if (input.charAt(right) != '0') {
				if (input.charAt(right) == '.')
					right--;
				break;
			}
			right--;
		}

		if (left >= fp)
			return "0" + input.substring(left, right + 1);
		return input.substring(left, right + 1);
	}

	/**
	 * Convert digit to int (for reading)
	 */
	private int parseDigit(char c) {
		if (c <= '9') {
			return c - '0';
		}
		return c - 'A' + 10;
	}

	/**
	 * Convert int to digit. (for printing)
	 */
	private char toDigit(int digit) {
		if (digit <= 9) {
			return (char) (digit + '0');
		}
		return (char) (digit - 10 + 'A');
	}

	/***
	 * Main Function
	 * @param args
	 */
	public static void main(String[] args) {
		new SpaceshipMomentum();
	}
}
