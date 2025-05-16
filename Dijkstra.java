//Import
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

//Class
public class Dijkstra{
    //Create dist array, parant node array, min heap
    // int distanceArray[];
    // Integer parentpointerArray[];
    Map<Integer, Integer> distanceArray = new HashMap<>();
    Map<Integer, Integer> parentpointerArray = new HashMap<>();

    PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(element -> element[1]));
    //private static int startNode;
    Map<Integer, List<int[]>> adjList;
    //Constructor
    public Dijkstra(File inputFile) throws FileNotFoundException{
        //Break up the input file and paste it into an adj list
        adjList = new HashMap<>(); // to store the adj List: Integer is the vertex, int[] contain only neighbor and weight
        try (Scanner scanner = new Scanner(inputFile)){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                Scanner breakupLine = new Scanner(line);
                int source = breakupLine.nextInt();
                int dest = breakupLine.nextInt();
                int weight = breakupLine.nextInt();
                int[] destAndWeight = new int[]{dest, weight}; 
                adjList.putIfAbsent(source, new ArrayList<>());
                adjList.get(source).add(destAndWeight);
                breakupLine.close();
                int[] destAndWeight2 = new int[]{source, weight}; 
                adjList.putIfAbsent(dest, new ArrayList<>());
                adjList.get(dest).add(destAndWeight2);
                // read the line and break it up
                // Loop 3 times and use .next() to read the source node, dest node, weight
                // Let the 1st input to be key, and the second/third input will be put inside the ArrayList (A list of weight and neighbor pairs)
                // Goes on until file is done
            }
            for (List<int[]> neighbors : adjList.values()) {
                neighbors.sort(Comparator.comparingInt(element -> element[0])); // sort by neighbor ID
            }
            // sort the arrayList inside each key
            // Now we have adj list
            //Init size
        }
    }

    public void printDistantArray(){
        System.out.print("Distance array: {");
        for(Integer key : distanceArray.keySet()){
            int distance = distanceArray.get(key);
            if(distance == Integer.MAX_VALUE){
                System.out.print("  "+key + ":inf"+"  ");
            }
            else{
                System.out.print("  "+key + ":" + distance+"  ");
            }
        }
        System.out.println("}");
    }

    public void printPP(){
        System.out.print("Parent node array: {");
        for(Integer key : parentpointerArray.keySet()){
            Integer parent = parentpointerArray.get(key);
            if(parent == null){
                System.out.print("  "+key + ":null"+"  ");
            }
            else{
                System.out.print("  "+key + ":" + parent + "  ");
            }
        }
        System.out.println("}");
    }

    public void printHeap(){
        System.out.print("MinHeap: [");
        //neighbor 0 is weight, 1 is vertex
        for(int[] neighborAndWeight: minHeap){
            System.out.print("("+neighborAndWeight[0]+","+neighborAndWeight[1]+")");
        }
        System.out.print("]  \n");
    }

    public void printNeighbors(int vertex, int addOn){
        System.out.print("Check AdjList["+vertex+"] for neighbors: ");
        if(adjList.get(vertex) == null){
            System.out.println("no neighbor");
            return;
        }
        for(int i = 0; i < adjList.get(vertex).size(); i++){
            // System.out.print("(NODE: "+adjList.get(vertex).get(i)[0]+",DIS: "+adjList.get(vertex).get(i)[1]+addOn+")  ");
            System.out.print("(NODE: " + adjList.get(vertex).get(i)[0] + ",DIS: " + (adjList.get(vertex).get(i)[1] + addOn) + ")  ");

        }
    }

    public void initialize(int startNode)throws FileNotFoundException{
        //Init dist array with all infinite
        //Init parent node array with null
        //Init min heap with start node and distance 0
        for (Integer node : adjList.keySet()) {
            distanceArray.put(node, Integer.MAX_VALUE);
            parentpointerArray.put(node, null);
        }
        minHeap.add(new int[] {startNode, 0});
        distanceArray.put(startNode, 0);
        System.out.println("Initialization:");
        System.out.print("Pick a start node: " + startNode);
        // printDistantArray(startNode);
        printDistantArray();
        printPP();
        printHeap();
    }

    public int[] popMinHeap(){
        //Pop the MinHeap
        int[] popped  = minHeap.poll();
        System.out.print("Popped Heap ");
        printHeap();
        // printNeighbors(popped[0], distanceArray.get(popped[0]));
        return popped;
        //Fix minheap after pop automatically
    } 

    public void updateDistanceAndParentAndHeap(int poppedVertex){
        // use poppedVertex to find it's neighbor (Remember to add this poppedVertex distance (of this node) from the distArray to the new distance result in actual distance from source node)
        // Update distance array acordingly to the distance of each neighbor (less then update, more stay)
        // in adj list: 0 is vertex, 1 is weight
        for (int[] neighbors : adjList.get(poppedVertex)) {
            for(Integer i : distanceArray.keySet()){
                // System.out.println("inside update");
                if(i == neighbors[0] && distanceArray.get(i) > (neighbors[1]+distanceArray.get(poppedVertex))){
                    distanceArray.put(i, neighbors[1]+distanceArray.get(poppedVertex)); //neighbor[0] is the node, neighbor[1] is the weight of that node
                    int temp = distanceArray.get(poppedVertex) + neighbors[1];
                    parentpointerArray.put(i, poppedVertex);
                    minHeap.add(new int[]{neighbors[0], temp}); //Add based on weight
                    // printHeap();
                }
            }
        }
        // printHeap();
        printDistantArray();
        printPP();
        printHeap();
        System.out.println("");
    }

    // public void shortestPathFromNode(int node, int startNode){
    //     System.out.println(startNode+ " to " + node + ":");
    //     for(int i = 0; i < distanceArray.length; i++){
    //         if(i == node && parentpointerArray[i] != null){
    //             System.out.print(" "+i);
    //             node = parentpointerArray[i];
    //             System.out.print(" "+node);
    //             i = 0;
    //         }
    //         else if(i == node && parentpointerArray[i] == null){
    //             System.out.print(" "+i);
    //             break;
    //         }
    //     }
    // }

    public void shortestPathFromNode(int startNode){
        for(Integer node : distanceArray.keySet()){
            if(node == startNode){
                continue;
            }
            // System.out.println(startNode+ " to " + node + ":");
            System.out.println("");
            List<Integer> path = new ArrayList<>();
            Integer cur = node; //make cur the node that wanting to find path
            if (cur == null) {
                System.out.println("No path from " + startNode + " to " + node);
                continue;
            }
            // if current is not start node then add the current to path, and update it with it's parent
            while (cur != null && !cur.equals(startNode)) {
                path.add(cur); // add the path in array
                cur = parentpointerArray.get(cur);
            }
            path.add(startNode);//add start node
            System.out.print("Shortest path from " + startNode + " to " + node + ": ");
            for (int i = path.size(); i >= 1; i--) {
                int distance = path.get(i-1);
                if(distance == Integer.MAX_VALUE){

                }
                else{
                    System.out.print(path.get(i-1));
                }
                if (i != 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.print("  , Distance: " + distanceArray.get(node));
        }
        System.out.println();
    }

    public void printAdjList() {
        for (Map.Entry<Integer, List<int[]>> entry : adjList.entrySet()) {
            int vertex = entry.getKey();
            List<int[]> neighbors = entry.getValue();
            System.out.print("AdjList[" + vertex + "]: ");
            for (int[] edge : neighbors) {
                System.out.print("(" + edge[0] + "," + edge[1] + ") ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
    //Create dist array, parant node array, min heap(1d array no need to add weight in) after getting the # of nodes in graphs (# row of adjList)        
    //Create the loop, stop if Heap empty
        // input: 1st argument is the file, second one is the start node
        // Ex: java Dijkstra file.txt 1
        File inputFile = new File(args[0]);
        int startNode = Integer.parseInt(args[1]);
        Dijkstra thisRun = new Dijkstra(inputFile);
        int numloop = 1;
        thisRun.initialize(startNode);
        thisRun.printAdjList();
        while(!thisRun.minHeap.isEmpty()){
            System.out.println("Iteration: " + numloop);
            int[] nextNode = thisRun.popMinHeap();
            thisRun.updateDistanceAndParentAndHeap(nextNode[0]);
            numloop++;
        }
        System.out.print("Valid shortest path:");
        thisRun.shortestPathFromNode(startNode);
        // thisRun.printAdjList();
    }
}
