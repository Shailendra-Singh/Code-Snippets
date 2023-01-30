package Graphs;

import CustomCollections.LinkedListStack;

import java.util.Iterator;

/**
 * Return all vertices in reverse DFS post order
 */
public class DepthFirstOrder implements Iterable<Integer> {
    private final boolean[] marked;
    private final LinkedListStack<Integer> reversePost;

    public DepthFirstOrder(DiGraph G) {
        if (GraphProcessor.hasCycle(G))
            throw new UnsupportedOperationException("Cannot find postorder in a cyclic graph.");

        this.reversePost = new LinkedListStack<>();
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!this.marked[v]) dfs(G, v);
    }

    private void dfs(DiGraph G, int v) {
        this.marked[v] = true;
        for (int w : G.adj(v))
            if (!this.marked[w]) dfs(G, w);
        this.reversePost.push(v);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return this.reversePost.iterator();
    }
}