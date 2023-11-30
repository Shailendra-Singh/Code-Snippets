package Graphs;

import CustomCollections.LinkedListQueue;

/**
 * Minimum Spanning Tree
 */
public abstract class MST {
    protected final EdgeWeightedGraph G;
    protected final LinkedListQueue<Edge> edgesQueue;
    protected double _weight;

    protected MST(EdgeWeightedGraph G) {
        this.G = G;
        this.edgesQueue = new LinkedListQueue<>();
        this._weight = 0;
    }

    /**
     * @return edges in MST
     */
    public Iterable<Edge> edges() {
        return this.edgesQueue;
    }

    /**
     * @return weight of MST
     */
    public double weight() {
        return this._weight;
    }
}