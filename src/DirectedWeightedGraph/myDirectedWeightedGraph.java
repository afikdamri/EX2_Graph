import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class myDirectedWeightedGraph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes;
    private HashMap<Vector<Integer>, EdgeData> edges;
    private HashMap<Integer, frTo> edgesOfNode;
    private int Mc;


    public myDirectedWeightedGraph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.edgesOfNode = new HashMap<>();
        Mc = 0;
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
    public void addNode(NodeData n) {
        if (nodes.get(n.getKey()) != null) {
            throw new RuntimeException("The Node key already exists");
        }
        nodes.put(n.getKey(), n);
        edgesOfNode.put(n.getKey(), new frTo());
        Mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (nodes.get(dest) == null || this.nodes.get(src) == null) {
            throw new RuntimeException("The source or destination does not exist");
        }
        myEdgeData edge = new myEdgeData(src, dest, w);
        Vector<Integer> v = new Vector<>();
        v.add(src);
        v.add(dest);
        edges.put(v, edge);
        edgesOfNode.get(src).fromMe.put(dest, edge);
        edgesOfNode.get(dest).toMe.put(src, edge);
        Mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return new Node_Iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Edge_Iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Edge_Iterator_OfNode(node_id);
    }

    @Override
    public NodeData removeNode(int key) {
        if (nodes.get(key) == null) {
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
        if (edgesOfNode.get(src) == null || edgesOfNode.get(dest) == null) {
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
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
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
        //iterator for the Edges
        public class Edge_Iterator implements Iterator<EdgeData> {
        private int mc;
        private final Iterator<EdgeData> edgeIter;
        private EdgeData edge;
        public Edge_Iterator() {
            mc = Mc;
            edgeIter = edges.values().iterator();
        }

        private void isValide() {
            if (mc != Mc) {
                throw new RuntimeException("the iterator has been change!");
            }
        }
        @Override
        public boolean hasNext() {
            isValide();
            return edgeIter.hasNext();
        }

        @Override
        public EdgeData next() {
            isValide();
            edge = edgeIter.next();
            return edge;
        }
        @Override
        public void remove() {
            isValide();
            mc++;
            edgeIter.remove();
            removeEdge(edge.getSrc(), edge.getDest());
        }
    }


    //iterator for the edges per node
    private class Edge_Iterator_OfNode implements Iterator<EdgeData> {
        private int mc;
        private Iterator<EdgeData> EdgeOfNodeIter;
        private EdgeData edgePerNode;
        public Edge_Iterator_OfNode(int node_id) {
            mc = Mc;
            if (edgesOfNode.get(node_id) != null)
            EdgeOfNodeIter = edgesOfNode.get(node_id).fromMe.values().iterator();

        }

        private void isValide() {
            if (mc != Mc) {
                throw new RuntimeException("the iterator has change!");
            }
        }
        @Override
        public boolean hasNext() {
            isValide();
            return EdgeOfNodeIter.hasNext();
        }
        @Override
        public EdgeData next() {
            isValide();
            edgePerNode = EdgeOfNodeIter.next();
            return edgePerNode;
        }
        @Override
        public void remove() {
            isValide();
            mc++;
            EdgeOfNodeIter.remove();
            removeEdge(edgePerNode.getSrc(), edgePerNode.getDest());

        }
    }


    //iterator for the nodes
    private class Node_Iterator implements Iterator<NodeData> {
        private int mc;
        private final Iterator<NodeData> iter;
        private NodeData Node;
        public Node_Iterator() {
            mc = Mc;
            iter = nodes.values().iterator();
        }

        private void isValide() {
            if (mc != Mc) {
                throw new RuntimeException("the iterator has change!");
            }
        }
        @Override
        public boolean hasNext() {
            isValide();
            return iter.hasNext();
        }
        @Override
        public NodeData next() {
            isValide();
            Node = iter.next();
            return Node;
        }
        @Override
        public void remove() {
            isValide();
            mc++;
            iter.remove();
            removeNode(Node.getKey());
        }
    }

        public HashMap<Integer, NodeData> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Integer, NodeData> nodes) {
        this.nodes = nodes;
    }

    public HashMap<Vector<Integer>, EdgeData> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<Vector<Integer>, EdgeData> edges) {
        this.edges = edges;
    }

    public HashMap<Integer, frTo> getEdgesOfNode() {
        return edgesOfNode;
    }

    public void setEdgesOfNode(HashMap<Integer, frTo> edgesOfNode) {
        this.edgesOfNode = edgesOfNode;
    }

    public int getMc() {
        return Mc;
    }

    public void setMc(int mc) {
        Mc = mc;
    }

}
