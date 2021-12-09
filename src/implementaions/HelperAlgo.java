package implementaions;
import api.NodeData;
import org.w3c.dom.Node;

import java.util.*;
public class HelperAlgo {

    public static boolean findPath(List<NodeData> nodes,MyDWG graph){
        MyDWGAlgo copy_graph=new MyDWGAlgo();
        boolean flag1 = true;
        try {
            copy_graph.init(graph);
            List<Integer> keys = new ArrayList<>();
            Iterator<NodeData> nodeIter = graph.nodeIter();
            while (nodeIter.hasNext()) {
                keys.add(nodeIter.next().getKey());
            }
            boolean src_node = false;
            int src_node_key = 0;
            Iterator<NodeData> nodeiter2 = copy_graph.getGraph().nodeIter();
            while (nodeiter2.hasNext() && flag1) {
                //int key=nodeiter2.next().getKey();
                NodeData key = nodeiter2.next();
                if (nodes.contains(key)) {
                    src_node_key = key.getKey();
                    src_node = true;
                }
                flag1 = false;
            }
            if (src_node == true) { // now we check if there is a path between src
                copy_graph.isConnected();
                flag1 = true;
                for (Integer key : keys) {
                    if (key != src_node_key && graph.getNode(key).getTag() != 1 && nodes.contains(key)) {
                        return false;
                    }
                }
            }
            copy_graph.isConnected();
            for (int i = 0; i < keys.size(); i++) {
                Integer key_currnet = keys.get(i);
                if (key_currnet != src_node_key && nodes.contains(key_currnet) && graph.getNode(key_currnet).getTag() != 1) {
                    return false;
                }
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
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
    public static boolean bfs(MyDWG graph){
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

    public static MyDWG reverse(MyDWG graph){
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