package implementaions;
import api.NodeData;
import org.w3c.dom.Node;

import java.util.*;
public class IsConnectedAlgo {
    // Member variables of this class
    private HashMap<Integer,HashMap<Integer, Double> >dist; //holds nodes in graph by key and their current weight for algorithm purpose
    private ArrayList<Integer> settled;
    private PriorityQueue<MyNode> pq;
    private HashMap<Integer, MyNode> nodes;
    private final Double INFINITY = Double.MAX_VALUE;
    // Constructor of this class
    public IsConnectedAlgo(int i, HashMap<Integer, MyNode> nodes) {
        // This keyword refers to current object itself
        this.nodes = nodes;
        this.dist = new HashMap<>();//this needs to be connected to
        this.settled = new ArrayList<>();
        this.pq = new PriorityQueue<>(this.nodes.size(), new MyNode());
    }
    // Method 1
    // Dijkstra's Algorithm
    public ArrayList dijkstra(int src,MyDWG graph,int func,int dest) {
        Iterator<NodeData> nodeIterator= graph.nodeIter();
        HashMap<Integer, Double> temp=new HashMap<>();
        while(nodeIterator.hasNext()) {
            int node_key = nodeIterator.next().getKey();
            temp.put(node_key,INFINITY);
            this.nodes.put(node_key, (MyNode) graph.getNode(node_key));
        }
            temp.replace(src,0.0);
        this.dist.put(0,temp);
        // Add source node to the priority queue
        MyNode node=new MyNode(graph.getNode(src));
        node.getNode().setWeight(0.0);
        this.pq.add(node);
        // Distance to the source is 0
        while (this.settled.size() != graph.nodeSize()) {
            int u = this.pq.remove().getNode().getKey();
            if(func==1&&u==dest){
                return shortestDistTo(dest);
            }
            if(func==2&&u==dest){
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
            MyNode node1=new MyNode(graph.getNode(u));
            node1.getNode().setWeight(0.0);
            this.pq.add(node1);
        }
        switch (func){
            case 0:
                return checkIfConnect();
            case 1:
                return shortestDistTo(dest);
            default:
                return nodeInShortestPath(src, dest);
        }
    }
        // Method 2
        // To process all the neighbours
        // of the passed node
    private void e_Neighbours(int u,HashMap<Integer,Double> temp) {
        double edgeDistance = -1;
        double newDistance = -1;
        HashMap<Integer,Double> ans=temp;
        ans.remove(u);
        // All the neighbors of v
        for(Integer i:this.nodes.get(u).getConnectedTo().keySet()){
            // If current node hasn't already been processed
            if (!this.settled.contains(i)) {
                edgeDistance = this.nodes.get(u).getConnectedTo().get(i).getWeight();
                newDistance = temp.get(u) + edgeDistance;
                // If new distance is cheaper in cost
                if (newDistance < temp.get(i)) {
                    ans.replace(i, newDistance);
                }
            }
        }
        this.dist.put(this.dist.size(),ans);
    }
    private ArrayList<Integer> checkIfConnect(){
        ArrayList<Integer> ans=new ArrayList<>();
        for(Double val:this.dist.get(this.dist.size()-1).values()){
            if(val==INFINITY){
                ans.add(-1);
                return ans;
            }
        }
        ans.add(0);
        return ans;
    }
    private ArrayList<Double> shortestDistTo(int dest){
        ArrayList<Double> ans=new ArrayList<>();
        for(int i=this.dist.size()-1;i>=0;i--){
            if(this.dist.get(i).containsKey(dest)){
                ans.add(this.dist.get(i).get(dest));
                return ans;
            }
        }
        ans.add(INFINITY);
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
        return ans;
    }
}

