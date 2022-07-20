import java.io.*;
import java.util.ArrayList;

public class UnweightedGraphs {

    // fields
    public int numNodes;
    public int numEdges;
    public int[][] graph;
    public ArrayList<Node> nodeList = new ArrayList<Node>();

    static class Node {
        public int index;
        public ArrayList<Node> next = new ArrayList<Node>();
        public Node parent;
        public boolean visited = false;
        public int distance = 0;

        public Node(int index) {
            this.index = index;
        }
    }

    // constructor
    public UnweightedGraphs(File file) throws IOException {
        this.graph = readGraphFromFile(file);

        for (int i = 0; i < numNodes; i++) {
            nodeList.add(new Node(i));
        }
        for (int i = 0; i < graph.length; i++) {
            nodeList.get(graph[i][0]).next.add(nodeList.get(graph[i][1]));
        }
    }

    // Search/sorting algorithms
    public void breadthFirstSearch(int index) {
        Node start = nodeList.get(index);
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(start);
        start.visited = true;

        while (queue.size() > 0) {
            Node n = queue.get(queue.size() - 1);
            queue.remove(queue.size() - 1);

            for (int i = 0; i < n.next.size(); i++) {
                if (n.next.get(i).visited == false) {
                    Node nextN = n.next.get(i);
                    nextN.distance += n.distance + 1;

                    nextN.parent = n;
                    nextN.visited = true;
                    queue.add(nextN);
                }
            }

        }
    }

    public ArrayList<Integer> topologicalSort() {
        ArrayList<Integer> retTable = new ArrayList<Integer>();

        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).visited = false;
        }

        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).visited == false) {
                topological_DFS(retTable, i);
            }
        }
        return retTable;
    }

    public void topological_DFS(ArrayList<Integer> edges, int start) {
        nodeList.get(start).visited = true;
        for (int i = 0; i < nodeList.get(start).next.size(); i++) {
            Node nextN = nodeList.get(start).next.get(i);
            if (nextN.visited == false) {
                topological_DFS(edges, nextN.index);
            }
        }
        edges.add(start);
    }

    //Support methods
    public int[][] readGraphFromFile(File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));

        String line;
        line = bf.readLine().trim();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ' && line.charAt(i - 1) == ' ')
                line = line.substring(0, i - 1) + line.substring(i, line.length());
        }

        String[] thisLine = line.split(" ");
        int numNodes = Integer.parseInt(thisLine[0]);
        int numEdges = Integer.parseInt(thisLine[1]);
        this.numNodes = numNodes;
        this.numEdges = numEdges;

        int[][] returnTable = new int[numEdges][2];
        for (int i = 0; i < numEdges; i++) {
            line = bf.readLine().trim();
            for (int u = 0; u < line.length(); u++) {
                if (u != 0 && line.charAt(u) == ' ' && line.charAt(u - 1) == ' ') {
                    line = line.substring(0, u - 1) + line.substring(u, line.length());
                    u--;
                }
            }
            thisLine = line.split(" ");

            returnTable[i][0] = Integer.parseInt(thisLine[0]);
            returnTable[i][1] = Integer.parseInt(thisLine[1]);
        }
        bf.close();
        return returnTable;
    }

    public void printUG() { // Method for printing results
        System.out.println("Node, parent, distance");
        for (int i = 0; i < numNodes; i++) {
            if (nodeList.get(i).parent != null) {
                System.out.println(i + ", " + nodeList.get(i).parent.index + ", " + nodeList.get(i).distance);
            } else {
                System.out.println(i + ",  , " + nodeList.get(i).distance);
            }
        }
    }
}