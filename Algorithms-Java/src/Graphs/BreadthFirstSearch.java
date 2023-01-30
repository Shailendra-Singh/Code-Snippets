package Graphs;

import CustomCollections.LinkedListQueue;

public class BreadthFirstSearch extends FirstSearch {
    private final int[] distTo;

    public BreadthFirstSearch(Graph G, int s) {
        super(G);
        this.distTo = new int[G.V()];
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
     * @return distance of the vertex v from source s
     */
    public int getDistTo(int v) {
        return this.distTo[v];
    }
}