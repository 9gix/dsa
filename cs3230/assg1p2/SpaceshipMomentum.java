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

			String P = multiply_karatsuba(V, M, B);

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
    public String multiply_long(String a1, String a2, int radix) {

        int decimal_point = this.getDecimalPointPosition(a1) + this.getDecimalPointPosition(a2);

        a1 = this.reverseString(this.removeDecimalPoint(a1));
        a2 = this.reverseString(this.removeDecimalPoint(a2));

        String result = this.mult_long(a1.toCharArray(), a2.toCharArray(), radix);

        return this.reconstructResultReverse(result, decimal_point);

    }
    
    /**
     * Long Multiplication Algorithm
     * 
     * @param a1 operand1 with reverse order and without decimal point
     * @param a2 operand2 with reverse order and without decimal point
     * @param radix arithmetic base
     * @return multiplication result string without decimal precision
     */
    private String mult_long(char[] a1, char[] a2, int radix){
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
	private String reconstructResultReverse(String result, int decimal_point) {
		StringBuilder sb = new StringBuilder(result);
		sb.insert(decimal_point, ".");
		sb.reverse();
		return trimZeros(sb.toString());
	}
	
	private String reconstructResult(String result, int decimal_point){
	    StringBuilder sb = new StringBuilder(result);
	    sb.reverse();
	    for (int i = sb.length(); i < decimal_point; i++){
	        sb.append("0");
	    }
	    sb.insert(decimal_point, ".");
	    sb.append("0");
	    sb.reverse();
        return sb.toString();
	}
	
	   /***
     * Multiplication of Two long numbers
     * 
     * @param a1 operand1 with any real value
     * @param a2 operand2 with any real value
     * @param radix or base
     * @return result multiplication string 
     */
    public String multiply_karatsuba(String x, String y, int radix) {

        int decimal_point = this.getDecimalPointPosition(x) + this.getDecimalPointPosition(y);

        x = this.removeDecimalPoint(x);
        y = this.removeDecimalPoint(y);
        
        String result = this.karatsuba(x, y, radix);
        return this.reconstructResult(result, decimal_point);
        
    }
    public static String add(String x, String y, int radix) {
        StringBuilder sb = new StringBuilder();
        for (int i = x.length() - 1, j = y.length() - 1, carry = 0;
                (i >= 0 || j >= 0) || carry != 0;
                i--, j-- ){
            
            int a = 0, b = 0;

            if (i >= 0){
                a = parseDigit(x.charAt(i));
            }
            if (j >= 0){
                b = parseDigit(y.charAt(j));
            }

            int result = a + b + carry;
            
            if (result > radix - 1) {
                carry = 1;
                result -= radix;
            } else {
                carry = 0;
            }
            sb.append(toDigit(result));
        }
        return trimZeros(sb.reverse().toString());
    }
    
    public static String nthComplement(String x, int radix, int digit_count){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < x.length(); i++){
            sb.append(toDigit(radix - 1 - parseDigit(x.charAt(i))));
        }
        for (int i = sb.length(); i < digit_count; i++){
            sb.insert(0, toDigit(radix - 1));
        }
        return trimZeros(add(sb.toString(), "1", radix));
    }
    
    public static String sub(String x, String y, int radix) {
        StringBuilder sb = new StringBuilder(add(x, nthComplement(y, radix, x.length()), radix));
        // Drop Leading Number by 1
        char new_val = toDigit(parseDigit(sb.charAt(0)) - 1);
        sb.deleteCharAt(0);
        sb.insert(0, new_val);
        return trimZeros(sb.toString());
    }
    
    public static String shiftLeft(String x, int shift_amount){
        StringBuilder sb = new StringBuilder(x);
        for (int i = 0; i < shift_amount; i++){
            sb.append("0");
        }
        return sb.toString();
    }
        
    
    public String karatsuba(String x, String y, int radix){
        if ((x.length() == 1 && x.equals("0")) ||
                y.length() == 1 && y.equals("0")){
            return "0";
        }
        if (x.length() + y.length() <= 150 ){
            return multiply_long(x, y, radix);
        }
        
        
        int len = Math.max(x.length(), y.length());
        int mid = len/2;
        
        // Split x & y into two parts each
        int x_mid = Math.max(x.length() - mid, 0);
        int y_mid = Math.max(y.length() - mid, 0);
        String[] xs = {x.substring(x_mid), x.substring(0, x_mid)}; // low x (x0) x[0], high x (x1) x[1]
        String[] ys = {y.substring(y_mid), y.substring(0, y_mid)}; // low y (y0) y[0], high y (y1) y[1]
        if (xs[0].isEmpty()) {
            xs[0] = "0";
        }
        
        if (ys[0].isEmpty()) {
            ys[0] = "0";
        }
        
        
        String z0 = karatsuba(
                xs[0], // low x
                ys[0], // low y
                radix);
        
        String xsum = add(xs[0], xs[1], radix); // low x + high x
        String ysum = add(ys[0], ys[1], radix); // low y + high y
        String z1 = karatsuba(
                xsum, 
                ysum, 
                radix);
        String z2 = karatsuba(
                xs[1], // high x
                ys[1], // high y
                radix);
        
        String result = add(
                            add(
                                shiftLeft(z2, mid * 2),
                                shiftLeft(
                                        sub(sub(z1,z2, radix),z0, radix), 
                                        mid),
                                radix),
                            z0,
                            radix);
        return result;
    }
	
	/**
	 * Use to trim leading and trailing zeros on a result string.
	 */
	private static String trimZeros(String input) {
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
	private static int parseDigit(char c) {
		if (c <= '9') {
			return c - '0';
		}
		return c - 'A' + 10;
	}

	/**
	 * Convert int to digit. (for printing)
	 */
	private static char toDigit(int digit) {
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
