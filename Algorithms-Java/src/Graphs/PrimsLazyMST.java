package Graphs;

import ElementaryAPIs.MinPriorityQueue;

public class PrimsLazyMST extends MST {

    private final boolean[] marked;
    private final MinPriorityQueue<Edge> edgeMinPQ;

    public PrimsLazyMST(EdgeWeightedGraph G) {
        super(G);
        this.marked = new boolean[G.V()];
        this.edgeMinPQ = new MinPriorityQueue<>();

        // assume G is connected
        visit(0);

        while (!edgeMinPQ.isEmpty()) {
            // repeatedly delete min weight edge from PQ
            Edge e = edgeMinPQ.delMin();
            int v = e.either(), w = e.other(v);

            // ignore if both endpoints in T
            if (marked[v] && marked[w]) continue;

            // add edge e to tree
            this.edgesQueue.enqueue(e);
            this._weight += e.weight();

            // add v or w to the tree
            if (!marked[v]) visit(v);
            if (!marked[w]) visit(w);
        }
    }

    private void visit(int v) {
        this.marked[v] = true;
        for (Edge e : this.G.adj(v))
            if (!this.marked[e.other(v)]) this.edgeMinPQ.insert(e);
    }
}