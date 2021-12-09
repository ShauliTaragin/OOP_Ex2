package implementaions;

import api.EdgeData;
import api.NodeData;

import java.util.Comparator;
import java.util.HashMap;

public class MyNode implements Comparator<MyNode> {
    private NodeData node;
    private HashMap<Integer,EdgeData> ConnectedTo;//Integer->the i.d of the node we are connected to. EdgeD-> the edge between us.
    private HashMap<Integer,Double> ConnectedFrom;//Integer->the i.d of the node that connects to me. Double-> the weight of the edge between us.

    public MyNode(NodeData node, HashMap<Integer, EdgeData> ConnectedTo, HashMap<Integer,Double> ConnectedFrom){
        this.node=node;
        this.ConnectedTo= new HashMap<>();
        this.ConnectedFrom= new HashMap<>();
    }
    public MyNode(NodeData node){
        this.node=node;
        this.ConnectedTo= new HashMap<>();
        this.ConnectedFrom= new HashMap<>();
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

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * The implementor must ensure that {@link Integer#signum
     * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * compare(x, y)} must throw an exception if and only if {@code
     * compare(y, x)} throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
     * {@code compare(x, z)>0}.<p>
     * <p>
     * Finally, the implementor must ensure that {@code compare(x,
     * y)==0} implies that {@code signum(compare(x,
     * z))==signum(compare(y, z))} for all {@code z}.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     * @apiNote It is generally the case, but <i>not</i> strictly required that
     * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     */
    @Override
    public int compare(MyNode o1, MyNode o2) {
        if (o1.getNode().getWeight() < o2.getNode().getWeight()) {
            return -1;
        }
        if (o1.getNode().getWeight() > o2.getNode().getWeight()) {
            return 1;
        }
        return 0;
    }
}
