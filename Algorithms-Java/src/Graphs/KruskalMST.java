package Graphs;

import CustomCollections.UnionFind;
import ElementaryAPIs.PriorityQueue;

public class KruskalMST extends MST {
    public KruskalMST(EdgeWeightedGraph G) {
        super(G);
        PriorityQueue<Edge> edgeMinPQ = new PriorityQueue<>(this.minEdgeWeightComparator);
        for (Edge e : this.G.edges())
            edgeMinPQ.insert(e);

        double wt = 0;

        UnionFind uf = new UnionFind(this.G.V());
        while (!edgeMinPQ.isEmpty() && this.edgesQueue.size() < this.G.V() - 1) {
            Edge e = edgeMinPQ.delRoot();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                this.edgesQueue.enqueue(e);
                wt += e.weight();
            }
        }

        this.weight = wt;
    }
}