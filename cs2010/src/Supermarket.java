import java.util.*;
import java.io.*;

// Matric number	: A0116631N
// Name here		: Eugene
// Collaborators	: CLRS, CS2010 Lab TA 

class Supermarket {
	private int N; // number of items in the supermarket.
	private int K; // the number of items that Steven has to buy
	private int[] shoppingList; // indices of items that Steven has to buy
	private int[][] T; // the complete weighted graph that measures the direct
						// walking time to go from one point to another point in
						// seconds

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------
	private int V; // number of vertices in the graph including the
					// source/cashier. V = N+1
	private int[][] D; // Distance Matrix
	private int[][] memo;
	private int shoppingMask;

	public Supermarket() {

	}

	void floydwarshall() {
		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					T[i][j] = Math.min(T[i][j], T[i][k] + T[k][j]);
				}
			}
		}
	}

	int DP_TSP(int u, int mask) {
		if (mask == (1 << K) - 1) { // base case: all vertex visited from u
			return T[u][0]; // return the base case
		}
		if (memo[u][mask] != -1) {
			return memo[u][mask];
		}
		memo[u][mask] = Integer.MAX_VALUE;
		for (int i = 0; i < K; i++) {
			if ((mask & (1 << i)) == 0) {
				int result1 = T[u][this.shoppingList[i]];
				int result2 = DP_TSP(this.shoppingList[i], mask | (1 << i));
				int distance = result1 + result2;
				memo[u][mask] = Math.min(
					memo[u][mask],
					distance);			
			}
		}
		return memo[u][mask];
	}
	
	int Query() {

		this.V = N + 1;
		this.memo = new int[V][1 << K];

		for (int[] m : this.memo) {
			Arrays.fill(m, -1);
		}

		// setup shopping mask: item not in the shopping list to be 1, item in
		// the shopping list to be 0
		this.shoppingMask = 0;
		for (int i = 0; i < K; i++) {
			this.shoppingMask |= (1 << i);
		}
		this.shoppingMask = ~this.shoppingMask & ((1 << V) - 1); // invert bits

		floydwarshall();
		
		int ans = DP_TSP(0, 0);
		return ans;
	}

	// You can add extra function if needed
	// --------------------------------------------

	void run() throws Exception {
		// do not alter this method to standardize the I/O speed (this is
		// already very fast)
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			// read the information of the complete graph with N+1 vertices
			N = sc.nextInt();
			K = sc.nextInt(); // K is the number of items to be bought

			shoppingList = new int[K];
			for (int i = 0; i < K; i++)
				shoppingList[i] = sc.nextInt();

			T = new int[N + 1][N + 1];
			for (int i = 0; i <= N; i++)
				for (int j = 0; j <= N; j++)
					T[i][j] = sc.nextInt();

			pw.println(Query());
		}

		pw.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		Supermarket ps7 = new Supermarket();
		ps7.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not
						// recommended
	BufferedInputStream bis;

	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		} catch (IOException ioe) {
			return -1;
		}
	}
}

class IntegerPair implements Comparable<IntegerPair> {
	int _first, _second;

	public IntegerPair(int f, int s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (this.first() != o.first())
			return this.first() - o.first();
		else
			return this.second() - o.second();
	}

	int first() {
		return _first;
	}

	int second() {
		return _second;
	}
}
