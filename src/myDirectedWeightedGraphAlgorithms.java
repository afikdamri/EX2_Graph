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

    //isConnected that working in recursion (very bad running time..) dont use it:

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
        dijkstra shortestPathDistCheck = getdijkstra(src);
        return shortestPathDistCheck.dists.get(dest);
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
                max = Math.max(comp.dists.get(D.getKey()), max);
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
    public List<NodeData> tsp(List<NodeData> Nodes) {
        upD(Nodes);
        double goodOne = infiP;
        List<NodeData> choose= new ArrayList<>();
        return bestOne(Nodes,goodOne,choose) ;
    }




    @Override
    public boolean save(String file) {
        Writer jsonFile;
        Map<String, JSONArray> mainMap = new HashMap<>();
        JSONArray edgeArray = new JSONArray();
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        while (iterEdges.hasNext()) {
            EdgeData edge = iterEdges.next();
            Map<String, String> edgeMap = new HashMap<>();
            edgeMap.put("src", edge.getSrc() + "");
            edgeMap.put("w", edge.getWeight() + "");
            edgeMap.put("dest", edge.getDest() + "");
            JSONObject obj = new JSONObject();
            obj.putAll(edgeMap);
            edgeArray.add(obj);
        }
        mainMap.put("Edges", edgeArray);
        JSONArray nodeArray = new JSONArray();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (true) {
            if (!iterNodes.hasNext()) break;
            NodeData node = iterNodes.next();
            GeoLocation pos = node.getLocation();
            Map<String, String> nodesMap = new HashMap<>();
            nodesMap.put("pos", pos.x() + "," + pos.y() + "," + pos.z());
            nodesMap.put("id", node.getKey() + "");
            JSONObject obj = new JSONObject();
            obj.putAll(nodesMap);
            nodeArray.add(obj);
        }
        mainMap.put("Nodes", nodeArray);
        try {
            jsonFile = new FileWriter(file);
            JSONObject temp = new JSONObject();
            temp.putAll(mainMap);
            jsonFile.write(temp.toJSONString());
            jsonFile.flush();
            jsonFile.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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


    private dijkstra getdijkstra(int src) {
        if (dijkstra.get(src) == null) {
            dijkstra.put(src, new dijkstra(src));
        }
        dijkstra dijkstraAlgo = dijkstra.get(src);
        dijkstraAlgo.update();
        return up(src);
    }



    public class dijkstra {
        public double maxDis;
        public int src;
        public int action;

        public HashMap<Integer, Double> dists;
        public HashMap<Integer, List<NodeData>> roads;
        public HashMap<Integer, Integer> paps = new HashMap<>();


        dijkstra(int src) {
            this.src = src;
            action = Integer.MIN_VALUE;
            dists = new HashMap<>();
            roads = new HashMap<>();
        }

        public void update() {
            if (action == graph.getMC()) {
                return;
            }
            action = graph.getMC();
            alg();
        }

        private void alg() {
            ArrayList<Integer> Q = new ArrayList<>();
            initMaps(paps, Q);

            while (!Q.isEmpty()) {
                int u = minInList(Q);
                Iterator<EdgeData> iter = graph.edgeIter(u);
                while (iter.hasNext()) {
                    relax(iter.next());
                }
            }
        }

        private void relax(EdgeData edge) {
            int u = edge.getSrc();
            int v = edge.getDest();
            double newDist = dists.get(u) + edge.getWeight();
            if (dists.get(v) > newDist) {
                dists.replace(v, newDist);
                paps.replace(v, u);
            }
        }

        private void addPath(int node) {
            if (node == src) {
                roads.get(node).add(graph.getNode(node));
                return;
            }
            int dad = paps.get(node);
            if (dad == Integer.MIN_VALUE) {
                dists.replace(node, -1.0);
                return;
            }
            if (roads.get(dad).isEmpty()) {
                addPath(dad);
            }
            roads.get(node).addAll(roads.get(dad));
            roads.get(node).add(graph.getNode(node));
            maxDis = Math.max(maxDis, dists.get(node));
        }

        private void initMaps(HashMap<Integer, Integer> dads, ArrayList<Integer> Q) {
            action = Integer.MIN_VALUE;
            Iterator<NodeData> iter = graph.nodeIter();
            while (iter.hasNext()) {
                int key = iter.next().getKey();
                if (key != src) {
                    dists.put(key, Double.POSITIVE_INFINITY);
                    dads.put(key, Integer.MIN_VALUE);
                    Q.add(key);
                    roads.put(key, new ArrayList<>());
                }
            }
            dads.put(src, src);
            dists.put(src, 0.0);
            roads.put(src, new ArrayList<>());
            Q.add(src);
        }

        private int minInList(ArrayList<Integer> Q) {
            double min = Double.MAX_VALUE;
            int ans = Integer.MIN_VALUE;
            int index = Integer.MIN_VALUE;
            for (int i = 0; i < Q.size(); i++) {
                if (min > dists.get(Q.get(i))) {
                    ans = Q.get(i);
                    min = dists.get(Q.get(i));
                    index = i;
                }
            }
            Q.remove(index);
            return ans;
        }
    }


//    public dijkstra getDijkstra(int src) {
//        System.out.println(graph.nodeSize());
//        if (dijkstra.get(src) == null) {
//            dijkstra.put(src, new dijkstra(src));
//        }
//        return up(src);}
//
//
//
//    public dijkstra up(int src){
//        dijkstra dijkst = dijkstra.get(src);
//        dijkst.updating();
//        return dijkst;
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //the dijkstra algorithm:

//        public class dijkstra {
//        public HashMap<Integer,Double> dists;
//        public HashMap<Integer, List<NodeData>> roads;
//        public HashMap<Integer,Integer> paps = new HashMap<>();
//
//        public int src;
//        public int action;
//        public double maxDis;
//
//        //constructor
//        dijkstra(int src) {
//            this.src = src;
//            action = Integer.MIN_VALUE;
//            dists = new HashMap<>();
//            roads  = new HashMap<>();
//        }
//
//        //update the graph details
//        public void updating() {
//            if (action != graph.getMC()) {
////                System.out.println("im here");
//                action = graph.getMC();
//                System.out.println(action);
//                goForIt();
//            }
//        }
//
//        //made the algo happen.
//        private void goForIt() {
//            ArrayList<Integer> listPerNode = new ArrayList<>();
//            init(paps, listPerNode);
//            boolean b = true;
//            b = listPerNode.isEmpty();
//
//            while (!b) {
//                b = listPerNode.isEmpty();
//                int y = minInList(listPerNode);
//                Iterator<EdgeData> iter = graph.edgeIter(y);
//                while (iter.hasNext()) {
//                    relax(iter.next());
//                }
//            }
//        }
//
//
//        private void relax(EdgeData edge) {
//            int u = edge.getSrc();
//            int v = edge.getDest();
//            double dist = dists.get(u) + edge.getWeight();
//            if (!(dists.get(v) > dist)) {
//                return;
//            }
//            dists.replace(v, dist);
//            paps.replace(v, u);
//        }
//
//        private void init(HashMap<Integer,Integer> paps, ArrayList<Integer> listPerNode) {
//            action = Integer.MIN_VALUE;
//            Iterator<NodeData> iter = graph.nodeIter();
//
//            while (iter.hasNext()) {
//                int key = iter.next().getKey();
//                check(key,listPerNode);}
//            paps.put(src, src);
//            dists.put(src, 0.0);
//            roads.put(src, new ArrayList<>());
//            listPerNode.add(src);
//        }
//
//        public void check(int key,ArrayList<Integer> listPerNode){
//            if(key != src) {
//                dists.put(key, Double.POSITIVE_INFINITY);
//                paps.put(key, Integer.MIN_VALUE);
//                listPerNode.add(key);
//                roads.put(key, new ArrayList<>());
//            }
//        }
//        private int minInList(ArrayList<Integer> listPerNode) {
//            double max = Double.MAX_VALUE;
//            int minValue = Integer.MIN_VALUE;
//            int index = Integer.MIN_VALUE;
//            for (int i = 0; i < listPerNode.size(); i++) {
//                if (max>dists.get(listPerNode.get(i))) {
//                    minValue = listPerNode.get(i);
//                    max = dists.get(listPerNode.get(i));
//                    index = i;
//                }
//            }
//            System.out.println(index);
//            listPerNode.remove(index);
//            return minValue;
//        }
//
//        public void conversion(double min ,double value, int ans,int value2,int i,int index){
//            if (min>value) {
//                ans = value2;
//                min = value;
//                index = i;
//            }
//        }
//
//
//            private void addPath(int node) {
//                if (node == src) {
//                    roads.get(node).add(graph.getNode(node));
//                    return;
//                }
//                int pap = paps.get(node);
//
//                if (pap == Integer.MIN_VALUE) {
//                    dists.replace(node, -1.0);
//                    return;
//                }
//                if (roads.get(pap).isEmpty()) {
//                    addPath(pap);
//                }
//                roads.get(node).addAll(roads.get(pap));
//                roads.get(node).add(graph.getNode(node));
//                maxDis = Math.max(maxDis, dists.get(node));
//            }
//    }

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
            getdijkstra(i.getKey()).update();
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
                if (getdijkstra(ind.getKey()).dists.get(temp.getKey()) < shorted) {
                    shorted = getdijkstra(ind.getKey()).dists.get(temp.getKey());
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
        S += getdijkstra(ind.getKey()).dists.get(one.getKey());

        return S;
    }

//    public NodeData findBestNode(List<NodeData> newArray,NodeData ind,double low,NodeData another){
//        for (int i = 0, newArraySize = newArray.size(); i < newArraySize; i++) {
//            NodeData j = newArray.get(i);
//            if (getdijkstra(ind.getKey()).dists.get(j.getKey()) < low) {
//                low = getdijkstra(ind.getKey()).dists.get(j.getKey());
//                another = j;
//            }
//        }
//    return another; }


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

    public dijkstra up(int src){
        dijkstra dijkst = dijkstra.get(src);
        dijkst.update();
        return dijkst;
    }
}


