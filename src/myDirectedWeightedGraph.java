import java.util.HashMap;
import java.util.Iterator;

public class myDirectedWeightedGraph implements DirectedWeightedGraph {
    static int edgeSize;
    static int nodeSize;
    HashMap<Integer,NodeData> graph =new HashMap<>();
    HashMap<Integer, HashMap<Integer, EdgeData>> gi = new HashMap<>();



    @Override
    public NodeData getNode(int key) {
        NodeData node = graph.get(key);
        if (node == null)
        {
        return null;
        }
        return node;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n){
        if (!graph.containsKey(n.getKey())){
            graph.put(n.getKey(),n);
            gi.put(n.getKey(),new HashMap<>());
            nodeSize++;
        }

    }

    @Override
    public void connect(int src, int dest, double w) {
        myEdgeData edge = new myEdgeData(src,dest,w);
        edgeSize++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
