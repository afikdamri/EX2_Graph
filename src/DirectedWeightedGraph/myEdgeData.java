package DirectedWeightedGraph;

public class myEdgeData implements EdgeData{
    int src;
    int dest;
    double w;

    public myEdgeData(int src, int dest, double w)
    {
        this.src = src;
        this.dest = dest;
        this.w = w;
    }

    public myEdgeData(myEdgeData n)
    {
        this.src = n.src;
        this.dest = n.dest;
        this.w = n.w;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest ;
    }

    @Override
    public double getWeight(){
        return w;
    }

    @Override
    public String getInfo(){
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
        return "DirectedWeightedGraph.myEdgeData{" +
                "src=" + src +
                ", dest=" + dest +
                ", w=" + w +
                '}';
    }
}
