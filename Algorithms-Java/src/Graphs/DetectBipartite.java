package Graphs;

/**
 * Finds whether the vertices of a given graph be assigned one of the two colors
 * in such a way that no edge connects vertices of the same color.
 */
public class DetectBipartite {
    private final boolean[] marked;
    private final boolean[] color;
    private boolean isBipartite;

    public DetectBipartite(Graph G) {
        this.marked = new boolean[G.V()];
        this.color = new boolean[G.V()];
        this.isBipartite = true;
        for (int v = 0; v < G.V(); v++)
            if (!this.marked[v]) this.dfs(G, v);
    }

    private void dfs(Graph G, int v) {
        this.marked[v] = true;
        for (int w : G.adj(v))
            if (!this.marked[w]) {
                this.color[w] = !this.color[v];
                dfs(G, w);
            } else if (this.color[w] == this.color[v]) this.isBipartite = false;
    }

    /**
     * @return is the graph bipartite?
     */
    public boolean isBipartite() {
        return this.isBipartite;
    }
}