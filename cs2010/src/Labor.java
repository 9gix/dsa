import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.io.*;

// Matric No. 	: A0116631N
// Name					: Eugene
// Collaborators: Introduction to Algorithms (CLRS)

class Labor {
	private Graph singapore_map;

	public Labor() {
	}

	@SuppressWarnings("serial")
	class Adjacency extends SimpleEntry<Vertex, Integer> {
		public Adjacency(Vertex vertex, Integer weight) {
			super(vertex, weight);
		}

		public Vertex getVertex() {
			return this.getKey();
		}

		public Integer getWeight() {
			return this.getValue();
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.valueOf(this.getVertex()) + "(w="
			    + String.valueOf(this.getWeight()) + ")";
		}
	}

	class Vertex {
		private int id;
		private int ssp_estimate;
		private List<Adjacency> adjacencies;

		public Vertex(int id) {
			this.setId(id);
			this.adjacencies = new ArrayList<Labor.Adjacency>();
		}

		public int getSSPEstimate() {
			return ssp_estimate;
		}

		public void setSSPEstimate(int ssp_estimate) {
			this.ssp_estimate = ssp_estimate;
		}

		public List<Adjacency> getAdjacencies() {
			return adjacencies;
		}

		public void addAdjacency(Vertex vertex, int weight) {
			this.adjacencies.add(new Adjacency(vertex, weight));
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.valueOf(this.getId());
		}
	}

	class Graph {
		private List<Vertex> vertices;
		Comparator<Vertex> vertex_ssp_estimate_comparator = new Comparator<Vertex>() {
			@Override
			public int compare(Vertex u, Vertex v) {
				if (u.getSSPEstimate() < v.getSSPEstimate()) {
					return -1;
				} else if (u.getSSPEstimate() > v.getSSPEstimate()) {
					return 1;
				} else {
					return 0;
				}
			}

		};

		@SuppressWarnings("serial")
		class SSPEstimatePair extends SimpleEntry<Integer, Vertex> implements
		    Comparable<SSPEstimatePair> {

			public SSPEstimatePair(Integer estimate, Vertex vertex) {
				super(estimate, vertex);
			}

			@Override
			public int compareTo(SSPEstimatePair p2) {
				int u = this.getKey();
				int v = p2.getKey();
				if (u < v) {
					return -1;
				} else if (u > v) {
					return 1;
				} else {
					return 0;
				}
			}

		}

		public Graph() {

			this.vertices = new ArrayList<Vertex>();
		}

		void dijkstra(Vertex source) {
			this.initSSP(source);

			PriorityQueue<SSPEstimatePair> queue = new PriorityQueue<SSPEstimatePair>();
			queue.add(new SSPEstimatePair(0, source));
			while (!queue.isEmpty()) {
				SSPEstimatePair est_pair = queue.poll();
				Vertex u = est_pair.getValue();
				if (u.getId() == 1) { // because hospital is the current minimum, thus
															// it is safe to ignore the rest in the queue.
					break;
				}

				if (est_pair.getKey() == u.getSSPEstimate()) {
					for (Adjacency adjacency : u.getAdjacencies()) {
						if (this.relax(u, adjacency)) {
							// Re-order the priority queue, upon SSP estimate changed
							queue.add(new SSPEstimatePair(adjacency.getVertex()
							    .getSSPEstimate(), adjacency.getVertex()));
						}
					}
				}
			}

		}

		private boolean relax(Vertex u, Adjacency adjacency) {
			Vertex v = adjacency.getVertex();
			int weight = adjacency.getWeight();
			if (v.getSSPEstimate() > u.getSSPEstimate() + weight) {
				v.setSSPEstimate(u.getSSPEstimate() + weight);
				return true;
			}
			return false;

		}

		private void initSSP(Vertex source) {
			for (Vertex v : this.vertices) {
				v.setSSPEstimate(1001);
			}
			source.setSSPEstimate(0);
		}

		private Vertex getVertex(int id) {
			return this.vertices.get(id);
		}

	}

	int Query() {
		this.singapore_map.dijkstra(this.singapore_map.getVertex(0));
		return this.singapore_map.getVertex(1).getSSPEstimate();
	}

	void run() throws Exception {
		// you can alter this method if you need to do so
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
		    System.out)));

		int n_test_cases = sc.nextInt(); // there will be several test cases
		while (n_test_cases-- > 0) {
			int n_vertices = sc.nextInt(); // Number of Vertices in the Graph

			this.singapore_map = new Graph();

			for (int i = 0; i < n_vertices; i++) {
				this.singapore_map.vertices.add(new Vertex(i)); // Instantiate all the
				                                                // Vertex (without edge
				                                                // yet)
			}

			for (int i = 0; i < n_vertices; i++) {
				Vertex v = this.singapore_map.getVertex(i);
				int n_adjacencies = sc.nextInt(); // number of adjacencies
				while (n_adjacencies-- > 0) {
					int adj_vertex_id = sc.nextInt(); // Adjacent Vertex
					int adj_weight = sc.nextInt(); // Weight of the Edge
					v.addAdjacency(this.singapore_map.getVertex(adj_vertex_id),
					    adj_weight);
				}
			}

			pr.println(Query());
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		Labor ps5 = new Labor();
		ps5.run();
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
