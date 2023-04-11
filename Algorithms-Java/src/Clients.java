import Graphs.*;
import edu.princeton.cs.algs4.In;

@SuppressWarnings("unused")
public final class Clients {

    private static EdgeWeightedGraph CreateEdgeWeightedGraph(String[] args){
        In in = new In(args[0]);
        int v = in.readInt();
        EdgeWeightedGraph G = new EdgeWeightedGraph(v);
        int e = in.readInt();
        for (int i = 0; i < e; i++) {
            int from = in.readInt();
            int to = in.readInt();
            double wt = in.readDouble();
            Edge edge = new Edge(from, to, wt);
            G.addEdge(edge);
        }
        return  G;
    }
    public static void KruskalMSTClient(String[] args){
        EdgeWeightedGraph G = CreateEdgeWeightedGraph(args);

        MST mst = new KruskalMST(G);
        for (Edge _e : mst.edges())
            System.out.println(_e);
        System.out.printf("Weight: %.3f",  mst.weight());
        System.out.println();
    }

    public static void PrimsLazyMSTClient(String[] args){
        EdgeWeightedGraph G = CreateEdgeWeightedGraph(args);

        MST mst = new PrimsLazyMST(G);
        for (Edge _e : mst.edges())
            System.out.println(_e);
        System.out.printf("Weight: %.3f",  mst.weight());
        System.out.println();
    }
}