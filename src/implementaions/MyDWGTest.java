package implementaions;

import api.EdgeData;
import api.NodeData;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGTest {
    MyDWG dwg = new MyDWG("data/G1.json");

    //
    @org.junit.jupiter.api.Test
    void getNode() {
        NodeData node = dwg.getNode(7);
        assertEquals(7, node.getKey());
        assertEquals(35.205764353510894, node.getLocation().x());
        assertEquals(32.106326494117646, node.getLocation().y());
        assertEquals(0.0, node.getLocation().z());
        assertEquals(0.0, node.getWeight());
        node = dwg.getNode(13);
        assertEquals(13, node.getKey());
        assertEquals(35.189568308313156, node.getLocation().x());
        assertEquals(32.106617263865544, node.getLocation().y());
        assertEquals(0.0, node.getLocation().z());
        assertEquals(0.0, node.getWeight());
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        EdgeData edge = dwg.getEdge(7, 6);
        assertEquals(7, edge.getSrc());
        assertEquals(6, edge.getDest());
        assertEquals(1.5786081900467002, edge.getWeight());
        edge = dwg.getEdge(16, 15);
        assertEquals(16, edge.getSrc());
        assertEquals(15, edge.getDest());
        assertEquals(1.5677693324851103, edge.getWeight());
    }

    @org.junit.jupiter.api.Test
    void addNode() {

    }

    @org.junit.jupiter.api.Test
    void connect() {
        NodeData first_node = dwg.getNode(0);
        NodeData second_node = dwg.getNode(1);
        dwg.connect(first_node.getKey(), second_node.getKey(), 1.0024425);
        assertEquals(1.0024425, dwg.getMyNode(0).getConnectedTo().get(1).getWeight());//check that connected to works
        assertEquals(1.0024425, dwg.getMyNode(1).getConnectedFrom().get(0));
        dwg.connect(second_node.getKey(), first_node.getKey(), 3.13475);
        assertEquals(3.13475, dwg.getMyNode(1).getConnectedTo().get(0).getWeight());//check that connected to works
        assertEquals(3.13475, dwg.getMyNode(0).getConnectedFrom().get(1));
    }

    @org.junit.jupiter.api.Test
    void nodeIter() {
        Iterator<NodeData> nodeIterator = dwg.nodeIter();
        int size = 0;
        while (nodeIterator.hasNext()) {
            nodeIterator.next();
            size++;
        }
        assertEquals(size, dwg.nodeSize());
    }

    @org.junit.jupiter.api.Test
    void edgeIter() {
        Iterator<EdgeData> edgeIterator = dwg.edgeIter();
        int size = 0;
        while (edgeIterator.hasNext()) {
            edgeIterator.next();
            size++;
        }
        assertEquals(size, dwg.edgeSize());
    }

    @org.junit.jupiter.api.Test
    void testEdgeIter() {
        Iterator<EdgeData> edgeIterator = dwg.edgeIter(0);
        MyNode node = dwg.getMyNode(0);
        while (edgeIterator.hasNext()) {
            assertTrue(node.getConnectedTo().containsValue(edgeIterator.next()));
        }
    }

    @org.junit.jupiter.api.Test
    void removeNode() {
        NodeData new_node = new NodeD(20, new GeoL(151553.022, 153235.5342, 0.0));
        dwg.addNode(new_node);
        dwg.removeNode(new_node.getKey());
        assertEquals(null, dwg.getNode(new_node.getKey()));
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        dwg.removeEdge(0,1);
        assertFalse(dwg.getMyNode(0).getConnectedTo().containsKey(1));
    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
        assertEquals(17, dwg.nodeSize());
        NodeData new_node = new NodeD(20, new GeoL(151553.022, 153235.5342, 0.0));
        dwg.addNode(new_node);
        assertEquals(18, dwg.nodeSize());
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
        int edgeCount = 0;
        Iterator<NodeData> nodeIterator=dwg.nodeIter();
        while(nodeIterator.hasNext()){
            edgeCount+=dwg.getMyNode(nodeIterator.next().getKey()).getConnectedTo().size();
        }
        assertEquals(dwg.edgeSize(),edgeCount);
    }

    @org.junit.jupiter.api.Test
    void getMC() {
        int currentMc = dwg.getMC();
        NodeData new_node = new NodeD(20, new GeoL(151553.022, 153235.5342, 0.0));
        dwg.addNode(new_node);
        dwg.removeNode(new_node.getKey());
        assertEquals(currentMc + 2, dwg.getMC());
    }
}