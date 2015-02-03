import java.util.*;
import java.io.*;

// Matric No. 	: A0116631N
// Name					: Eugene
// Collaborators: Introduction to Algorithms (CLRS)  

class Adjecency {
	private Vertex vertex;
	private int weight;
	public Vertex getVertex() {
	  return vertex;
  }
	public void setVertex(Vertex vertex) {
	  this.vertex = vertex;
  }
	public int getWeight() {
	  return weight;
  }
	public void setWeight(int weight) {
	  this.weight = weight;
  }
}

class Vertex {	
	private ArrayList<Adjecency> adjacencies;
	private int key;
	private Vertex parent;
	private int id;
	private boolean isVisited;
	
	public Vertex (int label){
		this.id = label;
		this.adjacencies = new ArrayList<Adjecency>();
	}
	
	public void addAdjacency(Adjecency adj){
		this.adjacencies.add(adj);
	}
	
	public ArrayList<Adjecency> getAdjacencies(){
		return this.adjacencies;
	}

	public int getKey() {
	  return key;
  }

	public void setKey(int key) {
	  this.key = key;
  }

	public Vertex getParent() {
	  return parent;
  }

	public void setParent(Vertex parent) {
	  this.parent = parent;
  }
	
	@Override
	public String toString() {
	  // TODO Auto-generated method stub
	  return String.valueOf(this.id);
	}

	public void setVisited(boolean b) {
	  this.isVisited = b;
  }
	public boolean isVisited(){
		return this.isVisited;
	}
}

class PrimMST {
	private List<Vertex> vertex_list;
	private Comparator<Vertex> min_key_comparator;
	private int[][] min_weight_table;
	
	public PrimMST(List<Vertex> vertex_list, int[][] weight_table){
		this.vertex_list = vertex_list;
		this.min_weight_table = weight_table;
		min_key_comparator = new Comparator<Vertex>() {

			@Override
	    public int compare(Vertex v1, Vertex v2) {
				if (v1.getKey() > v2.getKey()) {
			  	return 1;
			  } else if (v1.getKey() < v2.getKey()){
			  	return -1;
			  } else {
			  	return 0;
			  }
	    }
		};
		
	}
	
	public int[][] getMinWeightTable(){
		return this.min_weight_table;
	}
	
	public void traverse() {
		
		for (Vertex v: this.vertex_list){
			v.setKey(Integer.MAX_VALUE);
			v.setParent(null);
		}
		
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(min_key_comparator);		
		queue.addAll(this.vertex_list);
		this.vertex_list.get(0).setKey(0);
		
		while (!queue.isEmpty()){
			Vertex u = queue.poll();
			for (Adjecency adj: u.getAdjacencies()){
				Vertex v = adj.getVertex();
				if (queue.contains(v)){
					int weight = this.min_weight_table[this.vertex_list.indexOf(u)][this.vertex_list.indexOf(v)];
					if (weight < v.getKey()){
						queue.remove(v);
						v.setParent(u);
						v.setKey(weight);
						queue.add(v);
					}
				}
			}
		}
		

  }
	private void resetGraph() {
		for (Vertex v : this.vertex_list) {
			v.setVisited(false);
		}
	}
	private boolean isSpanningTree(Vertex a, Vertex b){
		return a.getParent() == b || a == b.getParent();
	}
	
	void dfs(){
		for (Vertex v: this.vertex_list){
			this.resetGraph();
			this.dfsVisit(this.vertex_list.indexOf(v), v, 0);
		}
	}
	
	void dfsVisit(int origin, Vertex u, int accumulated_weight){
		
		u.setVisited(true);
		for (Adjecency adj: u.getAdjacencies()){
			Vertex v = adj.getVertex();
			if (!v.isVisited() && this.isSpanningTree(u, v)){
				int v_index = this.vertex_list.indexOf(v);
				this.min_weight_table[origin][v_index] = Math.max(accumulated_weight, adj.getWeight());
				dfsVisit(origin, v, this.min_weight_table[origin][v_index]);
			}
		}
	}
	
}

class OutForAWalk {
  private int n_vertices;
  private List < Vertex > vertex_list;
  private int[][] min_weight_table;

  public OutForAWalk() {
    
  }
  
  void PreProcess() {
  	// Initialize Variable
  	
  	
  	
  	
  	// traverse MST
  	PrimMST mst = new PrimMST(this.vertex_list, this.min_weight_table);
  	mst.traverse();
  	mst.dfs();
  	
  	// Populating the Min Weight Table
  	for (int i = 0; i < n_vertices; i++){
  		for (int j = 0; j < n_vertices; j++){  			
  			this.min_weight_table[i][j] = mst.getMinWeightTable()[i][j];
  		}
  	}
  	
  }

  int Query(int source, int destination) {
  	return this.min_weight_table[source][destination];
  }

  void run() throws Exception {
    // do not alter this method
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      this.n_vertices = sc.nextInt();
      this.min_weight_table = new int[n_vertices][n_vertices];
    	
      // clear the graph and read in a new graph as Adjacency List
      this.vertex_list = new ArrayList < Vertex >();
      for (int i = 0; i < this.n_vertices; i++){
      	this.vertex_list.add(new Vertex(i));
      }
      
      for (int i = 0; i < this.n_vertices; i++) {
      	Vertex vertex_v = this.vertex_list.get(i);
        int n_adj = sc.nextInt();
        while (n_adj-- > 0) {
          int vertex_u = sc.nextInt(), weight = sc.nextInt();
          this.min_weight_table[i][vertex_u] = weight;
          Adjecency adj = new Adjecency();
          adj.setWeight(weight);
          adj.setVertex(this.vertex_list.get(vertex_u));
          vertex_v.addAdjacency(adj);
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    OutForAWalk ps4 = new OutForAWalk();
    ps4.run();
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