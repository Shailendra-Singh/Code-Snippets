package Graphs;

public class DetectCycle {
    private final boolean[] marked;
    private boolean hasCycle;

    /**
     * Detect cycle in a Graph
     *
     * @param G graph
     */
    public DetectCycle(Graph G) {
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!this.marked[v]) this.dfs(G, v, v);
    }

    private void dfs(Graph G, int v, int u) {
        this.marked[v] = true;
        for (int w : G.adj(v))
            if (!this.marked[w]) {
                dfs(G, w, v);
            } else if (w != u) this.hasCycle = true;
    }

    /**
     * @return is the given graph acyclic?
     */
    public boolean hasCycle() {
        return this.hasCycle;
    }
}