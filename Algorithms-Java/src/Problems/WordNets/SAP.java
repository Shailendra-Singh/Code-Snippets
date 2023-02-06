package Problems.WordNets;
/******************************************************************************
 *  Compilation:  javac SAP.java
 *  Execution:    none
 *  Dependencies: algs4.jar
 * <p>
 *  An immutable data type SAP to find the common ancestor in the shortest path between two vertices
 * <p>
 *  For use on Coursera, Algorithms Part II programming assignment.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;


/**
 * An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x,
 * together with a directed path from w to the same ancestor x.
 * The shortest ancestral path is an ancestral path of minimum total length.
 * We refer to the common ancestor in the shortest ancestral path as the shortest common ancestor.
 * Note also that an ancestral path is a path, but not a directed path.
 */
public class SAP {

    private static final int CACHE_LIMIT = 100;
    private final HashMap<Integer, HashMap<Integer, Integer>> cachedLengths;
    private final HashMap<Integer, HashMap<Integer, Integer>> cachedAncestors;
    private final Digraph G;

    /**
     * @param G a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Argument(s) is null");
        this.G = G.reverse().reverse();
        this.cachedLengths = new HashMap<>();
        this.cachedAncestors = new HashMap<>();
    }

    /**
     * unit testing of this class
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int ancestor = sap.ancestor(v, w);
            int length = sap.length(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return length of the shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        if (v >= G.V() || w >= G.V())
            throw new IllegalArgumentException("Vertex argument is outside its prescribed range");

        int cachedLength = this.getCachedLength(v, w);
        if (cachedLength != -1) return cachedLength;

        int[] ancLenArr = {-1, -1};
        this.bfs(v, w, ancLenArr);
        this.updateCachedAncestor(v, w, ancLenArr[0]);
        this.updateCachedLength(v, w, ancLenArr[1]);
        return ancLenArr[1];
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        if (v >= G.V() || w >= G.V())
            throw new IllegalArgumentException("Vertex argument is outside its prescribed range");

        int cachedAncestor = this.getCachedAncestor(v, w);
        if (cachedAncestor != -1) return cachedAncestor;

        int[] ancLenArr = {-1, -1};
        this.bfs(v, w, ancLenArr);
        this.updateCachedAncestor(v, w, ancLenArr[0]);
        this.updateCachedLength(v, w, ancLenArr[1]);
        return ancLenArr[0];
    }

    private void bfs(int v, int w, int[] ancLengthArr) {
        Queue<Integer> queue;
        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();
        HashSet<Integer> visited;
        HashSet<Integer> visitedV = new HashSet<>();
        HashSet<Integer> visitedW = new HashSet<>();
        HashMap<Integer, Integer> distTo;
        HashMap<Integer, Integer> distToV = new HashMap<>();
        HashMap<Integer, Integer> distToW = new HashMap<>();
        queueV.enqueue(v);
        queueW.enqueue(w);
        boolean lockStep = true;
        queue = queueV;
        distTo = distToV;
        visited = visitedV;

        int shortestLength = -1, anc = -1;

        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queue.isEmpty()) { // if one queue got processed, continue with the other
                int x = queue.dequeue();
                visited.add(x);
                if (distTo.get(x) == null) distTo.put(x, 0);
                boolean processAdjacentVertices = true;

                int distFromV = (distToV.get(x) == null) ? -1 : distToV.get(x);
                int distFromW = (distToW.get(x) == null) ? -1 : distToW.get(x);

                int currentLength = (distFromV != -1 && distFromW != -1) ? distFromV + distFromW : -1;
                if (currentLength != -1) {
                    if (shortestLength == -1) { // first ancestor
                        shortestLength = currentLength;
                        anc = x;
                    } else if (distFromV > shortestLength && distFromW > shortestLength) {
                        // One of the distance is greater or equal to the shortest distance,
                        // terminate bfs for adjacent vertices of x,
                        // since no further adjacent can result in shorter path length
                        processAdjacentVertices = false;
                    } else if (currentLength < shortestLength) {
                        shortestLength = currentLength;
                        anc = x;
                    }
                }

                if (processAdjacentVertices) {
                    for (int adjVertex : this.G.adj(x))
                        if (!visited.contains(adjVertex)) {
                            visited.add(adjVertex);
                            distTo.put(adjVertex, distTo.get(x) + 1);
                            queue.enqueue(adjVertex);
                        }
                }

            }
            lockStep = !lockStep;

            if (lockStep) {
                queue = queueV;
                distTo = distToV;
                visited = visitedV;
            } else {
                queue = queueW;
                distTo = distToW;
                visited = visitedW;
            }
        }

        ancLengthArr[0] = anc;
        ancLengthArr[1] = shortestLength;
    }

    private void updateCachedAncestor(int v, int w, int anc) {
        if (this.cachedAncestors.size() == CACHE_LIMIT) this.cachedAncestors.clear();

        // put a -> v,w
        if (this.cachedAncestors.get(v) == null || this.cachedAncestors.get(v).size() == CACHE_LIMIT)
            this.cachedAncestors.put(v, new HashMap<>()); // create new table
        if (!this.cachedAncestors.get(v).containsKey(w)) this.cachedAncestors.get(v).put(w, anc);

        // put a -> w,v
        if (this.cachedAncestors.get(w) == null || this.cachedAncestors.get(w).size() == CACHE_LIMIT)
            this.cachedAncestors.put(w, new HashMap<>()); // create new table
        if (!this.cachedAncestors.get(w).containsKey(v)) this.cachedAncestors.get(w).put(v, anc);
    }

    private int getCachedAncestor(int v, int w) {
        if (this.cachedAncestors.containsKey(v)) {
            if (this.cachedAncestors.get(v).containsKey(w)) return this.cachedAncestors.get(v).get(w);
        } else if (this.cachedAncestors.containsKey(w)) {
            if (this.cachedAncestors.get(w).containsKey(v)) return this.cachedAncestors.get(w).get(v);
        }

        return -1;
    }

    private void updateCachedLength(int v, int w, int len) {
        if (this.cachedLengths.size() == CACHE_LIMIT) this.cachedLengths.clear();

        // put len -> v,w
        if (this.cachedLengths.get(v) == null || this.cachedLengths.get(v).size() == CACHE_LIMIT)
            this.cachedLengths.put(v, new HashMap<>()); // create new table
        if (!this.cachedLengths.get(v).containsKey(w)) this.cachedLengths.get(v).put(w, len);

        // put len -> w,v
        if (this.cachedLengths.get(w) == null || this.cachedLengths.get(w).size() == CACHE_LIMIT)
            this.cachedLengths.put(w, new HashMap<>()); // create new table
        if (!this.cachedLengths.get(w).containsKey(v)) this.cachedLengths.get(w).put(v, len);
    }

    private int getCachedLength(int v, int w) {
        if (this.cachedLengths.containsKey(v)) {
            if (this.cachedLengths.get(v).containsKey(w)) return this.cachedLengths.get(v).get(w);
        } else if (this.cachedLengths.containsKey(w)) {
            if (this.cachedLengths.get(w).containsKey(v)) return this.cachedLengths.get(w).get(v);
        }

        return -1;
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Argument(s) is null");
        checkIterables(v, w);
        int a = this.ancestor(v, w);
        if (a == -1) return -1;
        int l = -1;
        int li;
        for (int vi : v) {
            for (int wi : w) {
                li = this.length(vi, wi);
                if (l == -1) l = li;
                else if (li < l) l = li;
            }
        }
        return l;
    }

    /**
     * @param v vertex
     * @param w vertex
     * @return a common ancestor that participates in the shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Argument(s) is null.");
        checkIterables(v, w);
        int a = -1;
        int l = -1;
        int ai;
        int li;
        for (int vi : v) {
            for (int wi : w) {
                ai = this.ancestor(vi, wi);
                li = this.length(vi, wi);

                if (ai == -1) continue; // no ancestor exists; check for next pair
                else if (a == -1) { // first ancestor found in v,w pair
                    a = ai;
                    l = li;
                } else if (li < l) { // update new ancestor, if its length is better than previously found one
                    a = ai;
                    l = li;
                }
            }
        }

        return a;
    }

    private void checkIterables(Iterable<Integer> v, Iterable<Integer> w) {
        for (Integer i : v)
            if (i == null) throw new IllegalArgumentException("Iterable argument contains a null item.");
        for (Integer i : w)
            if (i == null) throw new IllegalArgumentException("Iterable argument contains a null item.");
    }
}