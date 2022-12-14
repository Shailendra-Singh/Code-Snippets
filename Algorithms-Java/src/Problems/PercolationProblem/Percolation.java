package Problems.PercolationProblem;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    /*      Each node's state is stored using bits representing binary, which has unique integer value.

            STATE   |    OPEN  |  TOP  |   BOTTOM
            ---------------------------------------
            000 = 0         0       0       0
            100 = 4         1       0       0
            101 = 5         1       0       1
            110 = 6         1       1       0
            111 = 7         1       1       1       (percolating)
     */

    private static final int STATE_OPEN = 4; // Site is in 'Open' state
    private static final int STATE_OPEN_BOTTOM = 5; // 'Open' and connected to 'Bottom'
    private static final int STATE_OPEN_TOP = 6; // 'Open' and connected to 'Top'
    private static final int STATE_OPEN_TOP_BOTTOM = 7; // 'Open' and connected to 'Top' and 'Bottom'.

    private final int n;
    private final boolean[] isOpen;
    private final WeightedQuickUnionUF UF;
    private final byte[] siteState;
    private int openSiteCount;
    private boolean isPercolating;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.isPercolating = false;
        this.isOpen = new boolean[n * n + 2];
        this.siteState = new byte[n * n + 2];
        this.UF = new WeightedQuickUnionUF(n * n + 2);
        this.openSiteCount = 0;
    }

    private static int getId(int row, int col, int n) {
        return (row - 1) * n + col;
    }

    private static void checkArgumentsRange(int row, int col, int n) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    @SuppressWarnings("DuplicatedCode")
    public void open(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);

        // Do nothing if already opened
        if (this.isOpen[id]) return;

        this.isOpen[id] = true;
        this.openSiteCount++;

        byte state;

        if (this.n == 1) state = STATE_OPEN_TOP_BOTTOM;
        else if (row == 1) state = STATE_OPEN_TOP;
        else if (row == this.n) state = STATE_OPEN_BOTTOM;
        else state = STATE_OPEN;

        // Connect Left, Right, Above, and Bottom sites if they are opened already
        int idLeft = id - 1;
        int idRight = id + 1;
        int idAbove = id - n;
        int idBelow = id + n;

        // Connect Left if open
        if (col > 1 && this.isOpen[idLeft]) {
            state = (byte) (this.siteState[this.UF.find(idLeft)] | state);
            this.UF.union(id, idLeft);
        }

        // Connect Right if open
        if (col < n && this.isOpen[idRight]) {
            state = (byte) (this.siteState[this.UF.find(idRight)] | state);
            this.UF.union(id, idRight);
        }

        // Connect Above if open
        if (row > 1 && this.isOpen[idAbove]) {
            state = (byte) (this.siteState[this.UF.find(idAbove)] | state);
            this.UF.union(id, idAbove);
        }

        // Connect Below if open
        if (row < n && this.isOpen[idBelow]) {
            state = (byte) (this.siteState[this.UF.find(idBelow)] | state);
            this.UF.union(id, idBelow);
        }

        // Update combined state for id (after all bitwise OR operations)
        this.siteState[this.UF.find(id)] = state;

        // If state becomes 7 for any of the node, then system is percolating.
        if (state == STATE_OPEN_TOP_BOTTOM) this.isPercolating = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);
        return this.isOpen[id];
    }

    // is the site (row, col) full?
    @SuppressWarnings("unused")
    public boolean isFull(int row, int col) {
        Percolation.checkArgumentsRange(row, col, this.n);

        int id = Percolation.getId(row, col, this.n);
        return this.siteState[this.UF.find(id)] >= STATE_OPEN_TOP; // Either 6 or 7
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.isPercolating;
    }
}