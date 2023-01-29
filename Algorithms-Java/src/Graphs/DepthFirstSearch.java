package Graphs;

public class DepthFirstSearch {
    private final boolean[] marked;
    private final int[] edgeTo;

    public DepthFirstSearch(Graph G, int s) {
        int V = G.V();
        this.marked = new boolean[V];
        this.edgeTo = new int[V];
        this.dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        this.marked[v] = true;
        for (int w : G.adj(v))
            if (!this.marked[w]) {
                dfs(G, w);
                this.edgeTo[w] = v;
            }
    }

    /**
     * @param v queried vertex
     * @return is the vertex marked by DFS?
     */
    public boolean isMarked(int v) {
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