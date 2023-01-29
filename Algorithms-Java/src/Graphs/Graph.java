package Graphs;

import CustomCollections.RandomizedBag;

@SuppressWarnings("ALL")
public class Graph {

    private final int V;
    private final RandomizedBag<Integer>[] adj;

    /**
     * Creates an empty graph with V vertices
     *
     * @param V number of vertices
     */
    public Graph(int V) {
        this.V = V;
        //noinspection unchecked
        this.adj = new RandomizedBag[V];
        for (int i = 0; i < V; i++)
            this.adj[i] = new RandomizedBag<>();
    }

    /**
     * Add an edge v-w. Parallel edges are allowed.
     *
     * @param v vertex 1
     * @param w vertex 2
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    /**
     * @param v queried vertex
     * @return vertices adjacent to v
     */
    public Iterable<Integer> adj(int v) {
        return this.adj[v];
    }

    /**
     * @param v queried vertex
     * @return number of adjacent vertices
     */
    public int numOfAdjacents(int v) {
        return this.adj[v].size();
    }

    /**
     * @return number of vertices
     */
    public int V() {
        return this.V;
    }

    /**
     * @return number of edges
     */
    public int E() {
        int edges = 0;
        for (int v = 0; v < this.V; v++)
            edges += this.numOfAdjacents(v);

        return edges / 2; // as they are counted twice
    }

    /**
     * @return string representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < this.V; v++) {
            sb.append(String.format("%s\t->\t", v));
            for (int w : this.adj(v))
                sb.append(String.format("%s\t", w));
            sb.append("\n");
        }

        return sb.toString();
    }
}