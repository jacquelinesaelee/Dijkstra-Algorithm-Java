// Jacqueline Saelee
// COMP 282
// February 22, 2018
package graph;

import java.util.*;

class EdgeNode {

    int destVertex;
    int weight;

    // constructor
    public EdgeNode(int v, int w) {
        destVertex = v;
        weight = w;
    }

    public String toString() {
        String s = "(" + destVertex + "," + weight + ")";
        return s;
    }
}// EdgeNode

class DistNode implements Comparable<DistNode> {

    public int vertex;
    public int distance;

    public DistNode(int v, int d) {
        vertex = v;
        distance = d;
    }
    
    public String toString(){
        String s = "[v = " + vertex + " ,d = " + distance + "]\n";
        return s;
    }

    public int compareTo(DistNode node) {
        if (this.distance < node.distance) {
            return -1;
        } else if (this.distance == node.distance) {
            return 0;
        } else {
            return 1;
        }
    }
}// DistNode

public class Graph {

    private LinkedList<EdgeNode>[] adjList;
    private int nVertices;
    private int nEdges;
    private boolean[] visited;

    // Constructor
    // HELP
    public Graph(int[][] graphData, int nVertices) {
        // initialize graph values
        this.nVertices = nVertices;
        nEdges = graphData.length;

        // Create AdjList
        adjList = new LinkedList[nVertices];
        // Create Linked List inside AdjList
        for (int i = 0; i < nVertices; i++) {
            adjList[i] = new LinkedList<EdgeNode>();
        }

        //insert vertices
        // LOOP through entire length
        for (int i = 0; i < nEdges; i++) {
            // LOOP through vertices
            for (int k = 0; k < nVertices; k++) {
                if (graphData[i][0] == k) {
                    adjList[k].add(new EdgeNode(graphData[i][1], graphData[i][2]));
                }
            }
        }
    }

    // Print
    // HELP
    public void printGraph() {
        //int adjListSize;
        System.out.println("\nGraph: nVertices = " + nVertices + " nEdges = " + nEdges);
        System.out.println("Adjacency Lists");

        try {
            for (int i = 0; i < nVertices; i++) {
                System.out.print("\nv = " + i + " [");
                if (adjList[i] != null) {
                    for (int k = 0; k < adjList[i].size(); k++) {
                        System.out.print("[" + adjList[i].get(k).destVertex + ",");
                        System.out.print(adjList[i].get(k).weight + "]");
                    }
                }
            }
            System.out.println("]");
        } catch (java.lang.NullPointerException e) {
        }
    }

    // DFS
    public void dfsTraversal(int startVertex) {
        visited = new boolean[nVertices];
        for (int i = 0; i < nVertices; i++) {
            visited[i] = false;
        }
        dfs(startVertex);
    }

    private void dfs(int startVertex) {
        // Mark "startVertex" as visited
        visited[startVertex] = true;
        // Print out startVertex
        System.out.println("Vertex " + startVertex + " has been visited");

        // For each vertex "w" in adjacencyList[v]
        // if "w" is not visited, dfs(w)
        for (int i = 0; i < adjList[startVertex].size(); i++) {
            if (visited[adjList[startVertex].get(i).destVertex] == false) {
                dfs(adjList[startVertex].get(i).destVertex);
            }
        }
    }

    // dijkstra 
    public void dijkstraShortestPaths(int startVertex) {
        // Initialize VARS and Arrays
        int count = 0, start = startVertex;
        int[] d;
        int[] parent;
        d = new int[nVertices];
        parent = new int[nVertices];
        DistNode u;

        // 10000 is MAX/Infinity
        for (int i = 0; i < nVertices; i++) {
            parent[i] = -1;
            d[i] = 10000;
        }

        // Initialize Start vertex distance to 0
        d[startVertex] = 0;

        // Setup Priotiry Queue
        PriorityQueue<DistNode> pq = new PriorityQueue<DistNode>();
        for(int i = 0; i < nVertices; i++){
            pq.add(new DistNode(i, d[i]));
        }
        
    
        //
        while (count < nVertices && !pq.isEmpty()) {
            // remove DistNode with d[u] value
            u = pq.remove();
            
            count++;
            
            // for each v in adjList[u] (adjacency list for vertex u)
            for(int i = 0; i < adjList[u.vertex].size();i++){
                // v
                int v = adjList[u.vertex].get(i).destVertex;
                // w(u,v)
                int vWt = adjList[u.vertex].get(i).weight;
                
                if((d[u.vertex] + vWt) < d[v]){
                    d[v] = d[u.vertex] + vWt;
                    parent[v] = u.vertex;
                    pq.add(new DistNode(v,d[v]));
                }   
            }            
        }
        printShortestPaths(start, d, parent);
    }

    private void printShortestPaths(int start, int[] distance, int[] parent) {
            int findParent;
            System.out.println("Shortest path from vertex " + start + " to vertex");
            for(int i = 0; i < nVertices; i++){
                System.out.print(i + ": ");
                if(distance[i] == 10000)
                    System.out.println("There is no such path");
                else{
                    findParent = parent[i];
                    System.out.print("[");
                    while(findParent != -1){
                        System.out.print(findParent + ", ");
                        findParent = parent[findParent];  
                }
                  
                    System.out.println(i + "]  Path weight: " + distance[i]);
                }
            }
    }

    public static void main(String[] args) {
        System.out.println("Jacqueline Saelee");
        System.out.println("Comp 282");
        System.out.println("Project 2 - Djikstra's Algorithm and DFS");
        System.out.println("February 22, 2018");
        
        
        System.out.println("\n** Slight error in printShortestPaths method\n*** Please note that if the Path length is greater than 1\n"
                + "*** that the vertices from 1 to n-1 are in reverse order\n*** The Dijstra Algorithm is corect, however the \n*** printShortestPath is partially reversed");
        
        int nVertices = 5;
        int[][] graphData = {{0, 1, 20}, {2, 0, 30}, {1, 2, 20}, {2, 3, 10}, {0, 3, 40}, {4, 3, 12}, {1, 3, 60}, {3, 0, 15}};
        Graph g = new Graph(graphData, nVertices);
        System.out.println("\nTest 1a -- print graph ");
        g.printGraph();
        int start = 1;
        System.out.println("\nTest 1b  -- dfs");
        g.dfsTraversal(start);
        System.out.println("\n Test 1c -- Dijkstra SP");
        g.dijkstraShortestPaths(start);
        
        System.out.println("\nTest cases For Graph 2");
        int nVertices2= 8;
        int[][] graphData2 ={{4,5,6} , { 2,3,4}, {7,3,2}, {3,6,7}, {3,4,10}, {6,1,18}, {4,3,12},{2,7,16}, {6,4,17}, {1,7,20},{4,6,3}, {2,3,4}, {2,5,7}, {1,5,15},{0,1,5}, {0,3,5}, {0,6,9}, {5,3,2}, {7,3,12},  {4,6,12}, {7,6,14} ,{5,6,18}, {3,2,10}, {7,0,3} , {6,0,12}, {4,2,5}, {0,1,7}, {7,6,23} };
        Graph g2 = new Graph(graphData2, nVertices2);
        System.out.println("\nTest 2a -- print graph ");
        g2.printGraph();
        int start2 = 4;
        System.out.println("\nTest 2b  -- dfs");
        g2.dfsTraversal(start2);
        System.out.println("\n Test 2c -- Dijkstra SP");
        g2.dijkstraShortestPaths(start2);
        
        System.out.println("\n\nMy Test Cases for Graph 3");
        int nVertices3 = 4;
        int[][] graphData3 = {{0, 1, 2}, {0,3,20}, {1, 2, 5}, {2, 3, 3}};
        Graph g3 = new Graph(graphData3, nVertices3);
        System.out.println("\nMy Test 3a -- print graph ");
        g3.printGraph();
        int start3 = 0;
        System.out.println("\nTest 3b -- dfs");
        g3.dfsTraversal(start3);
        System.out.println("\nMy Test 3c -- Dijkstra SP");
        g3.dijkstraShortestPaths(start3);
        
        System.out.println("\n\nMy Test Cases for Graph 4");
        int nVertices4 = 7;
        int[][] graphData4 = {{0, 1, 30}, {0, 2, 15}, {2, 1, 10}, {1, 6, 8}, {3, 4, 19}, {6, 4, 7}, {3,2, 12}, {6, 2, 5}, {5, 3, 9}, {4, 5, 15}};
        Graph g4 = new Graph(graphData4, nVertices4);
        System.out.println("\nMy Test 4a -- print graph ");
        g4.printGraph();
        int start4 = 2;
        System.out.println("\nTest 4b -- dfs");
        g4.dfsTraversal(start4);
        System.out.println("\nMy Test 4c -- Dijkstra SP");
        g4.dijkstraShortestPaths(start4);
    }

}