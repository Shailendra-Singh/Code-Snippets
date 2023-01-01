package Problems.NBoardPuzzle;
/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    none
 *  Dependencies: Board.java
 * <p>
 *  A data type that models an n-by-n board with sliding tiles.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {

    private final int[] board;
    private final int dim;
    private final int boardLength;
    private final int manhattanDistance;
    private final int hammingDistance;
    private Board twinBoard;

    /**
     * create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     *
     * @param tiles n-by-n array of tiles
     */
    public Board(int[][] tiles) {
        this.dim = tiles.length;
        this.boardLength = this.dim * this.dim;
        this.board = new int[this.boardLength];
        for (int i = 0; i < this.boardLength; i++) {
            int row = getRow(i, this.dim);
            int col = getCol(i, this.dim);
            this.board[i] = tiles[row][col];
        }
        this.hammingDistance = calculateHammingDistance();
        this.manhattanDistance = calculateManhattanDistance();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
//        int[][] a = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        int[][] a = {{1, 2, 3, 4, 5}, {12, 6, 8, 9, 10}, {0, 7, 13, 19, 14}, {11, 16, 17, 18, 15}, {21, 22, 23, 24, 20}};
        int[][] c = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(a);
        StdOut.println("E:\t" + b.equals(new Board(c)));
        StdOut.println("H:\t" + b.hamming());
        StdOut.println("M:\t" + b.manhattan());
        StdOut.println("G:\t" + b.isGoal());
        StdOut.println("T:\t" + b.twin());
        StdOut.println("N:\t");
        for (Board bd : b.neighbors())
            StdOut.println(bd);
    }

    private static int getRow(int id, int n) { return id / n; }

    private static int getCol(int id, int n) { return id % n; }

    private static void exch(int[] a, int i, int j) {
        int swap = a[i]; a[i] = a[j]; a[j] = swap;
    }

    private static int[][] getBoardArray(int[] a, int dim) {
        int[][] newBoard = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            System.arraycopy(a, i * dim, newBoard[i], 0, dim);

        return newBoard;
    }

    /**
     * @return string representation of this board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.dim);
        for (int i = 0; i < this.boardLength; i++) {
            if (i % this.dim == 0) sb.append("\n");
            sb.append(" ").append(this.board[i]);
        }
        return sb.toString();
    }

    /**
     * @return board dimension n
     */
    public int dimension() { return this.dim; }

    /**
     * @return number of tiles out of place
     */
    public int hamming() { return this.hammingDistance; }

    private int calculateHammingDistance() {
        int hammingDistance = 0;

        for (int i = 0; i < this.boardLength - 1; i++)
            if (this.board[i] != i + 1) hammingDistance++;

        return hammingDistance;
    }

    /**
     * @return sum of Manhattan distances between tiles and goal
     */
    public int manhattan() { return this.manhattanDistance; }

    private int calculateManhattanDistance() {
        int manhattanDistance = 0;
        for (int i = 0; i < this.boardLength; i++) {
            if (this.board[i] == 0) continue;

            int orgRow = getRow(i, this.dim);
            int orgCol = getCol(i, this.dim);
            int keyRow = getRow(this.board[i] - 1, this.dim);
            int keyCol = getCol(this.board[i] - 1, this.dim);

            manhattanDistance += Math.abs(orgRow - keyRow) + Math.abs(orgCol - keyCol);
        }
        return manhattanDistance;
    }

    /**
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        if (this.board[this.boardLength - 1] != 0) return false;

        for (int i = 0; i < this.boardLength - 1; i++)
            if (this.board[i] != i + 1) return false;

        return true;
    }

    /**
     * @param y board y
     * @return does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null) return false;

        // equals method should not assume anything about the type of its argument
        if (getClass() != y.getClass()) return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) return false;

        for (int i = 0; i < this.boardLength; i++)
            if (this.board[i] != that.board[i]) return false;

        return true;
    }

    /**
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int n = this.dim;
        ArrayList<Board> neighborsList = new ArrayList<>();

        // Find blank tile
        int id = this.boardLength - 1;
        for (int i = 0; i < this.boardLength; i++)
            if (this.board[i] == 0) id = i;

        int row = getRow(id, this.dim);
        int col = getCol(id, this.dim);

        // Find Left, Right, Above, and Bottom tiles

        // Check Left
        if (col - 1 >= 0) {
            int idLeft = id - 1;
            int[] temp = new int[this.boardLength];
            System.arraycopy(this.board, 0, temp, 0, this.boardLength);
            exch(temp, id, idLeft);
            neighborsList.add(new Board(getBoardArray(temp, this.dim)));
        }

        // Check Right
        if (col + 1 < n) {
            int idRight = id + 1;
            int[] temp = new int[this.boardLength];
            System.arraycopy(this.board, 0, temp, 0, this.boardLength);
            exch(temp, id, idRight);
            neighborsList.add(new Board(getBoardArray(temp, this.dim)));
        }

        // Check Above
        if (row - 1 >= 0) {
            int idAbove = id - n;
            int[] temp = new int[this.boardLength];
            System.arraycopy(this.board, 0, temp, 0, this.boardLength);
            exch(temp, id, idAbove);
            neighborsList.add(new Board(getBoardArray(temp, this.dim)));
        }

        // Check Below
        if (row + 1 < n) {
            int idBelow = id + n;
            int[] temp = new int[this.boardLength];
            System.arraycopy(this.board, 0, temp, 0, this.boardLength);
            exch(temp, id, idBelow);
            neighborsList.add(new Board(getBoardArray(temp, this.dim)));
        }

        return neighborsList;
    }

    /**
     * @return a board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        if (this.twinBoard != null) return this.twinBoard;

        int i = StdRandom.uniformInt(0, this.boardLength);
        int j = StdRandom.uniformInt(0, this.boardLength);
        // Find a random index until it is not a blank tile
        while (this.board[i] == 0) i = StdRandom.uniformInt(0, this.boardLength);
        // Find a random index until it is different and not a blank tile
        while (j == i || this.board[j] == 0) j = StdRandom.uniformInt(0, this.boardLength);

        int[] temp = new int[this.boardLength];
        System.arraycopy(this.board, 0, temp, 0, this.boardLength);
        exch(temp, i, j);
        this.twinBoard = new Board(getBoardArray(temp, this.dim));
        return this.twinBoard;
    }
}