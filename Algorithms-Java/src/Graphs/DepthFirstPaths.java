package Graphs;

@SuppressWarnings("unused")
public class DepthFirstPaths extends Paths {
    /**
     * Find paths in G from source s using Depth First Search (recursive) algorithm
     *
     * @param G graph
     * @param s source vertex
     */
    public DepthFirstPaths(Graph G, int s) {
        super(new DepthFirstSearch(G, s), s);
    }
}