package Graphs;

import CustomCollections.RandomizedBag;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class EdgeWeightedGraph {
    protected final RandomizedBag<Edge>[] adj;
    private final int V;
    protected int E;

    /**
     * Creates an empty graph with V vertices
     *
     * @param V number of vertices
     */
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        //noinspection unchecked
        this.adj = new RandomizedBag[V];
        for (int i = 0; i < V; i++)
            this.adj[i] = new RandomizedBag<>();
    }

    /**
     * Add weighted edge to this graph
     *
     * @param e weighted edge
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        this.adj[v].add(e);
        this.adj[w].add(e);
        this.E++;
    }

    /**
     * @param v queried vertex
     * @return edges incident to v
     */
    public Iterable<Edge> adj(int v) {
        return this.adj[v];
    }

    /**
     * @return edges in this graph
     */
    public Iterable<Edge> edges() {
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (int v = 0; v < this.V; v++) {
            for (Edge e : this.adj[v]) {
                if (e.either() == v)
                    edgeList.add(e);
            }
        }
        return edgeList;
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
        return this.E;
    }

    /**
     * @return string representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e : this.edges())
            sb.append(e);

        return sb.toString();
    }
}