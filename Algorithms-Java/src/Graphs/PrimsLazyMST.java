package Graphs;

import ElementaryAPIs.PriorityQueue;

public class PrimsLazyMST extends MST {

    private final boolean[] marked;
    private final PriorityQueue<Edge> edgeMinPQ;

    public PrimsLazyMST(EdgeWeightedGraph G) {
        super(G);
        this.marked = new boolean[G.V()];
        this.edgeMinPQ = new PriorityQueue<>(this.minEdgeWeightComparator);

        visit(0);

        while (!edgeMinPQ.isEmpty()) {
            Edge e = edgeMinPQ.delRoot();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            this.edgesQueue.enqueue(e);
            this.weight += e.weight();
            if (!marked[v]) visit(v);
            if (!marked[w]) visit(w);
        }
    }

    private void visit(int v) {
        this.marked[v] = true;
        for (Edge e : this.G.adj(v))
            if (!this.marked[e.other(v)])
                this.edgeMinPQ.insert(e);
    }
}