# Task 2 Object-Oriented Programming
Directed Weighted Graph Algorithm, designing a graph interface.

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Content</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#about-the-project">Plan and design of The Project</a></li>
    <li><a href="#Gui">GUI</a></li>
    <li><a href="#the-algorithm">The Algorithm</a></li>
    <li><a href="#results">Results</a></li>
    <li><a href="#languages-and-tools">Languages and Tools</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

####Task 2 Object-Oriented Programming

***Authors : Shauli Taragin & Ido Bar***  

In this project we implemented algorithms for developing a data structure into a directional weighted graph which implements many algorithms.

We also created a Graphical User Interface to visualize any given directed weighted graph, and it's implemented methods and algorithms.




---------

## Plan and design of The Project

Upon planning are project we would like to focus on 3 fields for which we wish to plan elaborately before we begin implementing code.
<li> Planning the main classes:</li>
We will implement a class for each interface. Our node , edge, geoLocation and DirectedWeightedGraph classes will hold all the function it implements from the interface as well as variables which we will elaborate on in the next paragraph.
We will also implement an algorithm class which implements DirectedWeightedGraphAlgorithms. In that class we will also create our main algorithm.
<li> Planning the method of implementation:</li>
We will create HashMap that every key is the Node Id and the value is the NodeData Object.
Every NodeData will contain two HashMaps:1. HashMap of the edges that the current node is their source, the key of the HashMap will be the Id of the destination node and the value will be the Edge.
2. HashMap of the Edges that the current node is their destination.
The key will be the Id of the source node and the value will be the weight of the edge.
<li> Laying out testing methods :</li>
We would like to check the next methods:1. Creat graph by adding nodes and edges.
2. Connect two nodes. 3. Delete node. 4. Delete edge from the graph. 5. Get the correct mode count.
6. Get the correct size of edges and the correct size of nodes.









---------

## GUI
#### We will explain about our gui as well as give a short and easy manual of how the user can use it easily.

![Screenshot](our_graph.png)

|*Our gui representation of the graph* |

###### Note - The user must first load a graph from the menu or terminal in order to display it 
* About our GUI :
In our display of the graph the user must first load A graph from a json file of their choice which is located in the data package.

The graph is represented in the following manner : 

Nodes are represented by blue circles. Each nodes key is written above it in black.

Edges are represented by red lines with an arrow at the end showing the direction of the edge.

Pressing on each node will show each node's geolocation. To unshow the location press clear.</li> 
* Functions and algorithm within our GUI 
1. Load: Allows user to load A graph from A json file from our data package.  
2. Save: Allows user to save A graph into A json file to our data package.
3. Edit graphs: Allows user to make changes to graph.
    1. Add Node: Allows user to add a node to the graph by typing in the id(key) of the node and its geolocation. If the node already exits it will rewrite its location. Writing wrong id or wrong cordinance will throw an error.
    2. Remove Node:Allows user to remove a node from the graph by typing in the id(key) of the node. Writing wrong id  will throw an error.
    3. Connect: Allows user to connect between 2 nodes an edge(with direction src/dst) in the graph by typing in the id(key) of the source node, id(key) of the destination node  and the weight of the edge. If the edge already exits it will rewrite its weight. Writing wrong id or wrong weight will throw an error.
    4. Remove edge:Allows user to remove an edge from the graph by typing in the id(key) of the node src and id(key) of the node dest. Writing wrong id's  will throw an error.
4. Algorithms: Allows user to run and visualize algorithms on the graph .
   1. Center: Pressing this button will represent on the screen what is the center of the graph. The node which is colored in light blue is the center node of the graph.
   2. IsConnected:Pressing this button will represent on the screen A message that lets the user know whether the graph is connected.
   3. TSP: Allows user to insert nodes(by key) into the tsp algorithm. When the user is ready they can press submit, and the screen will show the path taken visiting all the nodes in tsp. First node is marked in orange,final node in pink. The edges which have been visited will become black(including their arrows).
   4. Shortest Dist: Allows user to insert 2 nodes(by key of src and dest). The screen will show the shortest path possible between the nodes. First node is marked in orange , final node in pink. The edges which have been visited will become black(including their arrows).
   5. Shortest Dist Path: Allows user to insert 2 nodes(by key of src and dest). The screen will show a message with the shortest path possible between the nodes by the sum of the weights taken.
5. Exit: Allows the user to exit the program.

* Help button refers the user to this user manual in our github repository.

* Clear button clears the drawings on top of the loaded graph. i.e geolocation , shortestdist, center etc..

![Screenshot](graph_draw.png)
| *Our gui representation of the shortest path and some geolocations * |
---------

## The Algorithm
***The Directed Weighted Graph Algorithm interface is implemented in MyDWGAlgo class:***
***The MyDWGAlgo object contains a class member of A graph inorder to activate the algorithms on.***

We will lay out our graph Theory algorithms and explain how we implemented them:

1. **init**- Initializes the graph from a json file .
1. **copy**- We create a Deep copy of the graph using our copy function from MyDWG.
4. **isConnected** -Checks whether the graph is strongly connected.
   1. We implemented this algorithm by running a bfs on the graph. Then traversing the graph. And then another bfs.
   2. Then we iterated through all the nodes in the graph if we find that some node has not been visited we know the graph is not connected.
5. **shortestPathDist**- Calculates the shortest path distance between 2 given nodes.
   1. We implement this algorithm as well as the shortestpath and tsp algorithm in the following manner.
   2. In order to find the shortest path between two nodes we run the dijkstra algorithm using a data structure of Min Heap.Both these methods are implemnted in the DijkstraUsingMinHeap class
   3. Then fitting the algorithm to our data structures that we are using to represent the graph.
   4. Finally, returning the weight of the shortest path
6. **shortestPath** - Finds the shortest path (at what edges should we use the path) between 2 given nodes in the graph.
   1. We run the same methods as 5. However , we hold with arrays the path we took to reach the shortest distance
   2. We iterate over our arrays and return in a list the path the algorithm traveled.
6. **Center** - Finds the shortest path (at what edges should we use the path) between 2 given nodes in the graph.   
7. **TSP**- Computes a relatively short path which visit each node in the cities List.
   1. We run the same methods as 5. With additions that we run each of the cities as a src and find in a greedy way the shortest path to travel through all the cities
   2. In this solution we also hold with arrays the path we took to reach the shortest distance.
   2. We iterate over our arrays and return in a list the path the algorithm traveled.
3.   **save**- Saves the graph to a json file.
3.   **Load**- Loads the graph in to a json file.



---------
<!-- results -->
## Results

Our best Results:

|Algorithms|Small graph up to 1000 nodes|10000 nodes|100000 nodes|1000000 nodes|
|---------|---------|---------|---------|---------|
|**Init**|Very quick|Very quick|Very quick|Not enough ram to create |
|**Get graph**|Very quick|Very quick|Very quick|Not enough ram to create |
|**Is connected**|Very quick|Very quick|4 seconds|Not enough ram to create
|**shortestPathDist**|Very quick|Very quick|Very quick even for far distances|Not enough ram to create
|**shortestPath**|Very quick|Very quick|Very quick even for far distances|Not enough ram to create
|**center**|Very quick 1000 takes 1 second|1.2 minutes|Computer not strong enough , would take a few hours|Not enough ram to create
|**tsp**|Very quick however if we put many nodes in the cities it can take up to a few minutes|Up to a few nodes in the cities Very quick. Up to 40 nodes within a couple of minutes|Up to a few nodes it's able to run.|Not enough ram to create



As you can see our results after analyzing the code,our code is efficient. Besides running in good times are tsp algorithm returns a very accurate result.



---------


## Languages and Tools

  <div align="center">

<code><img height="50" width="50" src="https://icon-library.com/images/java-icon-png/java-icon-png-15.jpg"></code>
<code><img height="40" width="70" src="https://upload.wikimedia.org/wikipedia/commons/d/d5/UML_logo.svg"/></code>
<code><img height="40" width="40" src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/IntelliJ_IDEA_Icon.svg/768px-IntelliJ_IDEA_Icon.svg.png"/></code>
<code><img height="40" height="40" src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/git/git.png"></code>
<code><img height="40" height="40" src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/terminal/terminal.png"></code>
  </div>


<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [Java](https://en.wikipedia.org/wiki/Java_(programming_language))
* [UML](https://en.wikipedia.org/wiki/Unified_Modeling_Language)
* [Git](https://git-scm.com/)

* [Git-scm](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)


<!-- CONTACT -->
## Contact

Ido - [here](https://github.com/idobar1403/)

Shauli - [here](https://github.com/ShauliTaragin/)

Project Link: [here](https://github.com/ShauliTaragin/OOP_Ex2)