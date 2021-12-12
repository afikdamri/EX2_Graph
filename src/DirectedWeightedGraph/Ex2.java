package DirectedWeightedGraph;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is the main class for DirectedWeightedGraph.Ex2 - your implementation will be tested using this class.
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
            throws IOException, org.json.simple.parser.ParseException {

        myDirectedWeightedGraph pick = new myDirectedWeightedGraph();
        JSONParser take = new JSONParser();

        var obj = take.parse(new FileReader(json_file));
        var jobj = (JSONObject) obj;
        var edges = (JSONArray) jobj.get("Edges");
        var nodes = (JSONArray) jobj.get("Nodes");
        for (int i = 0, nodesSize = nodes.size(); i < nodesSize; i++) {
            Object o = nodes.get(i);
            var temp = (JSONObject) o;
            NodeData n = new myNodeData(Integer.parseInt(temp.get("id").toString()), temp.get("pos").toString());
            pick.addNode(n);
        }
        for (int i = 0, edgesSize = edges.size(); i < edgesSize; i++) {
            var o = edges.get(i);
            var temp = (JSONObject) o;
            if ((temp.get("src") == null) || temp.get("dest") == null || temp.get("w") == null) {
                continue;
            }
            int src = Integer.parseInt(temp.get("src").toString());
            int dest = Integer.parseInt(temp.get("dest").toString());
            double w = Double.parseDouble(temp.get("w").toString());
            pick.connect(src, dest, w);
        }
        return pick;
    }



    public static void main(String[] args) {
        DirectedWeightedGraph b = getGrapg("data//G2.json");
        myDirectedWeightedGraphAlgorithms algograph;
        algograph = new myDirectedWeightedGraphAlgorithms();
        algograph.init(b);

        ArrayList<NodeData> y = new ArrayList<NodeData>();

        for (int i = 0; i <b.nodeSize() ; i++) {
            y.add(b.getNode(i));
        }

        System.out.println(algograph.isConnected());
        System.out.println(algograph.shortestPathDist(1,7));
        System.out.println(algograph.shortestPath(1,7));
        System.out.println(algograph.center());
        System.out.println(algograph.tsp(y));

//        DirectedWeightedGraph n = new myDirectedWeightedGraph();
//        NodeData a = new myNodeData(1,"1,2,0");
//        NodeData b = new myNodeData(2,"5,3,0");
//        NodeData c = new myNodeData(3,"8,4,0");
//
//        n.addNode(a);
//        n.addNode(b);
//        n.addNode(c);
//        n.connect(1,2,3.55);
//        for (int i = 0; i < n.edgeSize() ; i++) {
//            System.out.println(n.edgeIter().next());
//        }
//        for (int i = 0; i <n.nodeSize() ; i++) {
//            System.out.println(n.nodeIter().next());
//        }
//        System.out.println("");
//        n.removeEdge(1,2);
//        n.removeNode(3);
//
//
//        for (int i = 0; i < n.edgeSize() ; i++) {
//            System.out.println(n.edgeIter().next());
//        }
//        for (int i = 0; i <n.nodeSize() ; i++) {
//            System.out.println(n.nodeIter().next());
//        }



    }
    }

