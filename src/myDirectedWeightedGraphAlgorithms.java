import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class myDirectedWeightedGraphAlgorithms implements DirectedWeightedGraphAlgorithms{
    myDirectedWeightedGraph graph;

    @Override
    public void init(DirectedWeightedGraph g){
        this.graph= (myDirectedWeightedGraph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        myDirectedWeightedGraph graph = new myDirectedWeightedGraph();
        return graph;
    }

    @Override
    public boolean isConnected() {
      return true;
    }

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
}
