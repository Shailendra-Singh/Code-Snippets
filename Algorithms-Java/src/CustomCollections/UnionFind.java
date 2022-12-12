package CustomCollections;

public class UnionFind {

    private final int[] id; // every node is root for itself
    private final int[] sz; // every node has size of its connected components
    private int count; // number of components

    public UnionFind(int n) {
        this.id = new int[n];
        this.sz = new int[n];
        this.count = n;

        for (int i = 0; i < n; i++) {
            this.id[i] = i;
            this.sz[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return this.find(p) == this.find(q);
    }

    public void union(int p, int q) {
        int pid = this.find(p);
        int qid = this.find(q);
        // ignore union for nodes in same connected component
        if (pid == qid) return;
        // weighted union: smaller components points to larger component
        if (this.sz[pid] < this.sz[qid]) {
            this.id[pid] = qid;
            this.sz[qid] += this.sz[pid];
        } else {
            this.id[qid] = pid;
            this.sz[pid] += this.sz[qid];
        }
        this.count--;
    }

    public int find(int k) {
        // follow parent until parent is in its own index
        while (k != this.id[k]) {
            // path compression: every node points to its grandparent (halving the path length)
            this.id[k] = this.id[this.id[k]];
            k = this.id[k];
        }
        return k;
    }


    public int count() {
        // returns number of connected components
        return this.count;
    }
}
