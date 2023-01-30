package Graphs;

@SuppressWarnings("unused")
public class DiGraph extends Graph {
    /**
     * Creates an empty graph with V vertices
     *
     * @param V number of vertices
     */
    public DiGraph(int V) {
        super(V);
    }

    /**
     * Add an edge v -> w
     *
     * @param v source vertex
     * @param w destination destination
     */
    @Override
    public void addEdge(int v, int w) {
        this.adj[v].add(w);
        this.E++;
    }

    /**
     * @return reverse of this digraph
     */
    public DiGraph reverse() {
        DiGraph DG = new DiGraph(this.V());
        for (int v = 0; v < this.V(); v++)
            for (int w : this.adj(v))
                DG.addEdge(w, v); // reverse vertex and their adjacent neighbors

        return DG;
    }
}