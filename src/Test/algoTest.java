import DirectedWeightedGraph.Ex2;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class graphTest {
        public DirectedWeightedGraphAlgorithms Graph;
        public DirectedWeightedGraph graph;

        graphTest() {
            //import from "data" file
            graph = Ex2.getGrapg("data//G1.json"); // enter here the path for G1 json file
            Graph = Ex2.getGrapgAlgo("data//1000Nodes.json");
            Graph.init(graph);
        }


        @Test
        void findCenter() {
            int center = Graph.center().getKey();
            assertEquals(8, center);
        }

        @Test
        void isConnected(){
            boolean isConnected = Graph.isConnected();
            assertTrue(isConnected);
        }
        @Test
        void shortestPathDist(){
            double shortest = 4.8330358613553095;
            assertEquals(4.8330358613553095,shortest);

    }}
