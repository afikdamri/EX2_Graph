import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class myDirectedWeightedGraph implements DirectedWeightedGraph {
     private HashMap<Integer,NodeData> nodes;
     private HashMap<Vector<Integer>,EdgeData> edges;
     private HashMap<Integer,frTo> edgesOfNode;
     private int Mc;
     int node_size;
     int edge_size;

    public myDirectedWeightedGraph()
    {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.edgesOfNode = new HashMap<>();
        Mc = 0;
        edge_size = 0;
        node_size = 0;
    }

    private class frTo {
        public HashMap<Integer, EdgeData> fromMe;
        public HashMap<Integer, EdgeData> toMe;
        public frTo() {
            this.fromMe = new HashMap<>();
            this.toMe = new HashMap<>();
        }
    }

    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        Vector<Integer> v = new Vector<>();
        v.add(src);
        v.add(dest);
        return edges.get(v);
    }

    @Override
    public void addNode(NodeData n){
        if (nodes.get(n.getKey()) != null) {
            throw new RuntimeException("The Node key already exists");
        }
        nodes.put(n.getKey(),(myNodeData)n);
        edgesOfNode.put(n.getKey(),new frTo());
        node_size++;
        Mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(nodes.get(dest) == null || this.nodes.get(src) == null)
        {
            throw new RuntimeException("The source or destination does not exist");
        }
        myEdgeData edge = new myEdgeData(src,dest,w);
        Vector<Integer> v = new Vector<>();
        v.add(src);
        v.add(dest);
        edges.put(v,edge);
        edgesOfNode.get(src).fromMe.put(dest,edge);
        edgesOfNode.get(dest).toMe.put(src ,edge);
        edge_size++;
        Mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return edges.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return edgesOfNode.get(node_id).fromMe.values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        if (nodes.get(key)==null)
        {
            throw new RuntimeException("The node does not exist");
        }
        NodeData node = nodes.remove(key);
        for (EdgeData e : edgesOfNode.get(key).fromMe.values()) {
            Vector<Integer> v = new Vector<>();
            v.add(e.getSrc());
            v.add(e.getDest());
            edges.remove(v);
            edgesOfNode.get(e.getDest()).toMe.remove(key);
        }
        for (EdgeData e : edgesOfNode.get(key).toMe.values()) {
            Vector<Integer> v = new Vector<>();
            v.add(e.getSrc());
            v.add(e.getDest());
            edges.remove(v);
            edgesOfNode.get(e.getSrc()).fromMe.remove(key);
        }
        edgesOfNode.remove(key);
        Mc++;
        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (edgesOfNode.get(src) == null || edgesOfNode.get(dest) ==null)
        {
            throw new RuntimeException("The edge does not exist");
        }
        edgesOfNode.get(src).fromMe.remove(dest);
        edgesOfNode.get(dest).toMe.remove(src);
        Vector<Integer> v = new Vector<>();
        v.add(src);
        v.add(dest);
        Mc++;
        return edges.remove(v);
    }

    @Override
    public int nodeSize() {
        return node_size;
    }

    @Override
    public int edgeSize() {
        return edge_size;
    }

    @Override
    public int getMC() {
        return Mc;
    }

    @Override
    public String toString() {
        return "myDirectedWeightedGraph{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}
