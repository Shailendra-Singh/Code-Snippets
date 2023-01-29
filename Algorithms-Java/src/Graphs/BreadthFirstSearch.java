package Graphs;

import CustomCollections.LinkedListQueue;

public class BreadthFirstSearch {
    private final int[] edgeTo;
    private final int[] distTo;
    private final boolean[] marked;

    public BreadthFirstSearch(Graph G, int s) {
        int V = G.V();
        this.edgeTo = new int[V];
        this.distTo = new int[V];
        this.marked = new boolean[V];
        this.bfs(G, s);
    }

    private void bfs(Graph G, int v) {
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        queue.enqueue(v);
        this.marked[v] = true;
        while (!queue.isEmpty()) {
            v = queue.dequeue();
            for (int w : G.adj(v))
                if (!this.marked[w]) {
                    queue.enqueue(w);
                    this.edgeTo[w] = v;
                    this.marked[w] = true;
                    this.distTo[w] = this.distTo[v] + 1;
                }
        }
    }

    /**
     * @param v queried vertex
     * @return is the vertex marked by DFS?
     */
    public boolean isMarked(int v) {
        return this.marked[v];
    }

    /**
     * @param v queried vertex
     * @return previous vertex on path to v
     */
    public int getEdgeTo(int v) {
        return this.edgeTo[v];
    }

    /**
     * @param v queried vertex
     * @return distance of the vertex v from source s
     */
    public int getDistTo(int v) {
        return this.distTo[v];
    }
}