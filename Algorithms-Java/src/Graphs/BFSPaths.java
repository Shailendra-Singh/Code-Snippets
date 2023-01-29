package Graphs;

import CustomCollections.LinkedListStack;

@SuppressWarnings("unused")
public class BFSPaths {
    private final BreadthFirstSearch BFS;
    private final int sourceVertex;

    public BFSPaths(Graph G, int s) {
        this.BFS = new BreadthFirstSearch(G, s);
        this.sourceVertex = s;
    }

    /**
     * @param v queried vertex
     * @return is there a path from s to v?
     */
    public boolean hasPathTo(int v) {
        return this.BFS.isMarked(v);
    }

    /**
     * @param v queried vertex
     * @return path from s to v; null if no such path
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedListStack<Integer> path = new LinkedListStack<>();
        for (int x = v; x != this.sourceVertex; x = BFS.getEdgeTo(x))
            path.push(x);
        path.push(this.sourceVertex);
        return path;
    }

    /**
     * @param v queried
     * @return number of hops from the source vertex
     */
    public int distTo(int v) {
        return this.BFS.getDistTo(v);
    }
}