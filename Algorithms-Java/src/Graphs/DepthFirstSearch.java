package Graphs;

public class DepthFirstSearch extends FirstSearch {
    public DepthFirstSearch(Graph G, int s) {
        super(G);
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
}