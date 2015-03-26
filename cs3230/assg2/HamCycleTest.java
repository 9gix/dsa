import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HamCycleTest {

    private HamCycle hamCycle;

    @Before
    public void setUp() throws Exception {
        hamCycle = new HamCycle();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testVisitingAllVertex() {
        hamCycle.addEdge("A", "B");
        hamCycle.addEdge("A", "F");
        hamCycle.addEdge("B", "C");
        hamCycle.addEdge("B", "A");
        hamCycle.addEdge("C", "D");
        
        hamCycle.addHamCycleVertex("A");
        assertFalse(hamCycle.isVisitingAllVertex());
        hamCycle.addHamCycleVertex("B");
        assertFalse(hamCycle.isVisitingAllVertex());
        hamCycle.addHamCycleVertex("D");
        assertFalse(hamCycle.isVisitingAllVertex());
        hamCycle.addHamCycleVertex("C");
        assertFalse(hamCycle.isVisitingAllVertex());
        hamCycle.addHamCycleVertex("F");
        assertTrue(hamCycle.isVisitingAllVertex());
    }
    
    @Test
    public void testLoopBack(){
        hamCycle.addHamCycleVertex("A");
        assertFalse(hamCycle.isLoopBack());
        hamCycle.addHamCycleVertex("B");
        assertFalse(hamCycle.isLoopBack());
        hamCycle.addHamCycleVertex("D");
        assertFalse(hamCycle.isLoopBack());
        hamCycle.addHamCycleVertex("C");
        assertFalse(hamCycle.isLoopBack());
        hamCycle.addHamCycleVertex("B");
        assertFalse(hamCycle.isLoopBack());
        hamCycle.addHamCycleVertex("A");
        assertTrue(hamCycle.isLoopBack());
    }
    
    @Test
    public void testInterconnected(){
        hamCycle.addEdge("A", "B");
        hamCycle.addEdge("F", "D");
        hamCycle.addEdge("A", "F");
        hamCycle.addEdge("B", "C");
        hamCycle.addEdge("B", "A");
        hamCycle.addEdge("C", "D");
        
        hamCycle.addHamCycleVertex("F");
        hamCycle.addHamCycleVertex("A");
        hamCycle.addHamCycleVertex("B");
        hamCycle.addHamCycleVertex("C");
        hamCycle.addHamCycleVertex("D");
        assertTrue(hamCycle.isInterconnected());
        hamCycle.addHamCycleVertex("A");
        assertFalse(hamCycle.isInterconnected());
    }
    
    @Test
    public void testSimpleCycle(){
        hamCycle.addEdge("A", "B");
        hamCycle.addEdge("F", "D");
        hamCycle.addEdge("A", "F");
        hamCycle.addEdge("B", "C");
        hamCycle.addEdge("B", "A");
        hamCycle.addEdge("C", "D");
        hamCycle.addVertex("X");
        hamCycle.setHamCycleVertices("F A B C D");
        assertFalse(hamCycle.isSimpleCycle()); // No Loopback
        hamCycle.setHamCycleVertices("A B C D A");
        assertFalse(hamCycle.isSimpleCycle()); // No Edge A-D
        hamCycle.setHamCycleVertices("F A B C D A F");
        assertFalse(hamCycle.isSimpleCycle()); // Repeating Vertex
        
        hamCycle.addEdge("X", "A");
        hamCycle.setHamCycleVertices("A B C D X A");
        assertFalse(hamCycle.isSimpleCycle()); // No Edge X-D 
        hamCycle.addEdge("X", "D");
        hamCycle.setHamCycleVertices("A B C D X A");
        assertTrue(hamCycle.isSimpleCycle()); // Cycle ABCDXA
    }
    
    @Test
    public void testHamCycle(){
        hamCycle.addEdge("A", "B");
        hamCycle.addEdge("B", "C");
        hamCycle.addEdge("B", "A");
        hamCycle.addEdge("C", "D");
        hamCycle.addEdge("X", "A");
        hamCycle.addEdge("X", "D");
        hamCycle.setHamCycleVertices("A B C D X A");
        assertTrue(hamCycle.isHamCycle());
        hamCycle.addVertex("Y");
        assertFalse(hamCycle.isHamCycle());
    }

}
