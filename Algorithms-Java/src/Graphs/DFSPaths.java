package Graphs;

import CustomCollections.LinkedListStack;

@SuppressWarnings("unused")
public class DFSPaths {

    private final DepthFirstSearch DFS;
    private final int sourceVertex;

    /**
     * Find paths in G from source s
     *
     * @param G graph
     * @param s source vertex
     */
    public DFSPaths(Graph G, int s) {
        this.DFS = new DepthFirstSearch(G, s);
        this.sourceVertex = s;
    }

    /**
     * @param v queried vertex
     * @return is there a path from s to v?
     */
    public boolean hasPathTo(int v) {
        return this.DFS.isMarked(v);
    }

    /**
     * @param v queried vertex
     * @return path from s to v; null if no such path
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedListStack<Integer> path = new LinkedListStack<>();
        for (int x = v; x != this.sourceVertex; x = DFS.getEdgeTo(x))
            path.push(x);
        path.push(this.sourceVertex);
        return path;
    }
}