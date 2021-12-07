import java.util.HashMap;
import java.util.Map;

public class myNodeData implements NodeData{
    int key;
    myGeoLocation n;
    int tag;
    String info;

    public Map<Integer, EdgeData> EdgesIn; // key = node it's going to
    public Map<Integer,EdgeData> EdgesOut; // key = node it's came from

    public myNodeData(int key, GeoLocation n) {
        this.key = key;
        this.n = (myGeoLocation) n;
        EdgesIn = new HashMap<>();
        EdgesOut = new HashMap<>();
    }

    public myNodeData(NodeData n) {
        this.key = n.getKey();
        this.n =new myGeoLocation(n.getLocation().x(),n.getLocation().y(),n.getLocation().z());
    }


    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.n;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.n.x = p.x();
        this.n.y = p.y();
        this.n.z = p.z();
    }

    @Override
    public double getWeight() { // not use
        return 0;
    } //not use

    @Override
    public void setWeight(double w) { //not use
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }

    @Override
    public String toString() {
        return "myNodeData{" +
                "key=" + key +
                ", n=" + n +
                '}';
    }
}
