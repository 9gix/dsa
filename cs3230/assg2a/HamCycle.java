

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
}


public class HamCycle{
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
                Vertex uVertex = graph.addVertex(u);
                Vertex vVertex = graph.addVertex(v);
                graph.addEdge(new UndirectedEdge(uVertex, vVertex));
            }
            
            int isolatedVertexCount = N - graph.getVertexSet().size();
            for (int j = 0; j < isolatedVertexCount; j++){
                graph.addVertex("ISOLATED " + j);
            }
            
            int p = scanner.nextInt();
            for (int j = 0; j < p; j++){
                String v = scanner.next();
                graph.addHamCycleVertex(v);
            }
            
            if (graph.isHamCycle()){
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }   
        scanner.close();
    }


}
