#  DirectedWeightedGraph
![image](https://user-images.githubusercontent.com/93542763/145729799-45139d78-a5d7-4e34-9a3d-7fdf4723e964.png)

In this project we will deal with directed graphs and all kinds of algorithms that can be run on them.
In the project we will examine the runtimes of the algorithms and see visually how they work.

#  Project departments
The project works so that when we run a graph from one of the json files the graph will be built in the program and then we can run the algorithms on it, add or remove nodes and edges from the graph or get information about nodes and edges in the graph.
The graph is divided into 2 main parts:

A. Graph construction.

B. The algorithms that can be run on the graph.

#  Project idea
The idea of ​​the project is basically to create a directed graph as we wish and the possibility to look at the algorithms that can be run on directed graphs.

#  So how does the project actually work?
The project is divided into 2 parts the first is the creation of the graph itself and the second is a visual presentation of the graph on a gui panel.

#  Creating the graph
The graph is created and works according to the following slide:

![image](https://user-images.githubusercontent.com/93542763/145778013-a32bc937-a3d3-49d0-8b05-6386c16397c5.png)

The graph is created by edgs and nodes as you can see in the slide and you can add or subtract sides and vertices and run new graphs as you wish.

#  classes
*MyGeoLocation* - this class saved The Location of the node.

*MyNodeData* - this class save information about each node:
its exclusive id.
MyGeoLocation information.
HashMap for all the edges from this node to another.
HashMap for all the edges that getting to this node.

*MyEdgeData* - contains to every edge The id source, id detention and the wight of the edge.

*MyDirectedGraph* - contains List of the nodes in the graph(MyNodeData) and all the edges(MyEdgeDate).

*MyDirectedGraphAlgorithm* - this class get MyDirectedGraph and can calculate the next list of algorithms:
If the graph is connected.
A shorted path between 2 nodes.
The ideal center of the graph.
Tsp problem for a group of verticals in the graph

#  DirectedWeightedGraphAlgorithm
The algorithm class is a class where certain algorithms can be run on the graph.
We will now explain each of the existing functions in the class:

*init* - Construction of a new directional graph.

*isConnected* - Finds whether the graph is a link or not. (Is it possible to reach from any node to any other node)

*shortestPathDist*- Finds the shortest trajectory value in the graph between any 2 nodes we want. (Receives source node and destination node)

*shortestPath* - Finds the shortest path in the graph between any 2 nodes we want. (Receives source node and destination node)

*center* - Finds the node with the smallest trajectory value toward all other graphite nodes.

*dijkstra* - dijkstra algorithm. https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

*tsp* - will find the best way to do circle on group of nodes in the graph.

#  Gui
The class uses libraries located in JAVA with which we can display on the screen the algorithms and functions we have implemented:

*MyJframe* - This class is basically the display screen that is seen on the screen in which we get all the functions and all the display options we have and with its help we define all the functional options that we have.

*MyJpanel* - The purpose of this department is to perform all the calculations behind the display screen and in addition is responsible for coloring everything that is seen on the screen.

#  Algorithms Results
![image](https://user-images.githubusercontent.com/93542763/145813558-599b6120-31dd-4a0c-b8f5-4194bf5c288a.png)


#  How to run the project

