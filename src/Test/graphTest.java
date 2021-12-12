import DirectedWeightedGraph.Ex2;
import DirectedWeightedGraph.myDirectedWeightedGraphAlgorithms;
import DirectedWeightedGraph.myGeoLocation;
import DirectedWeightedGraph.myNodeData;
import DirectedWeightedGraph.myEdgeData;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;


public class graphTest {
    DirectedWeightedGraph myGraph;
    myDirectedWeightedGraphAlgorithms meAlgo;

    graphTest() {
        myGraph = Ex2.getGrapg("data/G1.json"); // enter here the path for G1 json file
        meAlgo = new myDirectedWeightedGraphAlgorithms();
        meAlgo.init(myGraph); }

    public static void main(String[] args) {
        graphTest test = new graphTest();
        List<NodeData> checkTps = new ArrayList<NodeData>();
        for (int j = 0; j < 17; j++) {
            checkTps.add(test.myGraph.getNode(j));
        }

        System.out.println("hiii");
        List<NodeData> nodeData = test.meAlgo.tsp(checkTps);
        System.out.println("hiii");
        for (NodeData n : nodeData) {
            System.out.print(n.getKey() + ",");
        }
    }

    @Test
    void testGetNode() {
        graphTest test1 = new graphTest();
        GeoLocation g = new myGeoLocation("35.19589389346247,32.10152879327731,0.0");
        assertEquals(g.toString(), test1.myGraph.getNode(0).getLocation().toString());
    }
    @Test
    void testGetEdge() {
        graphTest test1 = new graphTest();
        assertEquals(1.8015954015822042,test1.myGraph.getEdge(1,2).getWeight());

    }



    @Test
    void testCenter() {
        graphTest test1 = new graphTest();
        assertEquals(8, test1.meAlgo.center().getKey());
    }

    @Test
    void testShortestPathDis() {
        graphTest test1 = new graphTest();
        assertEquals(1.0631605142699874, test1.meAlgo.shortestPathDist(2,3));

    }

    @Test
    void testEdgeSize() {
        graphTest test1 = new graphTest();
        assertEquals(36, test1.myGraph.edgeSize());
    }


    @Test
    void testNodeSize() {
        graphTest test1 = new graphTest();
        assertEquals(17, test1.myGraph.nodeSize());
    }

    @Test
    void testRemoveEdge() {
        graphTest test1 = new graphTest();
        test1.myGraph.removeEdge(0,1);
        assertEquals(null, test1.myGraph.getEdge(0,1));

    }

    @Test
    void testRemoveNode() {
        graphTest test1 = new graphTest();
        test1.myGraph.removeNode(1);
        assertEquals(16, test1.myGraph.nodeSize());

    }

    @Test
    void testNodeIterator() {
        graphTest test1 = new graphTest();
        Iterator<NodeData> iter = test1.myGraph.nodeIter();
        int key = iter.next().getKey();
        iter.remove();
        assertNull(test1.myGraph.getNode(key));
        test1.myGraph.addNode(new myNodeData(55,"0,0,0"));
    }

    @Test
    void testEdgeIterator() {
        graphTest test1 = new graphTest();
        myEdgeData edge = new myEdgeData(0,1,1);
        Iterator<EdgeData> iter = test1.myGraph.edgeIter();
        int key = iter.next().getSrc();
        iter.remove();
        assertEquals(null, test1.myGraph.getEdge(0,1));

    }
    @Test
    void testConnect() {
        graphTest test1 = new graphTest();
        assertEquals(true, test1.meAlgo.isConnected());
        graphTest test2 = new graphTest();
        for (int i = 0; i <1 ; i++) {
            for (int j = 1; j <= test2.myGraph.nodeSize(); j++) {
                test2.myGraph.removeEdge(i,j);
                test2.myGraph.removeEdge(j,i);
            }
        }
        assertEquals(false, test2.meAlgo.isConnected());

    }

    @Test
    void testShortestPath() {
        graphTest test1 = new graphTest();
        assertEquals(1, test1.meAlgo.shortestPath(2,3));

    }

    @Test
    void testGetMC() {
        graphTest test1 = new graphTest();
        test1.myGraph.removeEdge(0,1);
        test1.myGraph.removeEdge(1,0);
        test1.myGraph.removeEdge(2,1);
        assertEquals(92, test1.myGraph.getMC());

    }
}
