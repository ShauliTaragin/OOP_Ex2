package implementaions;

import api.EdgeData;

public class EdgeD implements api.EdgeData {
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    public EdgeD(int src, int dest, double weight,String info,int tag){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.info=info;
        this.tag=tag;
    }
    public EdgeD(int src,int dest,double weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.info="";
        this.tag=0;
    }
    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return null;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info=s;
    }
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}
