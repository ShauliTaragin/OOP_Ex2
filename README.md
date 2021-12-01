# Task 2 Object-Oriented Programming
Directed Weighted Graph Algorithm, designing a graph interface.

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Content</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#about-the-project">Plan and design of The Project</a></li>
    <li><a href="#the-algorithm">The Algorithm</a></li>
    <li><a href="#code-details">Code Details</a></li>
    <li><a href="#results">Results</a></li>
    <li><a href="#languages-and-tools">Languages and Tools</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Task 2 Object-Oriented Programming




---------

## Plan and design of The Project-

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

## The Algorithm


