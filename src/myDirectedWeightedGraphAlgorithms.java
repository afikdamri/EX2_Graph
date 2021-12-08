import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class myDirectedWeightedGraphAlgorithms implements DirectedWeightedGraphAlgorithms {
    myDirectedWeightedGraph graph;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (myDirectedWeightedGraph) g;
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
        Iterator<NodeData> iter = graph.nodeIter();
        NodeData f = iter.next(); //the first node that we check from him.
        f.setTag(0);

        while (iter.hasNext()) {
            iter.next().setTag(0);
        }

        DFS(graph,f); //With the DFS algo we can check the connected between every single node in the graph.
        // Now we checks if there is a node that has not been visited

        iter = graph.nodeIter();

        while (iter.hasNext()) {
            if (iter.next().getTag() == 0) {
                return false;
            }
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

        iter = reversgraph.nodeIter();
        f = iter.next();
        f.setTag(0);
        while (iter.hasNext()){
            iter.next().setTag(0);
        }

        DFS(reversgraph,f);

        iter = reversgraph.nodeIter();
        while (iter.hasNext()){
            if (iter.next().getTag()==0)
                return false;
        }

    return true;}

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
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(myDirectedWeightedGraph.class, new deserialize());
            Gson gson = builder.setPrettyPrinting().create();

            Reader reader = Files.newBufferedReader(Paths.get(file));
            graph = gson.fromJson(reader, myDirectedWeightedGraph.class);
            System.out.println(graph);
        } catch (IOException e) {
            e.printStackTrace();
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


//class ShortestPath {
//    // A utility function to find the vertex with minimum distance value,
//    // from the set of vertices not yet included in shortest path tree
//    static final int V = 9;
//    int minDistance(int dist[], Boolean sptSet[])
//    {
//        // Initialize min value
//        int min = Integer.MAX_VALUE, min_index = -1;
//
//        for (int v = 0; v < V; v++)
//            if (sptSet[v] == false && dist[v] <= min) {
//                min = dist[v];
//                min_index = v;
//            }
//
//        return min_index;
//    }
//
//    void printSolution(int dist[])
//    {
//        System.out.println("Vertex \t\t Distance from Source");
//        for (int i = 0; i < V; i++)
//            System.out.println(i + " \t\t " + dist[i]);
//    }
//
//    void dijkstra(int graph[][], int src)
//    {
//        int dist[] = new int[V]; // The output array. dist[i] will hold
//        // the shortest distance from src to i
//
//        // sptSet[i] will true if vertex i is included in shortest
//        // path tree or shortest distance from src to i is finalized
//        Boolean sptSet[] = new Boolean[V];
//
//        // Initialize all distances as INFINITE and stpSet[] as false
//        for (int i = 0; i < V; i++) {
//            dist[i] = Integer.MAX_VALUE;
//            sptSet[i] = false;
//        }
//
//        // Distance of source vertex from itself is always 0
//        dist[src] = 0;
//
//        // Find shortest path for all vertices
//        for (int count = 0; count < V - 1; count++) {
//            // Pick the minimum distance vertex from the set of vertices
//            // not yet processed. u is always equal to src in first
//            // iteration.
//            int u = minDistance(dist, sptSet);
//
//            // Mark the picked vertex as processed
//            sptSet[u] = true;
//
//            // Update dist value of the adjacent vertices of the
//            // picked vertex.
//            for (int v = 0; v < V; v++)
//
//                // Update dist[v] only if is not in sptSet, there is an
//                // edge from u to v, and total weight of path from src to
//                // v through u is smaller than current value of dist[v]
//                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
//                    dist[v] = dist[u] + graph[u][v];
//        }
//
//        // print the constructed distance array
//        printSolution(dist);
//    }
//
//// Driver method}
//public static void main(String[] args)
//{
//    /* Let us create the example graph discussed above */
//    int graph[][] = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
//            { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
//            { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
//            { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
//            { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
//            { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
//            { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
//            { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
//            { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
//    ShortestPath t = new ShortestPath();
//    t.dijkstra(graph, 0);
//}


    public void DFS(DirectedWeightedGraph graph, NodeData n) {
        n.setTag(1);
        Iterator<EdgeData> iter = graph.edgeIter(n.getKey());
        while (iter.hasNext()) {
            EdgeData edge = iter.next();
            NodeData nodeSon = graph.getNode(edge.getDest());
            if (nodeSon.getTag() == 0)
                DFS(graph, nodeSon);
        }
    }


}
