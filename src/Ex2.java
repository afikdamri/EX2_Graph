import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {

    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph graph;
        try {
            graph = readGRaphFromJson(json_file);
        } catch (Exception e) {
            e.printStackTrace();
            graph = new myDirectedWeightedGraph();
        }
        return graph;
        }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph graph = getGrapg(json_file);
        DirectedWeightedGraphAlgorithms graphAlgo = new myDirectedWeightedGraphAlgorithms();
        graphAlgo.init(graph);
        return graphAlgo;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static myDirectedWeightedGraph readGRaphFromJson(String json_file)
            throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
        myDirectedWeightedGraph ans = new myDirectedWeightedGraph();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(json_file));
        JSONObject jobj = (JSONObject) obj;
        JSONArray edges = (JSONArray) jobj.get("Edges");
        JSONArray nodes = (JSONArray) jobj.get("Nodes");
        for (Object o : nodes) {
            JSONObject temp = (JSONObject) o;
            NodeData n = new myNodeData(Integer.parseInt(temp.get("id").toString()), temp.get("pos").toString());
            ans.addNode(n);
        }
        for (Object o : edges) {
            JSONObject temp = (JSONObject) o;
            if ((temp.get("src") != null) && temp.get("dest") != null && temp.get("w") != null) {
                int src = Integer.parseInt(temp.get("src").toString());
                int dest = Integer.parseInt(temp.get("dest").toString());
                double w = Double.parseDouble(temp.get("w").toString());
                ans.connect(src, dest, w);
            }
        }
        return ans;
    }



    public static void main(String[] args) {
        DirectedWeightedGraph b = getGrapg("data//G3.json");
        myDirectedWeightedGraphAlgorithms algograph;
        algograph = new myDirectedWeightedGraphAlgorithms();
        algograph.init(b);

        ArrayList<NodeData>y = new ArrayList<NodeData>();

        for (int i = 0; i <b.nodeSize() ; i++) {
            y.add(b.getNode(i));
        }
        System.out.println(algograph.isConnected());
        System.out.println(algograph.shortestPathDist(1,7));
        System.out.println(algograph.shortestPath(1,7));
        System.out.println(algograph.center());
        System.out.println(algograph.tsp(y));

    }
    }

