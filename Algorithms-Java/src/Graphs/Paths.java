package Graphs;

import CustomCollections.LinkedListStack;

abstract class Paths {
    protected final FirstSearch firstSearch;
    private final int sourceVertex;

    public Paths(FirstSearch firstSearch, int sourceVertex) {
        this.firstSearch = firstSearch;
        this.sourceVertex = sourceVertex;
    }

    /**
     * @param v queried vertex
     * @return is there a path from s to v?
     */
    public boolean hasPathTo(int v) {
        return this.firstSearch.visited(v);
    }

    /**
     * @param v queried vertex
     * @return path from s to v; null if no such path
     */
    @SuppressWarnings("unused")
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedListStack<Integer> path = new LinkedListStack<>();
        for (int x = v; x != this.sourceVertex; x = this.firstSearch.getEdgeTo(x))
            path.push(x);
        path.push(this.sourceVertex);
        return path;
    }
}