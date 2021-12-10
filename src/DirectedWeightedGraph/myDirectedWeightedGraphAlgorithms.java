package DirectedWeightedGraph;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class myDirectedWeightedGraphAlgorithms implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraph graph;
    public HashMap<Integer, dijkstra> dijkstra;
    double infinite = Double.MAX_VALUE;
    double MIN = Integer.MIN_VALUE;
    double infiP =Double.POSITIVE_INFINITY;

    //can see all the auxiliary functions are at the bottom of the page:
    //1.copys - make the copy.
    //2.Dfs algorithm - make the Dfs algorithm.
    //3.Comparison - comparison between the Nodes.
    //4.upD - update the algorithm details.
    //5.bestOne - find the best road for the tsp algorithm
    //6.lustfulAlgorithm - use the lustfulAlgorithm for the tsp algorithm.
    //7.compAdd - making the edd to find the shortest road.
    //8.up - update the dijkstra algorithm on the graph.


    @Override
    public void init(DirectedWeightedGraph g) {
        graph = g;
        dijkstra = new HashMap<>();
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
        copys(graph1, iterN, iterE);
        return graph1;
    }

    @Override
    public boolean isConnected() {
        var iterator = graph.nodeIter();
        NodeData f = iterator.next();
        while (iterator.hasNext()) {
            if (!DFS(graph, iterator.next()))
                return false;
        }
        DirectedWeightedGraph reversgraph = new myDirectedWeightedGraph();

        var iterOfNode = graph.nodeIter();
        while (iterOfNode.hasNext()) {
            NodeData node = new myNodeData(iterOfNode.next());
            reversgraph.addNode(node);
        }

        var iterOfEdge = graph.edgeIter();
        while (iterOfEdge.hasNext()) {
            EdgeData edge = iterOfEdge.next();
            reversgraph.connect(edge.getDest(), edge.getSrc(), edge.getWeight());
        }
        iterator = reversgraph.nodeIter();
        while (iterator.hasNext()) {
            if (!DFS(reversgraph, iterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        dijkstra shortestPathDistCheck = getdijkstra(src);
        return shortestPathDistCheck.D.get(dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        dijkstra shortestPathCheck = getdijkstra(src);
        shortestPathCheck.addPath(dest);
        return shortestPathCheck.roads.get(dest);
    }

    @Override
    public NodeData center() {
        double min = infinite;//It will become minimal ..
        NodeData pick = null;
        var checkNodes = graph.nodeIter();

        while (checkNodes.hasNext()) {
            double max = MIN;
            NodeData S = checkNodes.next();
            dijkstra comp = getdijkstra(S.getKey());
            var iter = graph.nodeIter();

            while (iter.hasNext()) {
                NodeData D = iter.next();
                max = Math.max(comp.D.get(D.getKey()), max);
            }

            if (pick == Comparison(min, max, pick, S)) {
                continue;
            }
            else {
                pick = Comparison(min, max, pick, S);
                min = max;
            }
        }
        return pick;
    }


    @Override
    public String toString() {
        return "DirectedWeightedGraph.myDirectedWeightedGraphAlgorithms{" +
                "graph=" + graph +
                '}';
    }


    private dijkstra getdijkstra(int src) {
        if (dijkstra.get(src) != null) {
            return up(src);
        }
        dijkstra.put(src, new dijkstra(src));
        return up(src);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //The Dijkstra Algorithm:

    public class dijkstra {
        public double distM;
        public int src;
        public int action;
        double infinite = Double.MAX_VALUE;
        int MIN = Integer.MIN_VALUE;

        //Hashmaps:
        public HashMap<Integer, Double> D;
        public HashMap<Integer, List<NodeData>> roads;
        public HashMap<Integer, Integer> paps;


        dijkstra(int src) {
            this.src = src;
            this.action = MIN;

            this.paps = new HashMap<>();
            this.D = new HashMap<>();
            this.roads = new HashMap<>();

        }

        private void addPath(int node) {
            if (node != src) {
                int dad = paps.get(node);
                      if (dad != MIN) {
                           if (!roads.get(dad).isEmpty()) {
                               roads.get(node).addAll(roads.get(dad));
                                  } else {
                                      addPath(dad);
                                      roads.get(node).addAll(roads.get(dad));
                                  }
                          roads.get(node).add(graph.getNode(node));
                    distM = Math.max(distM, D.get(node));
                } else {
                    D.replace(node, -1.0);
                }
                } else {
                roads.get(node).add(graph.getNode(node));
                 }

        }

        private void turn(EdgeData edge) {
            int EdgeS = edge.getSrc();
            int EdgeD = edge.getDest();
            double TD = D.get(EdgeS) + edge.getWeight();
            if (!(D.get(EdgeD) > TD)) {
                return;
            }
            D.replace(EdgeD, TD);
            paps.replace(EdgeD, EdgeS);
        }

        public void goForIt() {
            var ListPerNode = new ArrayList<Integer>();
            initshiate(paps, ListPerNode);

            if (!ListPerNode.isEmpty()) {
                do {
                    int minInList = theSmallest(ListPerNode);
                    Iterator<EdgeData> iter = graph.edgeIter(minInList);
                    if (iter.hasNext()) {
                        do turn(iter.next());
                        while (iter.hasNext());
                    }
                } while (!ListPerNode.isEmpty());
            }
        }

        private void initshiate(HashMap<Integer,Integer> paps, ArrayList<Integer> listPerNode) {
            action = MIN;
            Iterator<NodeData> iter = graph.nodeIter();

            while (iter.hasNext()) {
                int key = iter.next().getKey();
                check(key,listPerNode);}
            paps.put(src, src);
            D.put(src, 0.0);
            roads.put(src, new ArrayList<>());
            listPerNode.add(src);
        }

        public int theSmallest(ArrayList<Integer> listPerNode) {
            double Will_B_Min = infinite;

            int minValue = MIN;
            int index = MIN;

            for (int i = 0; i < listPerNode.size(); i++)
                if (Will_B_Min > D.get(listPerNode.get(i))) {
                    minValue = listPerNode.get(i);
                    Will_B_Min = D.get(listPerNode.get(i));
                    index = i;
                }

            listPerNode.remove(index);
            return minValue;
        }


        public void updating() {
            if (action != graph.getMC()) {
                action = graph.getMC();
                goForIt();
            }
        }

        public void check(int key,ArrayList<Integer> listPerNode){
            if(key != src) {
                D.put(key, Double.POSITIVE_INFINITY);
                paps.put(key, Integer.MIN_VALUE);
                listPerNode.add(key);
                roads.put(key, new ArrayList<>());
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Make the copy
    public void copys(myDirectedWeightedGraph g, Iterator<NodeData> N, Iterator<EdgeData> E) {
        while (E.hasNext()) {
            EdgeData edge = E.next();
            g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
        }

        while (N.hasNext()) {
            NodeData node = N.next();
            NodeData another = new myNodeData(node);
            g.addNode(another);
        }
    }


    //DFS
    public boolean DFS(DirectedWeightedGraph graph, NodeData n) {
        boolean[] paint = new boolean[graph.nodeSize()];
        LinkedList<NodeData> l = new LinkedList<>();
        l.add(n);

        paint[n.getKey()] = true;
        int size = 1;

        while (!l.isEmpty()) {
            NodeData save = l.pollFirst();
            Iterator<EdgeData> iterator = graph.edgeIter(save.getKey());

            while (iterator.hasNext()) {
                EdgeData i = iterator.next();
                if (!paint[i.getDest()]) {
                    size++;
                    paint[i.getDest()] = true;
                    l.add(graph.getNode(i.getDest()));
                }
            }
        }
        return size == paint.length;
    }

    //Comparison.
    public NodeData Comparison(double min, double max, NodeData ans, NodeData src) {
        if (min > max) {
            min = max;
            ans = src;
        }
        return ans;
    }

    //update
    public void upD(List<NodeData> Nodes){
        for (NodeData i : Nodes) {
            getdijkstra(i.getKey()).updating();
        }
    }


    //bestOne
    public List<NodeData> bestOne(List<NodeData> Nodes,double goodOne,List<NodeData> choose){
        double Val = 0;
        for (int j = 0; j < Nodes.size(); j++) {
            var i = Nodes.get(j);
            var comp = new ArrayList();
            Val = lustfulAlgorithm(i, new ArrayList<>(Nodes), comp);
            if (Val < goodOne) {
                goodOne = Val;
                choose = comp;
            }
        }
        return choose;
    }

    //lustfulAlgorithm
    public double lustfulAlgorithm(NodeData ind, List<NodeData> newArray, List<NodeData> comp) {
        comp.add(ind);
        double S = 0;
        NodeData another = null;
        newArray.remove(ind);
        NodeData one = ind;

        while (!newArray.isEmpty()) {
            double shorted = infiP;
            for (int i = 0, newArraySize = newArray.size(); i < newArraySize; i++) {
                NodeData temp = newArray.get(i);
                if (getdijkstra(ind.getKey()).D.get(temp.getKey()) < shorted) {
                    shorted = getdijkstra(ind.getKey()).D.get(temp.getKey());
                    another = temp;
                }
            }
            assert another != null;
//            findBestNode( newArray,ind,shorted,another);
            S+= shorted;
            compAdd(another,ind,comp);
            ind = another;
            newArray.remove(another);
        }

        comp.add(one);
        S += getdijkstra(ind.getKey()).D.get(one.getKey());

        return S;
    }

    //compAdd
    public void compAdd(NodeData another, NodeData ind, List<NodeData> comp){
        boolean f = true;
        assert another != null;
        List<NodeData> shortestPath = shortestPath(ind.getKey(), another.getKey());
        for (int i = 0, shortestPathSize = shortestPath.size(); i < shortestPathSize; i++) {
            NodeData j = shortestPath.get(i);
            if (f) {
                f = false;
                continue;
            }
            comp.add(j);
        }

    }
    //up
    public dijkstra up(int src){
        dijkstra dijkst = dijkstra.get(src);
        dijkst.updating();
        return dijkst;
    }


    @Override
    public List<NodeData> tsp(List<NodeData> Nodes) {
        upD(Nodes);
        double goodOne = infiP;
        List<NodeData> choose= new ArrayList<>();
        return bestOne(Nodes,goodOne,choose) ;
    }



//json file load and write


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
    public boolean save(String file) {
        Writer jsonFile;
        Map<String, JSONArray> M = new HashMap<>();
        var edgeArray = new JSONArray();
        var iterEdges = graph.edgeIter();


        //Nodes
        if (iterEdges.hasNext()) {
            do {
                EdgeData E = iterEdges.next();
                Map<String, String> edgeMap = new HashMap<>();
                edgeMap.put("src", E.getSrc() + "");
                edgeMap.put("w", E.getWeight() + "");
                edgeMap.put("dest", E.getDest() + "");
                JSONObject obj = new JSONObject();
                obj.putAll(edgeMap);
                edgeArray.add(obj);
            } while (iterEdges.hasNext());
        }


        //Edges
        M.put("Edges", edgeArray);
        var nodeArray = new JSONArray();

        var iterNodes = graph.nodeIter();
        while (true) {
            if (!iterNodes.hasNext()) break;
            NodeData node = iterNodes.next();
            GeoLocation location = node.getLocation();
            Map<String, String> nodesMap = new HashMap<>();
            nodesMap.put("location", location.x() + "," + location.y() + "," + location.z());
            nodesMap.put("id", node.getKey() + "");
            JSONObject obj = new JSONObject();
            obj.putAll(nodesMap);
            nodeArray.add(obj);
        }

        M.put("Nodes", nodeArray);
        try {
            jsonFile = new FileWriter(file);
            JSONObject temp = new JSONObject();
            temp.putAll(M);
            jsonFile.write(temp.toJSONString());
            jsonFile.flush();
            jsonFile.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}


