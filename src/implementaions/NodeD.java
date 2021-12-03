package implementaions;

import api.GeoLocation;

import java.util.HashMap;

public class NodeD implements api.NodeData{
    //implemented methods
    private final int key;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;
    //our NodeD's properties
    public NodeD(int key , GeoLocation location,double Weight,String info,int tag ) {
        this.key=key;
        this.location=location;
        this.weight=Weight;
        this.info=info;
        this.tag=tag;
    }
    public NodeD(int key , GeoLocation location) {
        this.key=key;
        this.location=location;
        this.weight=0.0;
        this.info="";
        this.tag=0;
    }
    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }
    /**
     * Returns the location of this node, if none return null.
     *
     * @return
     */
    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    /**
     * Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(GeoLocation p) {
        this.location=p;
    }
    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }
    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight=w;
    }
    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
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
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}
