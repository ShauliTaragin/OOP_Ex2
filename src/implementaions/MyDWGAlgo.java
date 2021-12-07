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
        if(!HelperAlgo.bfs(this.graph)){
            return false;
        }
        MyDWG reveresd_graph = HelperAlgo.reverse(this.graph);
        if(!HelperAlgo.bfs(reveresd_graph)){
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
        DijkstraUsingMinHeap.Graph g=new DijkstraUsingMinHeap.Graph(this.graph);
        g.dijkstra_GetMinDistances(src);
//        return g.heapNodes.get(dest);
        return g.heapNodes[dest];
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
        DijkstraUsingMinHeap.Graph g=new DijkstraUsingMinHeap.Graph(this.graph);
        g.dijkstra_GetMinDistances(src);
        ArrayList<NodeData> nodes =new ArrayList<>();
        int key=dest;
        while(key!=src) {
            nodes.add(0,this.graph.getNode(key));
//            key=g.parents.get(key);
            key=g.parents[key];
        }
        nodes.add(0,this.graph.getNode(src));
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
//            System.out.println(node.getKey()+" is-"+g.max);
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
        if(!HelperAlgo.findPath(cities,graph)) {
            return null;
        }
        List<NodeData> bestPath=new ArrayList<>();
        double minPath =Double.MAX_VALUE;
        for(int j=0;j<cities.size();j++) {
            ArrayList<NodeData> holdCities=new ArrayList<>(cities);
            double current=0;
            List<NodeData> path=new ArrayList<NodeData>();
            int srcI = j;
            int src = cities.get(srcI).getKey();
            int destI = 0, currentdest = 0;
            holdCities.remove(srcI);
            path.add(graph.getNode(src));
            double ans ;
            while (!holdCities.isEmpty()) {
                double minDist = Double.MAX_VALUE;
                for (int i = 0; i < holdCities.size(); i++) {
                    ans = shortestPathDist(src, holdCities.get(i).getKey());
                    double dist = ans;
                    if (dist < minDist) {
                        minDist = dist;
                        currentdest = holdCities.get(i).getKey();
                        destI = i;
                    }
                }
                current+=minDist;
                ArrayList<NodeData> tempPath = (ArrayList<NodeData>) shortestPath(src , currentdest);
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
            if(current<minPath)
            {
                minPath=current;
                bestPath=path;
            }
        }
        return bestPath;
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