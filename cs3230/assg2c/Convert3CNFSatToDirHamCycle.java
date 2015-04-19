import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
abstract class Edge {
    
}

class Vertex {
    private String label;
    
    public Vertex(String label){
        this.setLabel(label);
    }
    
    @Override
    public boolean equals(Object obj) {
        Vertex vert = (Vertex) obj;
        return this.label.equals(vert.label);
    }
    
    @Override
    public int hashCode() {
        return label.hashCode();
        
    }
    
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return this.label;
    }
    
}

class DirectedEdge extends Edge{
    private Vertex fromVertex;
    private Vertex toVertex;
    public DirectedEdge(Vertex fromVertex, Vertex toVertex){
        this.setFromVertex(fromVertex);
        this.setToVertex(toVertex);
    }
    
    public DirectedEdge(String u, String v) {
        this(new Vertex(u), new Vertex(v));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;            
        }
        
        if (!(obj instanceof Edge)){
            return false;
        }
        
        return getFromVertex().equals(((DirectedEdge) obj).getFromVertex()) &&
                getToVertex().equals(((DirectedEdge) obj).getToVertex());
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + this.getFromVertex().hashCode();
        result = result * 31 + this.getToVertex().hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getFromVertex().toString() + " " + this.getToVertex().toString();
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }
}

class Graph {
    
    // Vertex List
    private Map<String, Vertex> vertexMap;
    // Edge Set DataStructure
    private Set<Edge> edgeSet;

    public Graph(){
        setVertexMap(new HashMap<String, Vertex>());
        setEdgeSet(new HashSet<Edge>());
    }
    
    public Vertex addVertex(String u){
        Vertex vert = new Vertex(u);
        getVertexMap().put(vert.getLabel(), vert);
        return vert;
    }
    
    public void addDirectedEdge(String u, String v){
        Vertex uVert = getOrCreateVertex(u);
        Vertex vVert = getOrCreateVertex(v);
        getEdgeSet().add(new DirectedEdge(uVert, vVert));
    }
    
    public void add2WayDirectedEdge(String u, String v){
        addDirectedEdge(u, v);
        addDirectedEdge(v, u);
    }
    public Vertex getOrCreateVertex(String label){
        Vertex u = getVertexMap().get(label);
        
        if (u == null){
            return addVertex(label);
        } else {
            return u;
        }
        
    }
    public Vertex getVertex(String label){
        return getVertexMap().get(label);
    }

    public Map<String, Vertex> getVertexMap() {
        return vertexMap;
    }

    public void setVertexMap(Map<String, Vertex> vertexMap) {
        this.vertexMap = vertexMap;
    }
    
    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public void setEdgeSet(Set<Edge> edgeSet) {
        this.edgeSet = edgeSet;
    }
    
    
    public String getEdgeSetString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVertexMap().size() + " " + getEdgeSet().size() + "\n");
        
        SortedSet<Edge> sortedEdgeSet = new TreeSet<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.toString().compareTo(e2.toString());
            }
        });
        
        sortedEdgeSet.addAll(getEdgeSet());
        for (Edge edge: sortedEdgeSet){
            sb.append(edge.toString() + "\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getEdgeSetString();
    }
}

class GraphUtils {

    /***
     *  Input: G = (Vg,Eg);
     *  Build H = (V,E) -- an undirected graph, where:
     *       1. Each vertex v in Vg is transformed into 3 vertices in V: hv (head-vertex of v), v, and  tv  (tail-vertex of v);
     *       2. For each v in Vg: undirected edges (tv,v) and (v,hv) are in E;
     *       3. Each directed edge (u,v) in Eg is transformed into an undirected edge (hu, tv) in E.
     *   Output: H = (V,E)
     * @param nLiteral 
     * @param directedGraph
     * @return
     */
    public static Graph convertToDirectedGraph(int nLiteral, ArrayList<Integer[]> clauses) {
        Graph graph = setup(new Graph(), nLiteral, clauses);
        
        return graph;
    }
    
    /***
     * Transformation Algorithm from 3CNF-SAT to Dir_Ham_Cyc
     * 
     * 1. Vertex Creation
     * 2. Edge Creation
     * @return directed graph
     */
    private static Graph setup(Graph graph, int N, ArrayList<Integer[]> clauses){
        
        int K = clauses.size();
        _setupVertex(graph, N, K);
        _setupEdge(graph, N, clauses);
        return graph;
    }

    /***
     * Vertex Creation
     * 
     * 1. Create 2 vertices S and T (represent Source S and sink T)
     * 2. For each literal Xn, n = 1..N: Create 3K+3 (literal) vertices:
     *    + 2 boundary vertices in the left and the right Ln, Rn
     *    + A (first, 0-th) buffer vertex Bn.0
     *    + 3K internal vertices of form: Xn.k.L, Xn.k.R and Bn.k for each k = 1..K
     * 3. For each clause Ck: create a clausal vertex Ck to represent the clause.
     * 
     * @param graph
     * @param N
     * @param K
     */
    private static void _setupVertex(Graph graph, int N, int K) {
        // 1. Create 2 Vertices S & T (Represent Source S & Sink T)
        graph.addVertex("S");
        graph.addVertex("T");
        
        // 2. For each Literal Xn, n = 1..N: Create 3K + 3 vertices
        for (int n = 1; n <= N; n++){
            
            // Boundary Vertex: Ln
            graph.addVertex("L" + n);
            
            // Bn.0
            graph.addVertex(String.format("B%s.%s", n, 0));
            
            // for each k = 1..K: Xn.k.L, Xn.k.R and Bn.k
            for (int k = 1; k <= K; k++){
                // Xn.k.L
                graph.addVertex(String.format("X%s.%s.L", n, k));
                // Xn.k.R
                graph.addVertex(String.format("X%s.%s.R", n, k));
                // Bn.k
                graph.addVertex(String.format("B%s.%s", n, k));
            }
            
            // Boundary Vertex: Rn
            graph.addVertex(String.format("R%s", n));
        }
        
        // 3. For each clause Ck: create a clausal vertex Ck to represent the clause.
        for (int k = 1; k <= K; k++){
            graph.addVertex(String.format("C%s", k));
        }
    }
    
    /***
     * Edge Creation
     * 
     * 1. Create edges between N(3K+3) literal vertices
     *      +For each literal index n = 1..N: 
     *      Create horizontal edges: 2-way directed edges
     *          Ln <-> Bn.0 <-> Xn.1.L <-> Xn.1.R <-> Bn.1 Xn.2.L <-> Xn.2.R <-> Bn.2 ... Xn.K.L <-> Xn.K.R <-> Bn.K  <-> Rn
     *      +Create 4 vertical edges for n = 1..N-1:
     *          Ln -> L(n+1), Ln -> R(n+1); Rn -> R(n+1), Rn -> L(n+1)
     *      
     *  2. Create edges with S and T
     *      + Connect S to L1, R1: S->L1, S->R1
     *      + Connect LN, RN to T: LN->T, RN->T
     *      + Connect T to S: T->S
     *      
     *  3. Create edges with the K clausal vertices
     *      + For each clausal vertex Ck with index k: (Ck is of form Ck = Xn v Xu v Xv) (total 6 directed edges)
     *          + For literal Xn: (2 directed edges)
     *              if Xn is positive literal: Connect Xn.k.L -> Ck and connect Ck -> Xn.k.R
     *              if Xn is negative literal: Connect Ck -> Xn.k.L and connect Xn.k.R -> Ck
     *          + For literal Xu and Xv: the same as above (4 directed edges)
     *          
     * @param graph
     * @param N
     * @param clauses
     */
    private static void _setupEdge(Graph graph, int N, ArrayList<Integer[]> clauses) {
        int K = clauses.size();
        
        // 1. Create edges between N(3K+3) literal vertices
        // +For each literal index n = 1..N: 
        // Create horizontal edges: 2-way directed edges
        // Ln <-> Bn.0 <-> Xn.1.L <-> Xn.1.R <-> Bn.1 Xn.2.L <-> Xn.2.R <-> Bn.2 ... Xn.K.L <-> Xn.K.R <-> Bn.K  <-> Rn
        for (int n = 1; n <= N; n++){
            graph.add2WayDirectedEdge(String.format("L%s", n), String.format("B%s.%s", n, 0));
            graph.add2WayDirectedEdge(String.format("B%s.%s", n, 0), String.format("X%s.%s.L", n, 1));
            for (int k = 1; k <= K; k++){
                graph.add2WayDirectedEdge(String.format("X%s.%s.L", n, k), String.format("X%s.%s.R", n, k));
                graph.add2WayDirectedEdge(String.format("B%s.%s", n, k), String.format("X%s.%s.R", n, k));
            }
            for (int k = 1; k < K; k++){
                graph.add2WayDirectedEdge(String.format("B%s.%s", n, k), String.format("X%s.%s.L", n, k+1));
            }
            graph.add2WayDirectedEdge(String.format("B%s.%s", n, K), String.format("R%s", n));
        }
        
        // +Create 4 vertical edges for n = 1..N-1:
        for (int n = 1; n < N; n++){
            // Ln -> L(n+1)
            graph.addDirectedEdge(String.format("L%s", n) , String.format("L%s", n+1));
            // Ln -> R(n+1)
            graph.addDirectedEdge(String.format("L%s", n) , String.format("R%s", n+1));
            // Rn -> R(n+1)
            graph.addDirectedEdge(String.format("R%s", n) , String.format("R%s", n+1));
            // Rn -> L(n+1)
            graph.addDirectedEdge(String.format("R%s", n) , String.format("L%s", n+1));
        }
        
        // 2. Create edges with S and T
        // + Connect S to L1, R1: S->L1, S->R1
        graph.addDirectedEdge("S", "L1");
        graph.addDirectedEdge("S", "R1");
        
        // + Connect LN, RN to T: LN->T, RN->T
        graph.addDirectedEdge(String.format("L%s", N), "T");
        graph.addDirectedEdge(String.format("R%s", N), "T");
        
        // + Connect T to S: T->S
        graph.addDirectedEdge("T", "S");
        
        // 3. Create edges with the K clausal vertices
        // + For each clausal vertex Ck with index k: (Ck is of form Ck = Xn v Xu v Xv) (total 6 directed edges)
        for (int k = 1; k <= K; k++){
            Integer[] clause = clauses.get(k - 1);
            
            // + For literal Xn, Xu, Xv: (2 directed edges each)
            for (int literal: clause){ 
                if (literal > 0) { // positive literal: Connect Xn.k.L -> Ck and connect Ck -> Xn.k.R
                    graph.addDirectedEdge(String.format("X%s.%s.L", literal, k), String.format("C%s", k));
                    graph.addDirectedEdge(String.format("C%s", k), String.format("X%s.%s.R", literal, k));
                } else { // negative literal: Connect Ck -> Xn.k.L and connect Xn.k.R -> Ck
                    literal = Math.abs(literal);
                    graph.addDirectedEdge(String.format("C%s", k), String.format("X%s.%s.L", literal, k));
                    graph.addDirectedEdge(String.format("X%s.%s.R", literal, k), String.format("C%s", k));
                }
            }
        }
    }

    
}

public class Convert3CNFSatToDirHamCycle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // # of testcases
        
        for (int i = 0; i < T; i++){
            int N = scanner.nextInt(); // # of variables
            int K = scanner.nextInt(); // # of clauses
            
            ArrayList<Integer[]> clauses = new ArrayList<Integer[]>();
            
            for (int k = 0; k < K; k++){
                // k := clause index
                int m = scanner.nextInt();
                int n = scanner.nextInt();
                int p = scanner.nextInt();
                
                Integer[] c = new Integer[]{m,n,p};
                clauses.add(c);
            }

            Graph graph = GraphUtils.convertToDirectedGraph(N, clauses);

            System.out.print(graph);
        }  
        scanner.close();
    }
}
