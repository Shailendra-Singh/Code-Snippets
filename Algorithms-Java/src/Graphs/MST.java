package Graphs;

import CustomCollections.LinkedListQueue;

import java.util.Comparator;

/**
 * Minimum Spanning Tree
 */
public class MST {
    protected final EdgeWeightedGraph G;
    protected final LinkedListQueue<Edge> edgesQueue;
    protected double weight;
    protected Comparator<Edge> minEdgeWeightComparator = new ReverseComparator();
    
    protected MST(EdgeWeightedGraph G){
        this.G = G;
        this.edgesQueue = new LinkedListQueue<>();
        this.weight = 0;
    }

    /**
     * @return edges in MST
     */
    public Iterable<Edge> edges(){
        return this.edgesQueue;
    }

    /**
     * @return weight of MST
     */
    public double weight(){ return this.weight;}
    
    private static class ReverseComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2) {
            return -o1.compareTo(o2);
        }
    }
}