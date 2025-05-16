# Dijkstra’s Algorithm – Shortest Path Finder

This project implements Dijkstra’s Algorithm to find the shortest paths from a specified starting node to all other nodes in a weighted, undirected graph. The graph is provided through an edge list stored in a plain text file. Each line of the file represents one edge and consists of three values: source node, destination node, and weight, separated by whitespace.

The program takes two command-line arguments: the name of the input file and the name of the starting node. It processes the graph using a min-heap to select the next node with the smallest tentative distance, efficiently updating distances and parent nodes at each step.

At each iteration of the algorithm, the program prints the current state of the distance array, the parent node array, and the contents of the min-heap. After the algorithm completes, it outputs the list of valid shortest paths from the starting node to all other reachable nodes, along with their corresponding shortest distances.

Example input file (graph.txt):
A B 4  
A C 2  
B C 1  
B D 5  
C D 8  
C E 10  
D E 2  

In the above file, each line represents an undirected edge with the specified weight. For example, the line "A B 4" means there is an edge between node A and node B with a weight of 4.

To run the program, use the command:
./dijkstra graph.txt A
This command computes the shortest paths from node A using the graph defined in "graph.txt".
