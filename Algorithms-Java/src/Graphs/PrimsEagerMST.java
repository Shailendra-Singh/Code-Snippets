package Graphs;

import CustomCollections.LinkedListQueue;
import ElementaryAPIs.MinIndexPriorityQueue;

public class PrimsEagerMST extends MST {
    private final Edge[] edgeTo;
    private final double[] distTo;
    private final boolean[] marked;
    private final MinIndexPriorityQueue<Double> pq;

    public PrimsEagerMST(EdgeWeightedGraph G) {
        super(G);
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        pq = new MinIndexPriorityQueue<>(G.V());

        distTo[0] = 0.0;
        // Initialize pq with 0, weight 0
        pq.insert(0, 0.0);

        while (!pq.isEmpty()) visit(G, pq.delMin());
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // Add v to tree; update data structures
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue; // v-w is ineligible

            if (e.weight() < distTo[w]) {
                double oldWeight = distTo[w];

                // Edge e is new best connection from tree to w
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) {
                    // Update running MST weight
                    this._weight = this._weight - oldWeight + distTo[w];
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                    // Update running MST weight
                    this._weight += distTo[w];
                }
            }
        }
    }

    /**
     * @return edges in MST
     */
    @Override
    public Iterable<Edge> edges() {
        LinkedListQueue<Edge> mst = new LinkedListQueue<>();
        for (int v = 1; v < edgeTo.length; v++)
            mst.enqueue(edgeTo[v]);

        return mst;
    }
}