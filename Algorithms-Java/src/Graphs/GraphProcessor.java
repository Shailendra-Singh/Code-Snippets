package Graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("unused")
public final class GraphProcessor {
    /**
     * Compute the degree of v in graph G
     *
     * @param G graph
     * @param v vertex
     * @return degree of v
     */
    public static int degree(Graph G, int v) {
        return G.numOfAdjacents(v);
    }

    /**
     * Compute the maximum degree
     *
     * @param G graph
     * @return maximum degree
     */
    public static int maxDegree(Graph G) {
        int maxDegree = 0;
        for (int v = 0; v < G.V(); v++)
            if (degree(G, v) > maxDegree) maxDegree = degree(G, v);

        return maxDegree;
    }

    /**
     * Compute the average degree
     *
     * @param G graph
     * @return average degree
     */
    public static double avgDegree(Graph G) {
        //noinspection IntegerDivisionInFloatingPointContext
        return 2 * G.E() / G.V();
    }

    /**
     * Counts number of self-loops in a graph
     *
     * @param G graph
     * @return number of self-loops
     */
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                if (v == w) count++;

        return count / 2; // each edge counted twice
    }

    /**
     * @param G graph
     * @return is the graph acyclic?
     */
    public static boolean hasCycle(Graph G) {
        return (new DetectCycle(G)).hasCycle();
    }

    /**
     * @param G graph
     * @return is the graph two-colorable
     * (no edge connects vertices of same color)?
     */
    public static boolean isBipartite(Graph G) {
        return (new DetectBipartite(G)).isBipartite();
    }

    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        int v = in.readInt();
        int e = in.readInt();
        DiGraph G = new DiGraph(v);
        for (int i = 0; i < e; i++) {
            int a = in.readInt();
            int b = in.readInt();
            G.addEdge(a, b);
        }
        StdOut.println(G);
    }
}