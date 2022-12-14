package Problems.PercolationProblem;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n, top, bottom;
    private final boolean[] state;
    private final WeightedQuickUnionUF UF;
    private int openSiteCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.top = 0;
        this.bottom = n * n + 1;
        this.state = new boolean[n * n + 2];
        this.UF = new WeightedQuickUnionUF(n * n + 2);
        this.openSiteCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);

        // Do nothing if already opened
        if (this.state[id]) return;

        this.state[id] = true;
        this.openSiteCount++;

        // Check for connections with TOP and BOTTOM
        if (row == 1) this.UF.union(this.top, id);
        if (row == n) this.UF.union(this.bottom, id);

        // Connect Left, Right, Above, and Bottom sites if they are opened already
        int idLeft = id - 1;
        int idRight = id + 1;
        int idAbove = id - n;
        int idBelow = id + n;

        // Connect Left if open
        if (col > 1 && this.state[idLeft]) {
            this.UF.union(id, idLeft);
        }

        // Connect Right if open
        if (col < n && this.state[idRight]) {
            this.UF.union(id, idRight);
        }

        // Connect Above if open
        if (row > 1 && this.state[idAbove]) {
            this.UF.union(id, idAbove);
        }

        // Connect Below if open
        if (row < n && this.state[idBelow]) {
            this.UF.union(id, idBelow);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);
        return this.state[id];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);
        return this.UF.find(top) == this.UF.find(id);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.UF.find(top) == this.UF.find(bottom);
    }

    private static int getId(int row, int col, int n) {
        return (row - 1) * n + col;
    }

    private static void checkArgumentsRange(int row, int col, int n) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
    }
}