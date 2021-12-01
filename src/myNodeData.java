import java.util.Map;

public class myNodeData implements NodeData{
    int key;
    GeoLocation n;
    int tag;
    String info;

    public Map<Integer, EdgeData> EdgesIn; // key = node it's going to
    public Map<Integer,EdgeData> EdgesOut; // key = node it's came from

    public myNodeData(int key, GeoLocation n) {
        this.key = key;
        this.n = n;
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
        this.n = p;
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
