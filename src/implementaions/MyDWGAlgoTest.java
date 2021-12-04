package implementaions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGAlgoTest {
    MyDWGAlgo best_algo_1 = new MyDWGAlgo("data/G1.json");
    MyDWGAlgo best_algo_2 = new MyDWGAlgo("data/Not_connected_G.json");
    @Test
    void init() {
    best_algo_1.init(best_algo_2.getGraph());
    assertSame(best_algo_2.getGraph(),best_algo_1.getGraph());
    }

    @Test
    void getGraph() {
       assertEquals(36,this.best_algo_1.getGraph().getMC());//if this would return 0 it means we couldn't gt graph
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
     assertTrue(best_algo_1.isConnected());
     assertFalse(best_algo_2.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(1.232037506070033,best_algo_2.shortestPathDist(0,1));
        assertEquals(4.418137984092362,best_algo_2.shortestPathDist(7,1));
    }

    @Test
    void shortestPath() {

    }

    @Test
    void center() {

    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
        assertFalse(best_algo_1.equals(null));
        assertFalse(best_algo_2.equals(null));
    }
}