package implementaions;

import api.EdgeData;
import api.NodeData;

import java.util.HashMap;

public class MyNode {
    private NodeData node;
    private HashMap<Integer,EdgeData> ConnectedTo;//Integer->the i.d of the node we are connected to. EdgeD-> the edge between us.
    private HashMap<Integer,Double> ConnectedFrom;//Integer->the i.d of the node that connects to me. Double-> the weight of the edge between us.

    public MyNode(NodeData node, HashMap<Integer, EdgeData> ConnectedTo, HashMap<Integer,Double> ConnectedFrom){
        this.node=node;
        this.ConnectedTo= new HashMap<Integer,EdgeData>();
        this.ConnectedFrom= new HashMap<Integer,Double>();
    }
    public MyNode(NodeData node){
        this.node=node;
        this.ConnectedTo= new HashMap<Integer,EdgeData>();
        this.ConnectedFrom= new HashMap<Integer,Double>();
    }
    public NodeData getNode(){
        return this.node;
    }
    public HashMap<Integer, EdgeData> getConnectedTo() {
        return this.ConnectedTo;
    }
    public HashMap<Integer, Double> getConnectedFrom() {
        return this.ConnectedFrom;
    }
    public void AddConnectedTo(EdgeData new_edge_to_add) {
        int dst = new_edge_to_add.getDest();
        this.ConnectedTo.put(dst,new_edge_to_add);
    }
    public void AddConnectedFrom(int src, Double Weight) {
        this.ConnectedFrom.put(src,Weight);
    }
    public void setWeight(int key,double weight){
        this.ConnectedFrom.replace(key,weight);
    }
}
