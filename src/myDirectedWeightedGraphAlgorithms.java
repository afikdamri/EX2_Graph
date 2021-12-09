import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class myDirectedWeightedGraphAlgorithms implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraph graph;

    @Override
    public void init(DirectedWeightedGraph g) {
        graph =  g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        myDirectedWeightedGraph graph1 = new myDirectedWeightedGraph();
        Iterator<NodeData> iterN = graph.nodeIter();
        Iterator<EdgeData> iterE = graph.edgeIter();

        while (iterE.hasNext()) {
            EdgeData edge = iterE.next();
            graph1.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
        }

        while (iterN.hasNext()) {
            NodeData node = iterN.next();
            NodeData another = new myNodeData(node);
            graph1.addNode(another);
        }
        return graph1;
    }
    @Override
    public boolean isConnected() {
        Iterator<NodeData> iterator = graph.nodeIter();
        NodeData f = iterator.next();
        while (iterator.hasNext()){
            if (!DFS(graph,iterator.next()))
                return false;
        }
        DirectedWeightedGraph reversgraph = new  myDirectedWeightedGraph();

        Iterator<NodeData> iterOfNode = graph.nodeIter();
        while (iterOfNode.hasNext()){
            NodeData node = new myNodeData(iterOfNode.next());
            reversgraph.addNode(node);
        }

        Iterator<EdgeData> iterOfEdge = graph.edgeIter();
        while (iterOfEdge.hasNext()){
            EdgeData edge = iterOfEdge.next();
            reversgraph.connect(edge.getDest() , edge.getSrc(),edge.getWeight());
        }
        iterator = reversgraph.nodeIter();
        while (iterator.hasNext()){
            if (!DFS(reversgraph,iterator.next())){
                return false;
            }
        }
    return true;}

//    @Override
//    public boolean isConnected() {
//        Iterator<NodeData> iter = graph.nodeIter();
//        NodeData f = iter.next(); //the first node that we check from him.
//        make0(iter,f);
//        DFS(graph,f); //With the DFS algo we can check the connected between every single node in the graph.
//        // Now we checks if there is a node that has not been visited
//
//        iter = graph.nodeIter();
//
//        if (!checkCon(iter)) return false;
//
//        DirectedWeightedGraph reversgraph = new  myDirectedWeightedGraph();
//
//        Iterator<NodeData> iterOfNode = graph.nodeIter();
//        while (iterOfNode.hasNext()){
//            NodeData node = new myNodeData(iterOfNode.next());
//            reversgraph.addNode(node);
//        }
//
//        Iterator<EdgeData> iterOfEdge = graph.edgeIter();
//        while (iterOfEdge.hasNext()){
//            EdgeData edge = iterOfEdge.next();
//            reversgraph.connect(edge.getDest() , edge.getSrc(),edge.getWeight());
//        }
//
//        iter = reversgraph.nodeIter();
//        f = iter.next();
//        make0(iter,f);
//
//        DFS(reversgraph,f);
//
//        iter = reversgraph.nodeIter();
//        return checkCon(iter);}
//
//
//    public void make0(Iterator<NodeData> iter, NodeData f){
//        f.setTag(0);
//        while (iter.hasNext()) {
//            iter.next().setTag(0);
//        }
//    }
//
//    public boolean checkCon( Iterator<NodeData> iter){
//        while (iter.hasNext()){
//            if (iter.next().getTag()==0)
//                return false;
//        }
//    return true;}

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            DirectedWeightedGraph newGraph = Ex2.readGRaphFromJson(file);
            init(newGraph);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "myDirectedWeightedGraphAlgorithms{" +
                "graph=" + graph +
                '}';
    }


    public boolean DFS(DirectedWeightedGraph graph, NodeData n) {
        boolean[] paint = new boolean[graph.nodeSize()];
        LinkedList <NodeData> l = new LinkedList<>();
        l.add(n);

        paint[n.getKey()] = true;
        int size = 1;

        while (!l.isEmpty()){
            NodeData save = l.pollFirst();
            Iterator<EdgeData> iterator = graph.edgeIter(save.getKey());

            while (iterator.hasNext()){
                EdgeData i = iterator.next();
                if (!paint[i.getDest()]){
                    size++;
                    paint[i.getDest()] = true;
                    l.add(graph.getNode(i.getDest()));
                }
            }
        }
        return size == paint.length;
    }}



