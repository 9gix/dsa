import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HamCycleTest {

    private Graph graph;
    private Vertex a;
    private Vertex b;
    private Vertex c;
    private Vertex d;
    private Vertex e;
    private Vertex f;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        a = graph.addVertex("A");
        b = graph.addVertex("B");
        c = graph.addVertex("C");
        d = graph.addVertex("D");
        e = graph.addVertex("E");
        f = graph.addVertex("F");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testVisitingAllVertex() {

        graph.addEdge(new UndirectedEdge(a, b));
        graph.addEdge(new UndirectedEdge(a, e));
        graph.addEdge(new UndirectedEdge(c, b));
        graph.addEdge(new UndirectedEdge(b, a));
        graph.addEdge(new UndirectedEdge(c, d));
                
        graph.setHamCycleVertices("A");
        assertFalse(graph.isVisitingAllVertex());
        graph.setHamCycleVertices("A B D C");
        assertFalse(graph.isVisitingAllVertex());
        
        graph.setHamCycleVertices("A B D C E F");
        assertTrue(graph.isVisitingAllVertex());
    }
    
    @Test
    public void testLoopBack(){
        graph.addHamCycleVertex("A");
        assertFalse(graph.isLoopBack());
        graph.addHamCycleVertex("B");
        assertFalse(graph.isLoopBack());
        graph.addHamCycleVertex("D");
        assertFalse(graph.isLoopBack());
        graph.addHamCycleVertex("C");
        assertFalse(graph.isLoopBack());
        graph.addHamCycleVertex("B");
        assertFalse(graph.isLoopBack());
        graph.addHamCycleVertex("A");
        assertTrue(graph.isLoopBack());
    }
    
    @Test
    public void testInterconnected(){

        graph.addEdge(new UndirectedEdge(a, b));
        graph.addEdge(new UndirectedEdge(f, d));
        graph.addEdge(new UndirectedEdge(a, f));
        graph.addEdge(new UndirectedEdge(b, c));
        graph.addEdge(new UndirectedEdge(b, a));
        graph.addEdge(new UndirectedEdge(c, d));
        
        graph.setHamCycleVertices("F A B C D");
        assertTrue(graph.isInterconnected());
        graph.setHamCycleVertices("F A B C D A");
        assertFalse(graph.isInterconnected());
    }
    
    @Test
    public void testSimpleCycle(){

        graph.addEdge(new UndirectedEdge(a, b));
        graph.addEdge(new UndirectedEdge(f, d));
        graph.addEdge(new UndirectedEdge(a, f));
        graph.addEdge(new UndirectedEdge(b, c));
        graph.addEdge(new UndirectedEdge(b, a));
        graph.addEdge(new UndirectedEdge(c, d));
        
        Vertex x = graph.addVertex("X");
        
        graph.setHamCycleVertices("F A B C D");
        assertFalse(graph.isSimpleCycle()); // No Loopback
        graph.setHamCycleVertices("A B C D A");
        assertFalse(graph.isSimpleCycle()); // No Edge A-D
        graph.setHamCycleVertices("F A B C D A F");
        assertFalse(graph.isSimpleCycle()); // Repeating Vertex
        
        graph.addEdge(new UndirectedEdge(x, a));
        graph.setHamCycleVertices("A B C D X A");
        assertFalse(graph.isSimpleCycle()); // No Edge X-D 
        graph.addEdge(new UndirectedEdge(x, d));
        graph.setHamCycleVertices("A B C D X A");
        assertTrue(graph.isSimpleCycle()); // Cycle ABCDXA
    }
    
    @Test
    public void testHamCycle(){

        Vertex x = graph.addVertex("X");
        
        graph.addEdge(new UndirectedEdge(a, e));
        graph.addEdge(new UndirectedEdge(b, e));
        graph.addEdge(new UndirectedEdge(b, c));
        graph.addEdge(new UndirectedEdge(b, a));
        graph.addEdge(new UndirectedEdge(c, f));
        graph.addEdge(new UndirectedEdge(x, a));
        graph.addEdge(new UndirectedEdge(x, d));
        graph.addEdge(new UndirectedEdge(f, d));
        graph.setHamCycleVertices("A E B C F D X A");
        
        assertTrue(graph.isHamCycle());
        graph.addVertex("Y");
        assertFalse(graph.isHamCycle());
    }

}
