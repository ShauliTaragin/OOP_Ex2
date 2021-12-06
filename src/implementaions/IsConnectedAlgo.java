package implementaions;
import api.NodeData;
import org.w3c.dom.Node;

import java.util.*;
public class IsConnectedAlgo {
    // Member variables of this class
    private HashMap<Integer,HashMap<Integer, Double> >dist; //holds nodes in graph by key and their current weight for algorithm purpose
    private ArrayList<Integer> settled;
    private TreeSet<MyNode> ts;
    private HashMap<Integer, MyNode> nodes;
    private final Double INFINITY = Double.MAX_VALUE;
    private HashMap<Integer, Double> temp;
    private double max;
    private double maxNode;
    // Constructor of this class
    public IsConnectedAlgo( HashMap<Integer, MyNode> nodes) {
        // This keyword refers to current object itself
        this.temp=new HashMap<>();
        this.nodes = nodes;
        this.dist = new HashMap<>();//this needs to be connected to
        this.settled = new ArrayList<>();
        this.ts = new TreeSet(new MyNode());
        this.max=0;
        this.maxNode=0;

    }
    // Dijkstra's Algorithm
    public ArrayList dijkstra(int src,MyDWG graph,int func,int dest) {
        Iterator<NodeData> nodeIterator= graph.nodeIter();
        this.max=0;
        this.maxNode=0;
        while(nodeIterator.hasNext()) {
            int node_key = nodeIterator.next().getKey();
            this.temp.put(node_key,INFINITY);
            MyNode node1=new MyNode(this.nodes.get(node_key).getNode());
            node1.getNode().setWeight(INFINITY);
            this.ts.add(node1);
            //this.pq.add(new MyNode(this.nodes.get(node_key).getNode()));
            //this.nodes.put(node_key, (MyNode) graph.getNode(node_key));
        }
        this.temp.replace(src,0.0);
        this.dist.put(0,this.temp);
        // Add source node to the priority queue
        MyNode node=new MyNode(this.nodes.get(src).getNode());
        node.getNode().setWeight(0.0);
        this.ts.add(node);
        // Distance to the source is 0
        while (this.settled.size() != graph.nodeSize()) {
            if(this.ts.isEmpty()){
                ArrayList<Integer> ans=new ArrayList<>();
                ans.add(-1);
                return ans;
            }
            int u = this.ts.pollFirst().getNode().getKey();
            if(func==1&&u==dest){
                return shortestDistTo(dest);
            }
            if(func==3&&u==dest){
                return nodeInShortestPath(src, dest);
            }
            // Adding the node whose distance is
            // finalized
            if (this.settled.contains(u)) {
                continue;
            }
            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            this.settled.add(u);
            e_Neighbours(u,this.dist.get(this.dist.size()-1));
//            MyNode node1=new MyNode(graph.getNode(u));
//            node1.getNode().setWeight(0.0);
//            this.pq.add(node1);
            if(this.temp.get(u)>this.maxNode){
                this.maxNode=this.temp.get(u);
            }

        }

        switch (func){
            case 0:
                return checkIfConnect();
            case 1:
                return shortestDistTo(dest);
            case 2:
                return findGraphCenter();
            default:
                return nodeInShortestPath(src, dest);
        }
    }
    // Method 2
    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(int u,HashMap<Integer,Double> temp1) {
        double edgeDistance = -1;
        double newDistance = -1;
        HashMap<Integer,Double> ans= (HashMap<Integer, Double>) temp1.clone();
        // All the neighbors of v
        for(Integer i:this.nodes.get(u).getConnectedTo().keySet()){
            // If current node hasn't already been processed
            if (!this.settled.contains(i)) {
                edgeDistance = this.nodes.get(u).getConnectedTo().get(i).getWeight();
                newDistance = temp1.get(u) + edgeDistance;
                // If new distance is cheaper in cost
                if (newDistance < temp1.get(i)) {
                    ans.replace(i, newDistance);
                    MyNode node1=new MyNode(this.nodes.get(i).getNode());
                    node1.getNode().setWeight(newDistance);
                    this.ts.add(node1);
                    this.temp.replace(i,newDistance);
                    if(this.max<=newDistance){
                        this.max=newDistance;
                    }
                }
            }
        }
        int distSize=this.dist.size();
        this.dist.put(this.dist.size(),ans);
        this.dist.get(distSize).remove(u);
    }
    private ArrayList<Integer> checkIfConnect(){
        ArrayList<Integer> ans=new ArrayList<>();
            if(this.maxNode==(INFINITY)){
                ans.add(-1);
                return ans;
            }
        this.ts.clear();
        this.dist.clear();
        this.settled.clear();
        this.temp.clear();
        ans.add(0);
        return ans;
    }
    private ArrayList<Double> shortestDistTo(int dest){
        ArrayList<Double> ans=new ArrayList<>();
        ans.add(temp.get(dest));
        this.ts.clear();
        this.dist.clear();
        this.settled.clear();
        this.temp.clear();
        return ans;
    }
    private ArrayList<Integer> nodeInShortestPath(int src,int dest){
        ArrayList<Integer> ans=new ArrayList<>();
        ans.add(dest);
        int pointer=dest;
        for(int i=this.dist.size()-1;i>=0;i--) {
            if(i!=0) {
                if(this.dist.get(i).get(pointer).equals(this.dist.get(i-1).get(pointer))){
                    if(!ans.contains(pointer)){
                        ans.add(0,pointer);
                    }
                }
                else {
                    double temp=Double.MAX_VALUE;
                    for (Integer key : this.dist.get(i-1).keySet()) {
                        if (this.dist.get(i-1).get(key)<temp){
                            temp=this.dist.get(i-1).get(key);
                            pointer=key;
                        }
                    }
                    if(!ans.contains(pointer)){
                        ans.add(0,pointer);
                    }
                }
            }
        }
        if(!ans.contains(src)) {
            ans.add(0, src);
        }
        this.ts.clear();
        this.dist.clear();
        this.settled.clear();
        this.temp.clear();
        return ans;
    }
    private ArrayList findGraphCenter(){
        ArrayList<Double> ans=new ArrayList<>();
//        double max = 0.;
//        for (Double value : this.temp.values()) {
//            if(value > max) {
//                max = value;
//            }
//        }
        this.ts.clear();
        this.dist.clear();
        this.settled.clear();
        this.temp.clear();
        ans.add(this.max);
        return ans;
    }
    public List<NodeData> Shortest_path_in_given_nodes(List<NodeData> cities,MyDWG graph){
        //if(!findPath(cities,graph))
        if (false)
        {
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
            while (!holdCities.isEmpty()) {
                double minDist = Double.MAX_VALUE;
                for (int i = 0; i < holdCities.size(); i++) {
                    ArrayList<Double> ans = new ArrayList<Double>();
                    ans = dijkstra(src, graph, 1, holdCities.get(i).getKey());
                    double dist = ans.get(0);
                    if (dist < minDist) {
                        minDist = dist;
                        currentdest = holdCities.get(i).getKey();
                        destI = i;
                    }
                }
                current+=minDist;
                ArrayList<Integer> tempPath = dijkstra(src, graph, 3, currentdest);
                if (tempPath == null) return null;
                boolean flag_first = true;
                for (Integer n : tempPath) {
                    if (flag_first) {
                        flag_first = false;
                    } else {
                        path.add(graph.getNode(n));
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
     * regular dfs using tag of each node to mark whether the node as been visited or not
     * @param graph
     * map:
     * 0 -> not_visited
     * 1 -> visited
     *
     * @return
     */
    public boolean bfs(MyDWG graph){
        boolean flag = true;
        Iterator<NodeData> iterator = graph.nodeIter();
        while(iterator.hasNext()){ //first lets set tag of all nodes to 0 e.g not visited
            iterator.next().setTag(0);
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        //get first node and run bfs from it
        for(Integer key : graph.getNodes().keySet()) {
            if (!flag){
                break;
            }
            flag=false;
            NodeData src =  graph.getNode(key);
            queue.add(key);
            src.setTag(1);
        }
        while(!queue.isEmpty()){
            Integer current_nodes_key = queue.peek();
            queue.poll();
            for (Integer neigbor_key : graph.getNodes().get(current_nodes_key).getConnectedTo().keySet()) {
                NodeData current_neigbor_node = graph.getNode(neigbor_key);
                if (current_neigbor_node.getTag()==0){
                    current_neigbor_node.setTag(1);
                    queue.add(neigbor_key);
                }
            }
        }
        //now we check if dfs returns false when starting from first node
        Iterator<NodeData> iterator2 = graph.nodeIter();
        while(iterator2.hasNext()){ //if we find for some node that its tag is 0 e.g hasn't been visited then return false.
            if(iterator2.next().getTag()==0){
                return false;
            }
        }
        return true;
    }

    public MyDWG reverse(MyDWG graph){
        MyDWG reversed_graph =  new MyDWG();
        for(Integer connected_key : graph.getNodes().keySet()){//traverse through each node
            if(!reversed_graph.getNodes().containsKey(connected_key)) {//only if graph dosent already have the node then add it
                NodeData src_bfr_reverse = graph.getNode(connected_key);
                reversed_graph.addNode(src_bfr_reverse);
            }
            for (Integer neighbor_of_connected_key:graph.getNodes().get(connected_key).getConnectedTo().keySet()){//traverse through edges coming out of each node
                if(!reversed_graph.getNodes().containsKey(neighbor_of_connected_key)) {//only if graph dosent already have the node then add it
                    NodeData dst_bfr_reverse= graph.getNode(neighbor_of_connected_key);
                    reversed_graph.addNode(dst_bfr_reverse);
                }
                double weight_of_reversed_edge = graph.getNodes().get(neighbor_of_connected_key).getConnectedFrom().get(connected_key);
                reversed_graph.connect(neighbor_of_connected_key , connected_key ,weight_of_reversed_edge );
            }
        }
        return reversed_graph;
    }
}