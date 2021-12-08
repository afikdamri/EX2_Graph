import java.util.Iterator;

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

    return null;}
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        myDirectedWeightedGraphAlgorithms ans = new myDirectedWeightedGraphAlgorithms();
        ans.load(json_file);
        return (DirectedWeightedGraphAlgorithms) ans;
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
    public static void main(String[] args) {
        myDirectedWeightedGraph graph = new myDirectedWeightedGraph();
        graph.addNode(new myNodeData(0, new myGeoLocation(0, 0, 0)));
        graph.addNode(new myNodeData(1, new myGeoLocation(1, 0, 0)));
        graph.addNode(new myNodeData(2, new myGeoLocation(2, 0, 0)));
        graph.connect(0,1,5);
        graph.connect(1,0,3);
        graph.connect(1,2,3);
        graph.removeEdge(0,1);
        Iterator<EdgeData> iter = graph.edgeIter();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        DirectedWeightedGraphAlgorithms a =getGrapgAlgo("data//G1.json");
        System.out.println(a.getGraph());
        a.isConnected();

    }
    }

