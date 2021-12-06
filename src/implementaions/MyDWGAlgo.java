package implementaions;

import api.DirectedWeightedGraph;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
        IsConnectedAlgo is_graph_connected_algo = new IsConnectedAlgo(this.graph.getNodes());
        if(!is_graph_connected_algo.bfs(this.graph)){
            return false;
        }
        MyDWG reveresd_graph = is_graph_connected_algo.reverse(this.graph);
        if(!is_graph_connected_algo.bfs(reveresd_graph)){
            return false;
        }
        return true;
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
        IsConnectedAlgo shortest_path_algo =new IsConnectedAlgo(this.graph.getNodes());
        ArrayList<Double> ans=new ArrayList<Double>();
        ans=shortest_path_algo.dijkstra(src,this.graph,1,dest);
        return ans.get(0);
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
        IsConnectedAlgo shortest_path_algo =new IsConnectedAlgo(this.graph.getNodes());
        ArrayList<Integer> ans=new ArrayList<Integer>();
        ans=shortest_path_algo.dijkstra(src,this.graph,3,dest);
        ArrayList<NodeData> nodes =new ArrayList<>();
        for(int i =0;i<ans.size();i++){
            nodes.add(this.graph.getNode(ans.get(i)));
        }
        return nodes;
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        if(!isConnected())
            return null;
        DijkstraUsingMinHeap.Graph g=  new DijkstraUsingMinHeap.Graph(this.graph);
        double min = Double.MAX_VALUE;
        int key_holder = -1;
        Iterator<NodeData> iterator =this.getGraph().nodeIter();
        while(iterator.hasNext()) {
            NodeData node=iterator.next();
            g.dijkstra_GetMinDistances(node.getKey());
            System.out.println(node.getKey()+" is-"+g.max);
            if(g.max < min){
                min = g.max;
                key_holder = node.getKey();
            }
        }
        return this.graph.getNode(key_holder);
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
        IsConnectedAlgo tsp_algo =new IsConnectedAlgo(this.graph.getNodes());
        return tsp_algo.Shortest_path_in_given_nodes(cities, this.graph);
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
        return false;
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
