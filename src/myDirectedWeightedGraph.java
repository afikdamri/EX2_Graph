import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class myDirectedWeightedGraph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes;
    private HashMap<Vector<Integer>, EdgeData> edges;
    private HashMap<Integer, frTo> edgesOfNode;
    private int Mc;
    int node_size;
    int edge_size;

    public myDirectedWeightedGraph() {
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
    public void addNode(NodeData n) {
        if (nodes.get(n.getKey()) != null) {
            throw new RuntimeException("The Node key already exists");
        }
        nodes.put(n.getKey(), (myNodeData) n);
        edgesOfNode.put(n.getKey(), new frTo());
        node_size++;
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

    //    private class EdgeIterator implements Iterator<EdgeData> {
//        private int myMC;
//        private Iterator<EdgeData> iter;
//        private EdgeData curr;
//        public EdgeIterator() {
//            myMC = Mc;
//            iter = edges.values().iterator();
//        }
//
//        private void isValide() {
//            if (myMC != Mc) {
//                throw new RuntimeException("the iterator has change!");
//            }
//        }
//        @Override
//        public boolean hasNext() {
//            isValide();
//            return iter.hasNext();
//        }
//        @Override
//        public EdgeData next() {
//            isValide();
//            curr = iter.next();
//            return curr;
//        }
//        @Override
//        public void remove() {
//            isValide();
//            ++myMC;
//            iter.remove();
//            removeEdge(curr.getSrc(), curr.getDest());
//        }
//    }
    private class EdgeIteratorOfNode implements Iterator<EdgeData> {
        private int myMC;
        private Iterator<EdgeData> iter;
        private EdgeData curr;
        public EdgeIteratorOfNode(int node_id) {
            myMC = Mc;
            iter = edgesOfNode.get(node_id).fromMe.values().iterator();
        }

        private void isValide() {
            if (myMC != Mc) {
                throw new RuntimeException("the iterator has change!");
            }
        }
        @Override
        public boolean hasNext() {
            isValide();
            return iter.hasNext();
        }
        @Override
        public EdgeData next() {
            isValide();
            curr = iter.next();
            return curr;
        }
        @Override
        public void remove() {
            isValide();
            ++myMC;
            iter.remove();
            removeEdge(curr.getSrc(), curr.getDest());

        }
    }
//
//    private class NodeIterator implements Iterator<NodeData> {
//        private int myMC;
//        private Iterator<NodeData> iter;
//        private NodeData curr;
//        public NodeIterator() {
//            myMC = Mc;
//            iter = nodes.values().iterator();
//        }
//
//        private void isValide() {
//            if (myMC != Mc) {
//                throw new RuntimeException("the iterator has change!");
//            }
//        }
//        @Override
//        public boolean hasNext() {
//            isValide();
//            return iter.hasNext();
//        }
//        @Override
//        public NodeData next() {
//            isValide();
//            curr = iter.next();
//            return curr;
//        }
//        @Override
//        public void remove() {
//            isValide();
//            ++myMC;
//            iter.remove();
//            removeNode(curr.getKey());
//        }
//    }
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

    public static void main(String[] args) {
//        myDirectedWeightedGraph graph = new myDirectedWeightedGraph();
//        graph.addNode(new myNodeData(0, new myGeoLocation(0, 0, 0)));
//        graph.addNode(new myNodeData(1, new myGeoLocation(1, 0, 0)));
//        graph.connect(0,1,5);
//        graph.connect(1,0,3);
//        System.out.println(graph.getEdge(1,0));
//        System.out.println(graph.);
    }


}
