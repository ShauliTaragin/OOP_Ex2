package implementaions;

import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class DijkstraUsingMinHeap {
    static class Graph {
        Double max;
        MyDWG graph;
        HashMap<Integer, Integer> parents;
        HashMap<Integer, Double> heapNodes;

        public Graph(MyDWG graph) {
            MyDWGAlgo newGraph=new MyDWGAlgo();
            newGraph.init(graph);
            this.max=0.0;
            this.heapNodes=new HashMap<Integer, Double>();
            this.graph = (MyDWG) newGraph.copy();
            this.parents = new HashMap<Integer, Integer>();
            //initialize adjacency lists for all the vertices
        }
        public void dijkstra_GetMinDistances(int sourceVertex) {
            int index=0;
            this.max=0.0;
            this.heapNodes.clear();
            this.parents.clear();
            double INFINITY = Double.MAX_VALUE;
            boolean[] SPT = new boolean[this.graph.getNodes().size()];
            Iterator<NodeData> nodeIterator = this.graph.nodeIter();
            while (nodeIterator.hasNext()) {
                int node_key = nodeIterator.next().getKey();
                this.heapNodes.put(node_key, INFINITY);
            }
            this.parents.put(sourceVertex,sourceVertex);
            this.heapNodes.replace(sourceVertex, 0.0);
            //decrease the distance for the first index
            //add all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(this.graph.getNodes().size(), sourceVertex, this.graph);
            nodeIterator = this.graph.nodeIter();
            try {
                while (nodeIterator.hasNext()) {
                    int key = nodeIterator.next().getKey();
                    if (key != sourceVertex) {
                        minHeap.insert(this.graph.getMyNode(key));
                    }
                }
                //while minHeap is not empty
                while (!minHeap.isEmpty()) {
                    //extract the min
                    MyNode extractedNode = minHeap.extractMin();

                    //extracted vertex
                    int extractedVertex = extractedNode.getNode().getKey();
                    SPT[extractedVertex] = true;

                    //iterate through all the adjacent vertices
                    Iterator<EdgeData> edges = this.graph.edgeIter(extractedVertex);
                    while (edges.hasNext()) {
                        EdgeData edge = edges.next();
                        int destination = edge.getDest();
                        //only if destination vertex is not present in SPT
                        if (SPT[destination] == false) {
                            ///check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance
                            double newKey = this.heapNodes.get(extractedVertex) + edge.getWeight();
                            double currentKey = this.heapNodes.get(destination);
                            if (currentKey > newKey) {
                                decreaseKey(minHeap, newKey, destination);
                                this.heapNodes.replace(destination, newKey);
                                this.parents.put(destination,extractedVertex);
                            }
                        }
                    }
                }
                this.max =minHeap.mH[0].getNode().getWeight();
                //print SPT
                //printDijkstra(this.heapNodes, sourceVertex);
            }
            catch (Exception e){
                return;
            }
        }

        public void decreaseKey(MinHeap minHeap, double newKey, int vertex){

            //get the index which distance's needs a decrease;
            int index = minHeap.indexes[vertex];

            //get the node and update its value
            MyNode node = minHeap.mH[index];
            node.getNode().setWeight(newKey);
            minHeap.bubbleUp(index);
        }

        public void printDijkstra(HashMap<Integer,Double> resultSet, int sourceVertex) {
            System.out.println("Dijkstra Algorithm: (Adjacency List + Min Heap)");
            for(Integer key:resultSet.keySet()){
                System.out.println("Source Vertex: " + sourceVertex + " to vertex " +key +
                        " distance: " + resultSet.get(key));
            }

        }
    }
    static class MinHeap{
        int capacity;
        int currentSize;
        MyNode[] mH;
        int [] indexes; //will be used to decrease the distance


        public MinHeap(int capacity, int key, MyDWG graph) {
            this.capacity = capacity;
            this.mH = new MyNode[capacity];
            this.indexes = new int[capacity];
            this.mH[0] = (graph.getMyNode(key));
            this.mH[0].getNode().setWeight(0.0);
            this.currentSize = 0;
        }
        public void display() {
            for (int i = 0; i <=currentSize; i++) {
                System.out.println(" " + mH[i].getNode().getKey() + " distance " + mH[i].getNode().getWeight());
            }
            System.out.println("________");
        }

        public void insert(MyNode x) {
            this.currentSize++;
            int idx =this.currentSize;
            this.mH[idx] = x;
            this.mH[idx].getNode().setWeight(Double.MAX_VALUE);
            this.indexes[x.getNode().getKey()] = idx;
            bubbleUp(idx);
        }

        public void bubbleUp(int pos) {
            int parentIdx = pos/2;
            int currentIdx = pos;
            while (currentIdx > 0 && mH[parentIdx].getNode().getWeight() > mH[currentIdx].getNode().getWeight()) {
                MyNode currentNode = mH[currentIdx];
                MyNode parentNode = mH[parentIdx];

                //swap the positions
                indexes[currentNode.getNode().getKey()] = parentIdx;
                indexes[parentNode.getNode().getKey()] = currentIdx;
                swap(currentIdx,parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx/2;
            }
        }

        public MyNode extractMin() {
            MyNode min = mH[0];
            MyNode lastNode = mH[currentSize];
// update the indexes[] and move the last node to the top
            indexes[lastNode.getNode().getKey()] = 0;
            mH[0] = lastNode;
            mH[currentSize] = null;
            sinkDown(0);
            this.currentSize--;
            return min;
        }

        public void sinkDown(int k) {
            int smallest = k;
            int leftChildIdx = 2 * k;
            int rightChildIdx = 2 * k+1;
            if (leftChildIdx < heapSize() && mH[smallest].getNode().getWeight() > mH[leftChildIdx].getNode().getWeight()) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heapSize() && mH[smallest].getNode().getWeight() > mH[rightChildIdx].getNode().getWeight()) {
                smallest = rightChildIdx;
            }
            if (smallest != k) {

                MyNode smallestNode = mH[smallest];
                MyNode kNode = mH[k];

                //swap the positions
                indexes[smallestNode.getNode().getKey()] = k;
                indexes[kNode.getNode().getKey()] = smallest;
                swap(k, smallest);
                sinkDown(smallest);
            }
        }

        public void swap(int a, int b) {
            MyNode temp = mH[a];
            mH[a] = mH[b];
            mH[b] = temp;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public int heapSize(){
            return currentSize;
        }
    }
}