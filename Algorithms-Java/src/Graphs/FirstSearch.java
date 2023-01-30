package Graphs;

abstract class FirstSearch {
    protected final boolean[] marked;
    protected final int[] edgeTo;

    public FirstSearch(Graph G) {
        int V = G.V();
        this.marked = new boolean[V];
        this.edgeTo = new int[V];
    }

    /**
     * @param v queried vertex
     * @return is the vertex marked by DFS?
     */
    public boolean visited(int v) {
        return this.marked[v];
    }

    /**
     * @param v queried vertex
     * @return previous vertex on path to v
     */
    public int getEdgeTo(int v) {
        return this.edgeTo[v];
    }
}