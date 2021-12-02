package implementaions;

import api.EdgeData;
import api.NodeData;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import org.w3c.dom.Node;

public class MyDWG implements api.DirectedWeightedGraph {
    private HashMap<Integer, MyNode> nodes;
    private int numOfEdge;
    private int mc;
    private int nodeIter;
    private int edgeIter;
    private int edgeNodeIter;

    public MyDWG() {
        this.nodes = new HashMap<Integer, MyNode>();
        this.numOfEdge = 0;
        this.mc = 0;
        this.nodeIter = 0;
        this.edgeIter = 0;
        this.edgeNodeIter = 0;

    }

    public MyDWG(HashMap<Integer, MyNode> nodes1) {
        this.nodes = nodes1;
        this.numOfEdge = 0;
        this.mc = 0;
        this.nodeIter = 0;
        this.edgeIter = 0;
        this.edgeNodeIter = 0;
    }
    /**
     * A simple copy constructor
     * @param other
     */
    public MyDWG(MyDWG other){
        this.nodes = other.nodes;
        this.numOfEdge = other.numOfEdge;
        this.mc = other.mc;
        this.nodeIter = other.nodeIter;
        this.edgeIter = other.edgeIter;
        this.edgeNodeIter = other.edgeNodeIter;
    }
    /**
     * A constructor that receives a json file and created a graph from it
     * @param json
     */
    public MyDWG(String json) {
        this.nodes = new HashMap<Integer , MyNode>();
        this.numOfEdge = 0;
        this.mc = 0;
        this.nodeIter = 0;
        this.edgeIter = 0;
        this.edgeNodeIter = 0;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(json));
            List<?> Nodes = null;
            List<?> Edges = null;
            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey().toString().equals("Nodes")) {
                    Nodes = (List<?>) entry.getValue();
                } else {
                    Edges = (List<?>) entry.getValue();
                }
            }
            ArrayList<NodeData> nodeArrayList = new ArrayList<>();
            ArrayList<EdgeData> edgeArrayList = new ArrayList<>();
            //now we go through each row in jason object Nodes and create a node from it
            for (Object node : Nodes) {
                LinkedTreeMap<?, ?> node1 = (LinkedTreeMap<?, ?>)node;
                String[] split_x_y_z = node1.get("pos").toString().split(",");
                GeoL geolocation = new GeoL(Double.parseDouble(split_x_y_z[0]),Double.parseDouble(split_x_y_z[1]),Double.parseDouble(split_x_y_z[2]));
                Double data_id = Double.parseDouble(node1.get("id").toString());
                int id_in_int = data_id.intValue();
                nodeArrayList.add(new NodeD(id_in_int,geolocation,0,"",0 ));
            }
            //now we go through each row in jason object Edges and create an edge from it
            for (Object edge : Edges) {
                LinkedTreeMap<?, ?> edge1 = (LinkedTreeMap<?, ?>) edge ;
                Double edge1_src = Double.parseDouble(edge1.get("src").toString());
                Double edge1_dest = Double.parseDouble(edge1.get("dest").toString());
                Double edge1_w  = Double.parseDouble(edge1.get("w").toString());
                edgeArrayList.add(new EdgeD(edge1_src.intValue(),edge1_dest.intValue(),edge1_w,"",0 ));
            }
            //add nodes to this.nodes list
            for(NodeData node:nodeArrayList){
                this.nodes.put(node.getKey(),new MyNode(node));
            }
            //add edges through our connect function
            for(EdgeData e :edgeArrayList){
                connect(e.getSrc(),e.getDest(),e.getWeight());
            }
            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * returns the node_data by the node_id.
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key).getNode();
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.nodes.get(src).getConnectedTo().get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        this.nodes.put(n.getKey(), new MyNode(n));
        this.mc++;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (this.nodes.get(src).getConnectedTo().containsKey(dest)) {
            // will change if make changes with tag or info
            this.nodes.get(src).getConnectedTo().replace(dest, new EdgeD(src, dest, w));
            this.nodes.get(dest).getConnectedFrom().replace(src, w);
        } else {
            EdgeD edge = new EdgeD(src, dest, w);
            this.nodes.get(src).AddConnectedTo(edge);
            this.nodes.get(dest).AddConnectedFrom(src, w);
            this.numOfEdge++;
        }
        this.mc++;
    }

    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() throws RuntimeException {
        this.nodeIter = mc;//when we implement the main funciton we will check if nodeIter changed from mc and if so we need to throw a runtime exception
        HashMap<Integer, NodeData> newHashMap = new HashMap<Integer, NodeData>();
        for (Integer key : nodes.keySet()) {
            newHashMap.put(key, this.nodes.get(key).getNode());
        }
        Iterator<NodeData> nodeDIterator = newHashMap.values().iterator();//need to test this
        return nodeDIterator;
    }

    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() throws RuntimeException {
        this.edgeIter = mc;
        ArrayList<EdgeData> all_edges_in_graph = new ArrayList<EdgeData>();
        for (Integer key : this.nodes.keySet()) {
            for (Integer key2 : this.nodes.get(key).getConnectedTo().keySet()) {
                all_edges_in_graph.add(this.nodes.get(key).getConnectedTo().get(key2));
            }
        }
        Iterator<EdgeData> edgeDIterator = all_edges_in_graph.iterator();
        return edgeDIterator;
    }

    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @param node_id
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id)throws RuntimeException {
        this.edgeNodeIter = mc;
        HashMap<Integer, EdgeData> newHashMap = new HashMap<Integer, EdgeData>();
        for (Integer key : this.nodes.get(node_id).getConnectedTo().keySet()) {
            newHashMap.put(key, this.nodes.get(node_id).getConnectedTo().get(key));
        }
        Iterator<EdgeData> iterator = newHashMap.values().iterator();//need to test this
        return iterator;
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public NodeData removeNode(int key) {
        for (Integer key1 : this.nodes.get(key).getConnectedTo().keySet()) {
            this.nodes.get(key1).getConnectedFrom().remove(key);
            this.numOfEdge--;
        }
        for (Integer key1 : this.nodes.get(key).getConnectedFrom().keySet()) {
            this.nodes.get(key1).getConnectedTo().remove(key);
            this.numOfEdge--;
        }
        NodeData node = this.nodes.get(key).getNode();
        this.nodes.remove(key);
        return node;
    }

    /**
     * Deletes the edge from the graph.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edge = getEdge(src, dest);
        this.nodes.get(src).getConnectedTo().remove(dest);
        this.nodes.get(dest).getConnectedFrom().remove(src);
        this.numOfEdge--;
        this.mc++;
        return edge;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.numOfEdge;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }
}
