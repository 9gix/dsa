package ps6;

import java.io.*;
import java.util.*;

// Matric No. 		: A0116631N
// Name						: Eugene
// Collaborators	: Introduction to Algorithms by CLRS

class Caesarean {
  private int V; // number of vertices in the graph (steps of a Caesarean section surgery)
  private int E; // number of edges in the graph (dependency information between various steps of a Caesarean section surgery)
	private Vector<Vector<IntegerPair>> adjacent_list;
	private boolean[] visited;
	private ArrayList<Integer> topological_sorted;
	private int[] pathEstimate;

  public Caesarean() { }

  void dfs(int u){
  	this.visited[u] = true;
  	for (IntegerPair v: this.adjacent_list.get(u)){
	  	if (this.visited[v.first()] == false){
	  		this.dfs(v.first());
	  	}
  	}
  	topological_sorted.add(u);
  }
  
  void topologicalSort(){
  	this.topological_sorted = new ArrayList<Integer>();
    this.visited = new boolean[V];
    Arrays.fill(this.visited, Boolean.FALSE);
    dfs(0);
    Collections.reverse(this.topological_sorted);
  }
  
  void initSSP(int source){
  	this.pathEstimate = new int[V];
  	Arrays.fill(this.pathEstimate, Integer.MAX_VALUE);
  	this.pathEstimate[source] = 0;
  }
  
  boolean relax(int u, int v, int weight){
  	int distance_from_u = this.pathEstimate[u] + weight;
  	if (this.pathEstimate[v] > distance_from_u){
  		this.pathEstimate[v] = distance_from_u;
  		return true;
  	}
  	return false;
  }
  
  void bellmanFordSorted(int source){
  	this.initSSP(source);
  	for (int u: this.topological_sorted){
  		for (IntegerPair v_pair: this.adjacent_list.get(u)){
  			this.relax(u, v_pair.first(), v_pair.second());
  		}
  	}
  }
  
  int min(int[] xs){
  	int min = Integer.MAX_VALUE;
  	for (int i: xs){
  		if (i < min){
  			min = i;
  		}
  	}
  	return min;
  }
  
  int dagShortestPath(int source){
  	this.topologicalSort();
  	bellmanFordSorted(source);
		return this.pathEstimate[V-1];
  }
  
  void negateWeight(){
  	/**
  	 * Negating all Weight (positive to negative or negative to positive)
  	 */
  	for (int i = 0; i < V; i++){
  		for (IntegerPair v: this.adjacent_list.get(i)){
  			v.setSecond(- v.second() );
  		}
  	}
  }
  int dagLongestPath(int source){
  	this.topologicalSort();
  	this.negateWeight(); // convert all edge from +ve weight to -ve weight
  	bellmanFordSorted(source);
		return -this.pathEstimate[V-1]; // convert -ve estimate to +ve estimate 
  }

  int Query() {
    return this.dagLongestPath(0);
  }

  void run() throws Exception {
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt(); E = sc.nextInt(); // read V and then E

      adjacent_list = new Vector <Vector<IntegerPair>> ();
      int[] estT = new int[V];
      for (int i = 0; i < V; i++){
        estT[i] = sc.nextInt();
        adjacent_list.add(new Vector<IntegerPair>());
      }

      for (int i = 0; i < E; i++){
      	int u = sc.nextInt(); int v = sc.nextInt();
      	adjacent_list.get(u).add(new IntegerPair(v, estT[u]));
      }

      pr.println(Query());
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Caesarean ps6 = new Caesarean();
    ps6.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
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
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}



class IntegerPair implements Comparable<IntegerPair> {
  private int _first, _second;

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
  
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return String.format("(%s, %s)", this.first(), this.second());
  }

  public int first() { return _first; }
  public int second() { return _second; }
  public void setSecond( int value ) { this._second = value ; }
}
