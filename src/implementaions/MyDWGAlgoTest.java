package implementaions;

import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGAlgoTest {
    MyDWGAlgo best_algo_1 = new MyDWGAlgo("data/G1.json");
    MyDWGAlgo best_algo_2 = new MyDWGAlgo("data/G2.json");
    MyDWGAlgo best_algo_3 = new MyDWGAlgo("data/G3.json");
    MyDWGAlgo best_algo_4 = new MyDWGAlgo("data/Not_connected_G.json");
    MyDWGAlgo best_algo_5 = new MyDWGAlgo("data/1000Nodes.json");
    @Test
    void init() {
    best_algo_1.init(best_algo_4.getGraph());
    assertSame(best_algo_4.getGraph(),best_algo_1.getGraph());
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
     assertFalse(best_algo_4.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(1.232037506070033,best_algo_4.shortestPathDist(0,1));
        assertEquals(4.418137984092362,best_algo_4.shortestPathDist(7,1));
    }

    @Test
    void shortestPath() {
       ArrayList<NodeData> path= (ArrayList<NodeData>)best_algo_1.shortestPath(0,1);
       assertEquals(0,path.get(0).getKey());
       assertEquals(1,path.get(1).getKey());
       path= (ArrayList<NodeData>)best_algo_4.shortestPath(7,1);
       assertEquals(7,path.get(0).getKey());
       assertEquals(5,path.get(1).getKey());
       assertEquals(3,path.get(2).getKey());
       assertEquals(1,path.get(3).getKey());

    }
    @Test
    void center() {
        assertEquals(8 , best_algo_1.center().getKey());
        assertEquals(0 , best_algo_2.center().getKey());
        assertEquals(40 , best_algo_3.center().getKey());
        assertNull(best_algo_4.center());
       // assertEquals(362 , best_algo_5.center().getKey());
    }

    @Test
    void tsp() {
        List<NodeData> cities = new ArrayList<>();
        cities.add(best_algo_4.getGraph().getNode(0));
        cities.add(best_algo_4.getGraph().getNode(1));
        cities.add(best_algo_4.getGraph().getNode(3));
        cities.add(best_algo_4.getGraph().getNode(7));
//        cities.add(best_algo_1.getGraph().getNode(16));
//        cities.add(best_algo_1.getGraph().getNode(8));
        List<NodeData> ans = best_algo_4.tsp(cities);
        for (int i = 0; i <ans.size() ; i++) {
            System.out.println(ans.get(i).getKey());
        }

    }

    @Test
    void save() {
    }

    @Test
    void load() {
        assertFalse(best_algo_1.equals(null));
        assertFalse(best_algo_4.equals(null));
    }
}