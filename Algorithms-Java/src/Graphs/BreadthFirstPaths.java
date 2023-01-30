package Graphs;

@SuppressWarnings("unused")
public class BreadthFirstPaths extends Paths {
    /**
     * Find paths in G from source s using Breadth First Search algorithm
     *
     * @param G graph
     * @param s source vertex
     */
    public BreadthFirstPaths(Graph G, int s) {
        super(new BreadthFirstSearch(G, s), s);
    }

    /**
     * @param v queried
     * @return number of hops from the source vertex
     */
    public int distTo(int v) {
        return ((BreadthFirstSearch) this.firstSearch).getDistTo(v);
    }
}