

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
class Edge {
    private Set<Vertex> vertexPair;
    public Edge(Vertex u, Vertex v){
        vertexPair = new HashSet<Vertex>();
        vertexPair.add(u);
        vertexPair.add(v);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;            
        }
        
        if (!(obj instanceof Edge)){
            return false;
        }
        
        return vertexPair.equals(((Edge) obj).vertexPair);
    }
    
    @Override
    public int hashCode() {
        return vertexPair.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.vertexPair.toString();
    }
}

public class HamCycle {
    
    
    
    // Vertex List
    private Set<Vertex> vertexSet;
    // Edge Set DataStructure
    private Set<Edge> edgeSet;
    // Hamiltonian Cycle (verify this exists in the graph)
    private ArrayList<Vertex> hamCycle;

    public HamCycle(){
        setVertexSet(new HashSet<Vertex>());
        edgeSet = new HashSet<Edge>();
        hamCycle = new ArrayList<Vertex>();
    }
    
    public Vertex addVertex(String u){
        Vertex vert = new Vertex(u);
        getVertexSet().add(vert);
        return vert;
    }
    
    public Edge addEdge(String v1, String v2){
        Vertex u = addVertex(v1);
        Vertex v = addVertex(v2);
        Edge e = new Edge(u, v);
        edgeSet.add(e);
        return e;
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
            if (!edgeSet.contains(new Edge(u, v))){
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
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // # of testcases
        
        for (int i = 0; i < T; i++){
            int N = scanner.nextInt(); // # of vertices
            int M = scanner.nextInt(); // # of edges
            
            HamCycle hamCycle = new HamCycle();
            
            for (int j = 0; j < M; j++){
                String u = scanner.next();
                String v = scanner.next();
                hamCycle.addEdge(u, v);
            }
            
            int isolatedVertexCount = N - hamCycle.getVertexSet().size();
            for (int j = 0; j < isolatedVertexCount; j++){
                hamCycle.addVertex("ISOLATED " + j);
            }
            
            int p = scanner.nextInt();
            ArrayList<Vertex> P = new ArrayList<Vertex>();
            for (int j = 0; j < p; j++){
                String v = scanner.next();
                hamCycle.addHamCycleVertex(v);
            }
            
            if (hamCycle.isHamCycle()){
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }        
    }


}
