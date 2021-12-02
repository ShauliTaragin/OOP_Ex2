package implementaions;
import api.NodeData;
import org.w3c.dom.Node;

import java.util.*;
public class IsConnectedAlgo {
        // Member variables of this class
        private HashMap<Integer, Double> dist; //holds nodes in graph by key and their current weight for algorithm purpose
        private ArrayList<Integer> settled;
        private PriorityQueue<MyNode> pq;
        private HashMap<Integer, MyNode> nodes;
        private final Double INFINITY = Double.MAX_VALUE;
        // Constructor of this class
        public IsConnectedAlgo(int i, HashMap<Integer, MyNode> nodes) {
            // This keyword refers to current object itself
            this.nodes = nodes;
            this.dist = new HashMap<Integer, Double>();//this needs to be connected to
            this.settled = new ArrayList<Integer>();
            this.pq = new PriorityQueue<MyNode>(this.nodes.size(), new MyNode());
        }
        // Method 1
        // Dijkstra's Algorithm
        public void dijkstra(int src,MyDWG graph) {
            Iterator<NodeData> nodeIterator= graph.nodeIter();
            while(nodeIterator.hasNext()) {
                int node_key = nodeIterator.next().getKey();
                this.dist.put(node_key, INFINITY);
                this.nodes.put(node_key, (MyNode) graph.getNode(node_key));
            }
                this.dist.replace(src,0.0);

            // Add source node to the priority queue
            pq.add(new MyNode(graph.getNode(src)));

            // Distance to the source is 0
            while (settled.size() != V) {

                // Terminating ondition check when
                // the priority queue is empty, return
                if (pq.isEmpty()) {
                    return;
                }
                // Removing the minimum distance node
                // from the priority queue
                int u = pq.remove().getNode().getKey();

                // Adding the node whose distance is
                // finalized
                if (settled.contains(u)) {
                    // Continue keyword skips exwcution for
                    // following check
                    continue;
                }
                // We don't have to call e_Neighbors(u)
                // if u is already present in the settled set.
                settled.add(u);

                e_Neighbours(u);
            }
        }

        // Method 2
        // To process all the neighbours
        // of the passed node
        private void e_Neighbours(int u) {

            int edgeDistance = -1;
            int newDistance = -1;

            // All the neighbors of v
            for (int i = 0; i < adj.get(u).size(); i++) {
                Node v = adj.get(u).get(i);

                // If current node hasn't already been processed
                if (!settled.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = dist[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < dist[v.node])
                        dist[v.node] = newDistance;

                    // Add the current node to the queue
                    pq.add(new Node(v.node, dist[v.node]));
                }
            }
        }

    }
