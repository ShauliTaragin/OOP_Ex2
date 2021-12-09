package implementaions;

import api.EdgeData;
import api.NodeData;
import java.util.Iterator;

public class DijkstraUsingMinHeap {
    static class Graph {
        Double max;
        MyDWG graph;
        Double [] heapNodes;
        Integer [] parents;
        public Graph(MyDWG graph) {
            MyDWGAlgo newGraph=new MyDWGAlgo();
            newGraph.init(graph);
            this.max=0.0;
            this.graph = (MyDWG) newGraph.copy();
        }
        public void dijkstra_GetMinDistances(int sourceVertex,int dest) {
            int index=0;
            this.max=0.0;
            double INFINITY = Double.MAX_VALUE;
            Iterator<NodeData> nodeIterator = this.graph.nodeIter();
            //find the node with the biggest key
            while (nodeIterator.hasNext()) {
                int node_key = nodeIterator.next().getKey();
                if(node_key>index){
                    index=node_key;
                }
            }
            boolean[] extracted = new boolean[index+1];
            this.heapNodes=new Double[index+1];
            this.parents=new Integer[index+1];
            this.parents[sourceVertex]=sourceVertex;
            //decrease the distance for the first index
            //add all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(this.graph.getNodes().size(), sourceVertex, this.graph,index+1);
            nodeIterator = this.graph.nodeIter();
            try {
                // iterate over the nodes and add them to the heap
                while (nodeIterator.hasNext()) {
                    int key = nodeIterator.next().getKey();
                    if (key != sourceVertex) {
                        minHeap.insert(this.graph.getMyNode(key));
                        this.heapNodes[key]=INFINITY;
                    }
                }
                this.heapNodes[sourceVertex]=0.0;
                //while minHeap is not empty
                while (!minHeap.isEmpty()) {
                    //extract the min
                    MyNode extractedNode = minHeap.extractMin();
                    //extracted vertex
                    int extractedNodeKey = extractedNode.getNode().getKey();
                    extracted[extractedNodeKey] = true;
                    if(extractedNodeKey==dest){
                        return;
                    }
                    //iterate through all the adjacent vertices
                    Iterator<EdgeData> edges = this.graph.edgeIter(extractedNodeKey);
                    while (edges.hasNext()) {
                        EdgeData edge = edges.next();
                        int destination = edge.getDest();
                        //only if destination vertex is not present in SPT
                        if (extracted[destination] == false) {
                            double newDest =this.heapNodes[extractedNodeKey]+edge.getWeight();
                            double currentDest =this.heapNodes[destination];
                            //if the currentDest is bigger then the newDest, update the min dist and update the parent
                            if (currentDest > newDest) {
                                decreaseKey(minHeap, newDest, destination);
                                this.heapNodes[destination]=newDest;
                                //switch the previous parent of the destination
                                this.parents[destination]=extractedNodeKey;
                            }
                        }
                    }
                }
                // the last Node to be extracted is the one with the biggest weight
                this.max =minHeap.nodeHolder[0].getNode().getWeight();
            }
            catch (Exception e){
                return;
            }
        }

        public void decreaseKey(MinHeap minHeap, double newKey, int vertex){
            //get the index which distance's needs a decrease;
            int index = minHeap.indexOfNodes[vertex];
            //get the node and update its value
            MyNode node = minHeap.nodeHolder[index];
            node.getNode().setWeight(newKey);
            minHeap.heapfyUp(index);
        }
    }
    //minheap class
    static class MinHeap{
        //number of nodes in the heap
        int numOfNodes;
        // the current nodes in the heap
        int currentHeapSize;
        // the array that holds the nodes for the heap
        MyNode[] nodeHolder;
        int [] indexOfNodes; //will be used to decrease the distance
        public MinHeap(int capacity, int key, MyDWG graph,int size) {
            this.numOfNodes = capacity;
            this.nodeHolder = new MyNode[capacity];
            this.indexOfNodes = new int[size];
            //init the src as the min node in the heap with weight 0
            this.nodeHolder[0] = (graph.getMyNode(key));
            this.nodeHolder[0].getNode().setWeight(0.0);
            this.currentHeapSize = 0;
        }
        //insert the node to the heap
        public void insert(MyNode x) {
            this.currentHeapSize++;
            int idx =this.currentHeapSize;
            this.nodeHolder[idx] = x;
            this.nodeHolder[idx].getNode().setWeight(Double.MAX_VALUE);
            this.indexOfNodes[x.getNode().getKey()] = idx;
            // put the node at the current index at his place in the heap
            heapfyUp(idx);
        }
        // simple heapfyUp
        public void heapfyUp(int pos) {
            int parentIdx = pos/2;
            int currentIdx = pos;
            while (currentIdx > 0 && this.nodeHolder[parentIdx].getNode().getWeight() > this.nodeHolder[currentIdx].getNode().getWeight()) {
                MyNode currentNode = this.nodeHolder[currentIdx];
                MyNode parentNode = this.nodeHolder[parentIdx];
                //swap the positions
                this.indexOfNodes[currentNode.getNode().getKey()] = parentIdx;
                this.indexOfNodes[parentNode.getNode().getKey()] = currentIdx;
                swap(currentIdx,parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx/2;
            }
        }
        // get the node with the minimum weight
        public MyNode extractMin() {
            MyNode min = this.nodeHolder[0];
            MyNode lastNode = this.nodeHolder[this.currentHeapSize];
            // update the indexes[] and move the last node to the top
            this.indexOfNodes[lastNode.getNode().getKey()] = 0;
            this.nodeHolder[0] = lastNode;
            this.nodeHolder[currentHeapSize] = null;
            sinkDown(0);
            this.currentHeapSize--;
            return min;
        }
        // push the node at the current index down
        public void sinkDown(int k) {
            int smallest = k;
            int leftChildIdx = 2 * k;
            int rightChildIdx = 2 * k+1;
            if (leftChildIdx < heapSize() && this.nodeHolder[smallest].getNode().getWeight() > this.nodeHolder[leftChildIdx].getNode().getWeight()) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heapSize() && this.nodeHolder[smallest].getNode().getWeight() > this.nodeHolder[rightChildIdx].getNode().getWeight()) {
                smallest = rightChildIdx;
            }
            if (smallest != k) {
                MyNode smallestNode = this.nodeHolder[smallest];
                MyNode kNode = this.nodeHolder[k];
                //swap the positions
                this.indexOfNodes[smallestNode.getNode().getKey()] = k;
                this.indexOfNodes[kNode.getNode().getKey()] = smallest;
                swap(k, smallest);
                sinkDown(smallest);
            }
        }
        //swap the two nodes
        public void swap(int a, int b) {
            MyNode temp = this.nodeHolder[a];
            this.nodeHolder[a] = this.nodeHolder[b];
            this.nodeHolder[b] = temp;
        }
        //check if the heap is empty
        public boolean isEmpty() {
            return this.currentHeapSize == 0;
        }
        //return the heapSize
        public int heapSize(){
            return this.currentHeapSize;
        }
    }
}