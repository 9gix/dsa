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
    
    private Vertex headVertex;
    private Vertex tailVertex;
    
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
    
    public Vertex getHeadVertex() {
        return headVertex;
    }

    public void setHeadVertex(Vertex headVertex) {
        this.headVertex = headVertex;
    }

    public Vertex getTailVertex() {
        return tailVertex;
    }

    public void setTailVertex(Vertex tailVertex) {
        this.tailVertex = tailVertex;
    }

    @Override
    public String toString() {
        return this.label;
    }
    
}

class UndirectedEdge extends Edge {
    private SortedSet<Vertex> vertexPair;
    public UndirectedEdge(Vertex u, Vertex v){
        vertexPair = new TreeSet<Vertex>(new Comparator<Vertex>() {

            @Override
            public int compare(Vertex u, Vertex v) {
                return u.getLabel().compareTo(v.getLabel());
            }
            
        });
        vertexPair.add(u);
        vertexPair.add(v);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;            
        }
        
        if (!(obj instanceof UndirectedEdge)){
            return false;
        }
        
        return vertexPair.equals(((UndirectedEdge) obj).vertexPair);
    }
    
    @Override
    public int hashCode() {
        return vertexPair.hashCode();
    }
    
    @Override
    public String toString() {
        return this.vertexPair.first().toString() + " " + this.vertexPair.last().toString();
    }
}

class Graph {
    
    
    
    // Vertex List
    private Set<Vertex> vertexSet;
    // Edge Set DataStructure
    private Set<Edge> edgeSet;
    // Hamiltonian Cycle (verify this exists in the graph)
    private ArrayList<Vertex> hamCycle;

    public Graph(){
        setVertexSet(new HashSet<Vertex>());
        setEdgeSet(new HashSet<Edge>());
        hamCycle = new ArrayList<Vertex>();
    }
    
    public Vertex addVertex(String u){
        Vertex vert = new Vertex(u);
        getVertexSet().add(vert);
        return vert;
    }
    
    public void addEdge(Edge edge){
        getEdgeSet().add(edge);
    }

    public void addHamCycleVertex(String v) {
        hamCycle.add(new Vertex(v));
    }
    
    public boolean isHamCycle(){
        return isSimpleCycle() && isVisitingAllVertex();
    }

    public boolean isSimpleCycle() {
        return isLoopBack() && isInterconnected() && isNoRepetition();
    }

    public boolean isNoRepetition() {
        Set<Vertex> hamSet = new HashSet<Vertex>(hamCycle);
        if (hamSet.size() == hamCycle.size() - 1){
            return true;
        }
        return false;
    }

    public boolean isInterconnected() {
        for (int i = 0; i < getHamCycle().size() - 1; i++){
            Vertex u = getHamCycle().get(i);
            Vertex v;
            try {
                v = getHamCycle().get(i+1);
            } catch (IndexOutOfBoundsException e){
                v = getHamCycle().get(i);
            }
            if (!getEdgeSet().contains(new UndirectedEdge(u, v))){
                return false;
            }
        }
        return true;
    }

    public boolean isLoopBack() {
        if (getHamCycle().size() <= 1){
            return false;
        }
        return getHamCycle().get(getHamCycle().size() - 1).equals(getHamCycle().get(0));
    }

    public boolean isVisitingAllVertex() {
        return getVertexSet().equals(new HashSet<Vertex>(getHamCycle()));
    }

    public ArrayList<Vertex> getHamCycle() {
        return hamCycle;
    }
    public void clearHamCycleVertices(){
        getHamCycle().clear();
    }
    
    public void setHamCycleVertices(String argv){
        clearHamCycleVertices();
        for (String a: argv.split(" ")){
            hamCycle.add(new Vertex(a));
        }
    }

    public Set<Vertex> getVertexSet() {
        return vertexSet;
    }

    public void setVertexSet(Set<Vertex> vertexSet) {
        this.vertexSet = vertexSet;
    }
    
    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public void setEdgeSet(Set<Edge> edgeSet) {
        this.edgeSet = edgeSet;
    }
    
    
    public String getEdgeSetString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVertexSet().size() + " " + getEdgeSet().size() + "\n");
        
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
}

class GraphUtils {

    /***
     *  Input: G = (Vg,Eg);
     *  Build H = (V,E) -- an undirected graph, where:
     *       1. Each vertex v in Vg is transformed into 3 vertices in V: hv (head-vertex of v), v, and  tv  (tail-vertex of v);
     *       2. For each v in Vg: undirected edges (tv,v) and (v,hv) are in E;
     *       3. Each directed edge (u,v) in Eg is transformed into an undirected edge (hu, tv) in E.
     *   Output: H = (V,E)
     * @param directedGraph
     * @return
     */
    public static Graph convertToUndirectedEdge(Graph directedGraph) {
        Set<Edge> directedEdgeSet = directedGraph.getEdgeSet();
        Set<Vertex> directedVertexSet= directedGraph.getVertexSet();
        Map<String, Vertex> directedVertexMap= new HashMap<String, Vertex>();

        
        for (Vertex vertex: directedVertexSet){
            directedVertexMap.put(vertex.getLabel(), vertex);
        }
        
        Set<Edge> undirectedEdgeSet = new HashSet<Edge>();
        Set<Vertex> undirectedVertexSet = new HashSet<Vertex>();
        

        for (Vertex v: directedVertexSet){
            
            // 1. Each vertex v in Vg is transformed into 3 vertices in V: hv (head-vertex of v), v, and  tv  (tail-vertex of v);
            String code = v.getLabel().substring(1, v.getLabel().length());
            Vertex headVertex = new Vertex("H" + code);
            Vertex tailVertex = new Vertex("T" + code);
            v.setHeadVertex(headVertex);
            v.setTailVertex(tailVertex);
            undirectedVertexSet.add(headVertex);
            undirectedVertexSet.add(v);
            undirectedVertexSet.add(tailVertex);
                        
            // 2. For each v in Vg: undirected edges (tv,v) and (v,hv) are in E;
            undirectedEdgeSet.add(new UndirectedEdge(headVertex, v));
            undirectedEdgeSet.add(new UndirectedEdge(tailVertex, v));
        }
        
        // 3. Each directed edge (u,v) in Eg is transformed into an undirected edge (hu, tv) in E.
        for (Edge edge: directedEdgeSet){
            DirectedEdge directedEdge = (DirectedEdge)edge;
            Vertex u = directedVertexMap.get(directedEdge.getFromVertex().getLabel());
            Vertex v = directedVertexMap.get(directedEdge.getToVertex().getLabel());

            Vertex headU = u.getHeadVertex();
            Vertex tailV = v.getTailVertex();
            
            undirectedEdgeSet.add(new UndirectedEdge(headU, tailV));
        }
        
        Graph undirectedGraph = new Graph();
        undirectedGraph.setEdgeSet(undirectedEdgeSet);
        undirectedGraph.setVertexSet(undirectedVertexSet);        
        return undirectedGraph;
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
        return this.getFromVertex().toString() + " -> " + this.getToVertex().toString();
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

public class GraphConvert {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // # of testcases
        
        for (int i = 0; i < T; i++){
            int N = scanner.nextInt(); // # of vertices
            int M = scanner.nextInt(); // # of edges
            
            Graph graph = new Graph();
            
            for (int j = 0; j < M; j++){
                String u = scanner.next();
                String v = scanner.next();
                graph.addVertex(u);
                graph.addVertex(v);
                graph.addEdge(new DirectedEdge(u, v));
            }
            
            int isolatedVertexCount = N - graph.getVertexSet().size();
            for (int j = 0; j < isolatedVertexCount; j++){
                graph.addVertex("ISOLATED " + j);
            }
            
            Graph undirectedGraph = GraphUtils.convertToUndirectedEdge(graph);
            System.out.print(undirectedGraph.getEdgeSetString());
        }  
        scanner.close();
    }

}
