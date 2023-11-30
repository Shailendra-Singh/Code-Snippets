package Graphs;


public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    /**
     * create a weighted edge v-w
     *
     * @param v      edge's first endpoint
     * @param w      edge's second endpoint
     * @param weight edge's weight
     */
    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * @return either endpoint
     */
    public int either() {
        return this.v;
    }

    /**
     * @param vertex current endpoint
     * @return the endpoint that is not v
     */
    public int other(int vertex) {
        if (this.v == vertex)
            return this.w;

        return this.v;
    }

    /**
     * @return edge's weight
     */
    public double weight() {
        return this.weight;
    }

    /**
     * @param that edge
     * @return compares this edge with that edge
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    /**
     * @return string representation of an edge
     */
    @Override
    public String toString() {
        return this.v + " - " + this.w + "\t" + this.weight;
    }
}