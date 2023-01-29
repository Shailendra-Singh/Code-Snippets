package Graphs;

@SuppressWarnings("unused")
public class ConnectedComponent {
    private final boolean[] marked;
    private final int[] id;
    private int count;

    /**
     * Find connected components in G
     *
     * @param G graph
     */
    public ConnectedComponent(Graph G) {
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        this.count = 0;
        for (int v = 0; v < G.V(); v++)
            if (!this.marked[v]) {
                dfs(G, v);
                this.count++;
            }
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return are v and w connected?
     */
    public boolean connected(int v, int w) {
        return this.id(v) == this.id(w);
    }

    /**
     * @return number of connected components
     */
    public int count() {
        return this.count;
    }

    /**
     * @param v queried vertex
     * @return component identifier for v
     */
    public int id(int v) {
        return this.id[v];
    }

    private void dfs(Graph G, int v) {
        this.marked[v] = true;
        this.id[v] = this.count;
        for (int w : G.adj(v))
            if (!this.marked[w]) dfs(G, w);
    }
}