import java.util.*;
import java.io.*;

// Matric No. : A0116631N
// Name			  : Eugene
// List of collaborators: 
// - Introduction to Algorithms (CLRS)
// - CSE 421: Intro to Algorithms (BFS, DFS, Articulation Points) [Larry Ruzzo]



class Graph{
	// Data Structure: Adjacent List
	
	private List<Vertex> vertices = new ArrayList<Vertex>();
	private static int time = 0;
	
	public void add(Vertex vertex){
		vertices.add(vertex);
	}
	
	public Vertex get(int id){
		return this.vertices.get(id);
	}

	/***
	 * Articulation point is a vertex whose removal disconnect the graph
	 *  
	 * @return a list of vertex that disconnect the graph upon removal.
	 */
	public List<Vertex> getArticulationPoint() {
		this.DFS();
		
		List<Vertex> articulationPoints = new ArrayList<Vertex>();
		for (Vertex u: this.vertices){
			if (u.isArticulationPoint()){
				articulationPoints.add(u);
			}
		}
		return articulationPoints;
  }
	
	/***
	 * Reset each of the vertex into its initial status
	 */
	private void resetGraph(){
		for (Vertex v: this.vertices){
			v.setParent(null);
			v.setVisited(false);
			v.setArticulationPoint(false);
		}
	}
	
	/***
	 * Perform Depth First Search
	 */
	private void DFS(){
		this.resetGraph();
		
		for (Vertex u: this.vertices){
			if (!u.isVisited()){
				this.DFSVisit(u);
			}
		}
		
	}
	
	/***
	 * Perform DFS to identify all articulation points.
	 * 
	 * @param u u is the source or the root of the DFS Tree.
	 */
	private void DFSVisit(Vertex u){
		time += 1;
		u.setDiscoveryTime(time);
		u.setShortestTime(time); 
		u.setVisited(true);
		int childrenCount = 0; // DFS Tree Children Counter
		
		for (Edge e:u.getAdjecencies()){
			Vertex v = e.getVertex();
			if (!v.isVisited()){ // unvisited Edges
				
				childrenCount += 1;
				
				// DFS Recurrence
				v.setParent(u);
				this.DFSVisit(v);
				
				
				// 
				u.setShortestTime(Math.min(u.getShortestTime(), v.getShortestTime()));
				
				if (u.getParent() == null){ 
					// Case 1: vertex u is the root of DFS Tree
					if (childrenCount > 1){
						// In DFS Tree, if a root have 2 or more children, then it is the articulation point
						u.setArticulationPoint(true);
					}
				} else {
					// Case 2: vertex u is not root of DFS Tree 
					if (v.getShortestTime() >= u.getDiscoveryTime()){
						// In DFS Tree, no descendant of u which have a tree edge to any of the ancestors of u,
						// however when there exists a back edge, the shortest vertex from the root will be the articulation point
	
						u.setArticulationPoint(true);
					}
				}
				
			} else { // Visited Edges
				if (v != u.getParent()) { // v is not the previous node
					
					// because Vertex v belongs to some ancestors of u
					// hence set the shortest time being the ancestors (v) only if it is smaller.
					u.setShortestTime(Math.min(u.getShortestTime(), v.getDiscoveryTime()));
				}
			}
		}
	}
}


/***
 * Vertex which implements some of the DFS behaviors
 */
class Vertex {

	// DFS sequence of being discovered
	private int discoveryTime;
	
	// shortest DFS sequence within the DFS subtree 
	// or the shortest DFS sequence being connected by a back edge.
	private int shortestTime;
	
	private int rating;
	private boolean isVisited;
	private Vertex parent;
	private boolean isArticulationPoint;
	private List<Edge> adjecencies;
	
	public Vertex(){
		this.isVisited = false;
		this.adjecencies = new ArrayList<Edge>();
	}
	
	public void setShortestTime(int time) {
	  this.shortestTime = time;
  }
	
	public int getShortestTime(){
		return this.shortestTime;
	}

	public void setArticulationPoint(boolean isArticulationPoint) {
		this.isArticulationPoint = isArticulationPoint;
  }
	
	public boolean isArticulationPoint(){
		return this.isArticulationPoint;
	}

	public boolean isVisited() {
	  return this.isVisited;
  }
	
	public void setVisited(boolean isVisited){
		this.isVisited = isVisited;
	}
	
	public void setParent(Vertex parent){
		this.parent = parent;
	}
	
	public Vertex getParent(){
		return this.parent;
	}

	public int getRating() {
	  return rating;
  }
	public void setRating(int rating) {
	  this.rating = rating;
  }
	public void addAdjacency(Vertex room2) {
		Edge edge = new Edge.Builder(room2).build();
	  this.adjecencies.add(edge);
  }
	
	
	public List<Edge> getAdjecencies() {
	  return this.adjecencies;
  }

	public int getDiscoveryTime() {
	  return discoveryTime;
  }

	public void setDiscoveryTime(int discoveryTime) {
	  this.discoveryTime = discoveryTime;
  }
	
}


/***
 * Edge class is a connection towards the target vertex.
 */
class Edge {
	private int weight;
	private Vertex vertex;
	
	/***
	 * Using Builder Pattern to create Edge object.
	 * It allows us to have a flexible optional parameters
	 */
	public static class Builder {
		private final Vertex vertex;
		private int weight = 1;
		
		/***
		 * A required vertex parameter to specify the connected vertex.
		 *   
		 * @param vertex this is the vertex to which the edge connecting to.
		 */
		public Builder(Vertex vertex){
			this.vertex = vertex;
		}
		
		/***
		 * Providing an optional weight parameter to edge builder
		 * 
		 * @param weight
		 * @return edge builder with the specified weight.
		 */
		public Builder weight(int weight){
			this.weight = weight;
			return this;
		}
		
		/***
		 * Create a new Edge object using this builder 
		 * 
		 * @return a new edge object
		 */
		public Edge build(){
			return new Edge(this);
		}
	}
	
	/***
	 * Constructor to create an Edge.
	 * 
	 * @param builder the Edge.Builder
	 */
	public Edge(Builder builder){
		this.setVertex(builder.vertex);
		this.setWeight(builder.weight);
	}

	public Vertex getVertex() {
	  return vertex;
  }

	private void setVertex(Vertex vertex) {
	  this.vertex = vertex;
  }

	public int getWeight() {
	  return weight;
  }

	public void setWeight(int weight) {
	  this.weight = weight;
  }
}


class HospitalTour {
  private Graph floorplan;
  
  int Query() {
  	List<Vertex> articulation_points = floorplan.getArticulationPoint();
  	Integer lowest = Integer.MAX_VALUE;
  	for (Vertex v: articulation_points){
  		int rating = v.getRating();
  		if (rating < lowest){
  			lowest = rating;
  		}
  	}
  	if (articulation_points.isEmpty()){
  		lowest = -1;
  	}
  	return lowest;
  }

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
    	floorplan = new Graph();
      br.readLine(); // ignore dummy blank line
      int V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      
      for (int i = 0; i < V; i++){
      	Vertex room = new Vertex(); 
      	room.setRating(Integer.parseInt(st.nextToken()));
      	floorplan.add(room);
      }

      // clear the graph and read in a new graph as Adjacency Matrix
      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
        	int j = Integer.parseInt(st.nextToken());
        	Vertex room1 = (Vertex) floorplan.get(i);
        	Vertex room2 = (Vertex) floorplan.get(j);
        	room1.addAdjacency(room2);        
        }
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalTour ps3 = new HospitalTour();
    ps3.run();
  }
}