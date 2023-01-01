package Problems.NBoardPuzzle;
/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    none
 *  Dependencies: Board.java
 * <p>
 *  Program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private final boolean isSolvableFlag;
    private Board[] solutionList;

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial board
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Argument to the constructor is null");
        this.isSolvableFlag = this.solve(initial);
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles = {{8, 6, 7}, {2, 5, 4}, {3, 0, 1}};

        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }

    /**
     * @return is the initial board solvable?
     */
    public boolean isSolvable() {
        return this.isSolvableFlag;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (this.isSolvable()) return this.solutionList.length - 1;

        return -1;
    }

    /**
     * @return sequence of boards in the shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (this.isSolvable()) return List.of(this.solutionList);

        return null;
    }

    /**
     * Solve using A* search.
     * <p>
     * A* (pronounced "A-star") is a graph traversal and path search algorithm,
     * which is used in many fields of computer science due to its completeness, optimality, and optimal efficiency.
     * One major practical drawback is its space complexity, as it stores all generated nodes in memory.
     * Thus, in practical travel-routing systems,
     * it is generally outperformed by algorithms which can pre-process the graph to attain better performance,
     * as well as memory-bounded approaches; however, A* is still the best solution in many cases.
     * <p>
     * A* Search: First, insert the initial search node
     * (the initial board, 0 moves, and a null previous search node) into a priority queue.
     * Then, delete from the priority queue the search node with the minimum priority,
     * and insert onto the priority queue all neighboring search nodes
     * (those that can be reached in one move from the dequeued search node).
     * Repeat this procedure until the search node dequeued corresponds to the goal board.
     */
    private boolean solve(Board initial) {
        MinPQ<Node> initialPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();

        // Add the starting board to the priority queue
        initialPQ.insert(new Node(initial, null, 0));
        twinPQ.insert(new Node(initial.twin(), null, 0));

        boolean lockStep = true;
        MinPQ<Node> pq = initialPQ;
        ArrayList<Node> nodeList = new ArrayList<>();

        while (true) {
            Node boardNode = pq.delMin();

            // Add neighbors
            for (Board b : boardNode.getSearchBoard().neighbors())
                // don't add previous step if present in neighbors
                if (boardNode.getPrevBoard() == null || !b.equals(boardNode.getPrevBoard().getSearchBoard()))
                    pq.insert(new Node(b, boardNode, boardNode.getMoves() + 1));

            if (lockStep) { // switch between initial and twin's turn
                nodeList.add(boardNode);
                pq = twinPQ;
            } else {
                pq = initialPQ;
            }

            lockStep = !lockStep;
            if (boardNode.getSearchBoard().isGoal()) break;
        }

        Node lastNode = nodeList.get(nodeList.size() - 1);
        int moves = lastNode.moves;
        this.solutionList = new Board[moves + 1]; // include initial board
        int idx = moves;
        // add in reverse order for sequence of moves from the initial board to the goal board
        while (lastNode != null) {
            this.solutionList[idx--] = lastNode.getSearchBoard();
            lastNode = lastNode.prev;
        }

        return this.solutionList[moves].isGoal();
    }

    private static class Node implements Comparable<Node> {
        private final Board board;
        private final Node prev;
        private final int moves;
        private final int manhattanPriority;

        public Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.manhattanPriority = this.board.manhattan() + this.moves;
        }

        public int getManhattanPriority() { return this.manhattanPriority; }

        public Node getPrevBoard() { return this.prev; }

        public Board getSearchBoard() { return this.board; }

        public int getMoves() { return this.moves; }

        /**
         * @param that the object to be compared.
         * @return compareTo value using manhattan priority (manhattan distance + number of moves)
         */
        @Override
        public int compareTo(Solver.Node that) { return this.getManhattanPriority() - that.getManhattanPriority(); }
    }
}