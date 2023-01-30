package Graphs;

/**
 * Uses Kosaraju-Sharir algorithm to find strong connected components in a Directed-Graph
 */
@SuppressWarnings("unused")
public class StrongConnectedComponent {
    private final ConnectedComponent cc;

    /**
     * Find strong connected components in G
     *
     * @param G directed graph
     */
    public StrongConnectedComponent(DiGraph G) {
        DepthFirstOrder dfo = new DepthFirstOrder(G.reverse());
        this.cc = new ConnectedComponent(G, dfo);
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return are v and w connected?
     */
    public boolean connected(int v, int w) {
        return this.cc.connected(v, w);
    }

    /**
     * @return number of connected components
     */
    public int count() {
        return this.cc.count();
    }

    /**
     * @param v queried vertex
     * @return component identifier for v
     */
    public int id(int v) {
        return this.cc.id(v);
    }
}