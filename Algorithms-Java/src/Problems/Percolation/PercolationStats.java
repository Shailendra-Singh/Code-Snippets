package Problems.Percolation;
/******************************************************************************
 *  Compilation:  javac PercolationStats.java Percolation.java
 *  Execution:    java PercolationStats
 *  Dependencies: Percolation
 *
 *  A data structure that performs independent trials on an NxN 2-D grid.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CI_FACTOR = 1.96;
    private final int trials;
    private final double[] thresholdList;

    /**
     * perform independent trials on an n-by-n grid
     * @param n size of grid
     * @param trials number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.trials = trials;

        this.thresholdList = new double[trials];

        Percolation percolation;

        // Perform trials
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);

            while (!percolation.percolates()) {
                // select random site
                int blockedSite = StdRandom.uniformInt(1, n * n + 1);
                int row = getRow(blockedSite, n);
                int col = getCol(blockedSite, n);
                // repeat until blocked site is found
                while (percolation.isOpen(row, col)) {
                    blockedSite = StdRandom.uniformInt(1, n * n + 1);
                    row = getRow(blockedSite, n);
                    col = getCol(blockedSite, n);
                }
                percolation.open(row, col);
            }

            double threshold = (1.0 * percolation.numberOfOpenSites()) / (n * n);
            thresholdList[i] = threshold;
        }
    }

    // test client
    public static void main(String[] args) {

        if (args.length == 0 || args.length > 2) throw new IllegalArgumentException();

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.printf("%-23s = %f\n", "mean", ps.mean());
        StdOut.printf("%-23s = %f\n", "stddev", ps.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval", ps.confidenceLo(), ps.confidenceHi());
    }

    private static int getRow(int id, int n) {
        int row = id / n;
        if (id % n != 0) row += 1;
        return row;
    }

    private static int getCol(int id, int n) {
        int col = id % n;
        if (col == 0) return n;
        return col;
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(this.thresholdList);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(this.thresholdList);
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return this.mean() - ((CI_FACTOR * this.stddev()) / Math.sqrt(trials));
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return this.mean() + ((CI_FACTOR * this.stddev()) / Math.sqrt(trials));
    }
}