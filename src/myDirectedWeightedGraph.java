import java.util.HashMap;
import java.util.Iterator;

public class myDirectedWeightedGraph implements DirectedWeightedGraph {
    static int edgeSize;
    static int nodeSize;
    HashMap<Integer,NodeData> nodes;
    HashMap<Integer,EdgeData> edge;

    public myDirectedWeightedGraph() {
        this.nodes = new HashMap<>();
        this.edge = new HashMap<>();
    }

    public void MapEdgesToNodes() {
        try {
            for (EdgeData edge : this.edge.values()) {
                ((myNodeData) nodes.get(edge.getSrc())).EdgesOut.put(edge.getDest(), edge);
                ((myNodeData) nodes.get(edge.getDest())).EdgesIn.put(edge.getSrc(), edge);
            }
        } catch (Exception e) {
            System.out.println("The graph might not initialized");
        }
    }

    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n){
        if (!nodes.containsKey(n.getKey())){
            nodes.put(n.getKey(),n);
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

    @Override
    public String toString() {
        return "myDirectedWeightedGraph{" +
                "nodes=" + nodes +
                ", edge=" + edge +
                '}';
    }
}
