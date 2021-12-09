package implementaions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class MyDWGAlgo implements api.DirectedWeightedGraphAlgorithms{
    private MyDWG graph;
    public MyDWGAlgo(String file){
        this.graph = null;
        load(file);
    }
    public MyDWGAlgo(){
    }
    public MyDWG getMyGraph(){
        return this.graph;
    }
    public HashMap<Integer, MyNode> getNodes(){
        return this.graph.getNodes();
    }
    /**
     * Inits the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph= (MyDWG) g;
    }

    /**
     * Returns the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     *
     * @return DirectedWeightedGraph copy
     */
    @Override
    public DirectedWeightedGraph copy()  {
        MyDWG copy_graph = new MyDWG(this.graph);
        return copy_graph;
    }
    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * @return
     */
    @Override
    public boolean isConnected() {
        if(!HelperAlgo.bfs(this.graph)){
            return false;
        }
        try {
            MyDWG reveresd_graph = HelperAlgo.reverse(this.graph);
            if (!HelperAlgo.bfs(reveresd_graph)) {
                return false;
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        try {
            DijkstraUsingMinHeap.Graph g = new DijkstraUsingMinHeap.Graph(this.graph);
            g.dijkstra_GetMinDistances(src, dest);
            return g.heapNodes[dest];
        }
        catch (Exception e){
            return Double.MAX_VALUE;
        }
    }

    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest){
        try {
            DijkstraUsingMinHeap.Graph g = new DijkstraUsingMinHeap.Graph(this.graph);
            g.dijkstra_GetMinDistances(src, dest);
            ArrayList<NodeData> nodes = new ArrayList<>();
            int key = dest;
            // iterate over the parents and compute the path
            while (key != src) {
                nodes.add(0, this.graph.getNode(key));
                key = g.parents[key];
            }
            //add the src first
            nodes.add(0, this.graph.getNode(src));
            return nodes;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        // check if the graph is connected
        if(!isConnected()) {
            return null;
        }
        try {
            DijkstraUsingMinHeap.Graph g = new DijkstraUsingMinHeap.Graph(this.graph);
            double min = Double.MAX_VALUE;
            int key_holder = -1;
            Iterator<NodeData> iterator = this.getGraph().nodeIter();
            // at every iteration get the node with the max weight and switch the max if needed
            while (iterator.hasNext()) {
                NodeData node = iterator.next();
                g.dijkstra_GetMinDistances(node.getKey(), Integer.MAX_VALUE);
                //check if the current max smaller than the current max
                if (g.max < min) {
                    min = g.max;
                    key_holder = node.getKey();
                }
            }
            return this.graph.getNode(key_holder);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     * @param cities
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if(!HelperAlgo.findPath(cities,graph)) {
            return null;
        }
        try {
            List<NodeData> bestPath = new ArrayList<>();
            double minPath = Double.MAX_VALUE;
            for (int j = 0; j < cities.size(); j++) {
                ArrayList<NodeData> holdCities = new ArrayList<>(cities);
                double current = 0;
                List<NodeData> path = new ArrayList<NodeData>();
                int srcI = j;
                int src = cities.get(srcI).getKey();
                int destI = 0, currentdest = 0;
                holdCities.remove(srcI);
                path.add(graph.getNode(src));
                double ans;
                while (!holdCities.isEmpty()) {
                    double minDist = Double.MAX_VALUE;
                    for (int i = 0; i < holdCities.size(); i++) {
                        if (this.graph.getMyNode(src).getConnectedTo().get(holdCities.get(i).getKey()) != null) {
                            ans = this.graph.getMyNode(src).getConnectedTo().get(holdCities.get(i).getKey()).getWeight();
                        } else {
                            ans = shortestPathDist(src, holdCities.get(i).getKey());
                        }
                        double dist = ans;
                        if (dist < minDist) {
                            minDist = dist;
                            currentdest = holdCities.get(i).getKey();
                            destI = i;
                        }
                    }
                    current += minDist;
                    ArrayList<NodeData> tempPath = (ArrayList<NodeData>) shortestPath(src, currentdest);
                    if (tempPath == null) return null;
                    boolean flag_first = true;
                    for (NodeData n : tempPath) {
                        if (flag_first) {
                            flag_first = false;
                        } else {
                            path.add(n);
                        }
                    }
                    holdCities.remove(destI);
                    src = currentdest;
                }
                if (current < minPath) {
                    minPath = current;
                    bestPath = path;
                }
            }
            return bestPath;
        }
        catch (Exception e){
            return null;
        }
    }
    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        Iterator<EdgeData> iterator=this.graph.edgeIter();
        Map<String, Object> customer ;
        try {
        while(iterator.hasNext()){
            customer =new HashMap<>();
            EdgeData edge= iterator.next();
            customer.put("src", edge.getSrc());
            customer.put("w", edge.getWeight());
            customer.put("dest", edge.getDest());
            jsonArray.add(customer);
        }
        jsonObject.put("Edges",jsonArray);
        jsonArray=new JSONArray();
        Iterator<NodeData> nodes=this.graph.nodeIter();
        while(nodes.hasNext()){
            NodeData node= nodes.next();
            customer =new HashMap<>();
            customer.put("pos",node.getLocation().x()+","+node.getLocation().y()+","+node.getLocation().z());
            customer.put("id",node.getKey());
            jsonArray.add(customer);
        }
            jsonObject.put("Nodes",jsonArray);
            PrintWriter pw = new PrintWriter("data/"+file);
            pw.write(jsonObject.toJSONString());
            pw.flush();
            pw.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try{
            this.graph = new MyDWG(file);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}