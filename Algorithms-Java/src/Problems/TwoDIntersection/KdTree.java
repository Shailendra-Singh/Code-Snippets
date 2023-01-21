package Problems.TwoDIntersection;
/******************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    none
 *  Dependencies: Point2D.java RecHV.java (algs4.jar)
 * <p>
 *  A BST with points in the nodes,
 *  using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 * <p>
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private final Point2dBST point2dTree;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        this.point2dTree = new Point2dBST();
    }

    /**
     * unit testing of the methods (optional)
     *
     * @param args (optional)
     */
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdDraw.enableDoubleBuffering();
        kdtree.draw();
    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return this.point2dTree.isEmpty();
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return this.point2dTree.size();
    }

    /**
     * @param p add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        if (this.contains(p)) return;

        this.point2dTree.put(p);
    }

    /**
     * @param p query point
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        return this.point2dTree.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        draw(point2dTree.root, 0, 0, 1, 1);
    }

    private void draw(Point2dBST.Node x, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) return;

        Point2D p = x.key;
        if (x.isVertical) {
            drawVLine(p.x(), ymin, p.x(), ymax);
            drawPoint(p);

            draw(x.left, xmin, ymin, p.x(), ymax);
            draw(x.right, p.x(), ymin, xmax, ymax);
        } else {
            drawHLine(xmin, p.y(), xmax, p.y());
            drawPoint(p);

            draw(x.left, xmin, ymin, xmax, p.y());
            draw(x.right, xmin, p.y(), xmax, ymax);
        }
    }

    private void drawPoint(Point2D point) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        point.draw();
        StdDraw.show();
    }

    private void drawHLine(double x0, double y0, double x1, double y1) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(x0, y0, x1, y1);
        StdDraw.show();
    }

    private void drawVLine(double x0, double y0, double x1, double y1) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(x0, y0, x1, y1);
        StdDraw.show();
    }

    /**
     * @param rect query rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Argument is null");

        ArrayList<Point2D> pointList = new ArrayList<>();
        range(point2dTree.root, rect, pointList);
        return pointList;
    }

    private void range(Point2dBST.Node x, RectHV rect, ArrayList<Point2D> pointList) {
        if (x == null) return;
        boolean isLeft, isIntersecting;
        RectHV rectThat;
        Point2D p = x.key;
        if (x.isVertical) {
            rectThat = new RectHV(p.x(), 0, p.x(), 1);
            isIntersecting = rect.intersects(rectThat);
            isLeft = rect.xmax() < p.x();
        } else {
            rectThat = new RectHV(0, p.y(), 1, p.y());
            isIntersecting = rect.intersects(rectThat);
            isLeft = rect.ymax() < p.y();
        }

        if (isIntersecting) { // search both subtrees
            // if point is within query rectangle, add to range search list
            if (rect.contains(p)) pointList.add(p);

            range(x.left, rect, pointList);
            range(x.right, rect, pointList);
        } else {
            if (isLeft) range(x.left, rect, pointList);
            else range(x.right, rect, pointList);
        }
    }

    /**
     * @param p query point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        if (isEmpty()) return null;

        Point2D champion;
        champion = nearest(point2dTree.root, p, null, 0, 0, 1, 1);
        return champion;
    }

    private Point2D nearest(Point2dBST.Node x, Point2D queryPoint, Point2D champion, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) return champion;

        Point2D currentPoint = x.key;
        if (champion == null) champion = currentPoint;
        else if (queryPoint.distanceSquaredTo(currentPoint) < queryPoint.distanceSquaredTo(champion))
            champion = currentPoint; // better champion found

        double currentPointX = currentPoint.x();
        double currentPointY = currentPoint.y();
        boolean isLeft;
        RectHV currentPartition;
        if (x.isVertical) {
            isLeft = queryPoint.x() < currentPointX;
            currentPartition = new RectHV(currentPointX, Math.min(ymin, ymax), currentPointX, Math.max(ymin, ymax));
        } else {
            isLeft = queryPoint.y() < currentPointY;
            currentPartition = new RectHV(Math.min(xmin, xmax), currentPointY, Math.max(xmin, xmax), currentPointY);
        }

        boolean alreadyCloser;

        // Decide closer subtree to the query point
        if (isLeft) {
            if (x.isVertical)
                // limit next iteration's xmax, if going left from vertical
                champion = nearest(x.left, queryPoint, champion, xmin, ymin, currentPointX, ymax);
            else
                // limit next iteration's ymax, if going left from horizontal
                champion = nearest(x.left, queryPoint, champion, xmin, ymin, xmax, currentPointY);

            alreadyCloser = queryPoint.distanceSquaredTo(champion) < currentPartition.distanceSquaredTo(queryPoint);
            if (alreadyCloser) return champion; // prune right

            if (x.isVertical)
                // limit next iteration's xmin, if going right from vertical
                champion = nearest(x.right, queryPoint, champion, currentPointX, ymin, xmax, ymax);
            else
                // limit next iteration's ymin, if going left from horizontal
                champion = nearest(x.right, queryPoint, champion, xmin, currentPointY, xmax, ymax);
        } else {
            if (x.isVertical)
                // limit next iteration's xmin, if going right from vertical
                champion = nearest(x.right, queryPoint, champion, currentPointX, ymin, xmax, ymax);
            else
                // limit next iteration's ymin, if going left from horizontal
                champion = nearest(x.right, queryPoint, champion, xmin, currentPointY, xmax, ymax);

            alreadyCloser = queryPoint.distanceSquaredTo(champion) < currentPartition.distanceSquaredTo(queryPoint);
            if (alreadyCloser) return champion; // prune left

            if (x.isVertical)
                // limit next iteration's xmax, if going left from vertical
                champion = nearest(x.left, queryPoint, champion, xmin, ymin, currentPointX, ymax);
            else
                // limit next iteration's ymax, if going left from horizontal
                champion = nearest(x.left, queryPoint, champion, xmin, ymin, xmax, currentPointY);
        }

        return champion;
    }

    private static class Point2dBST {
        private Node root;

        /**
         * put key into the table
         *
         * @param key item key
         */
        public void put(Point2D key) {
            root = put(root, key, true);
        }

        private Node put(Node x, Point2D key, boolean isVertical) {
            if (x == null) return new Node(key, isVertical);
            int cmp = x.getCompareTo(key);
            if (cmp <= 0) x.left = put(x.left, key, !isVertical);
            else x.right = put(x.right, key, !isVertical);
            x.count = 1 + size(x.left) + size(x.right);
            return x;
        }

        /**
         * @param key item key
         * @return value paired with key (null if key is absent)
         */
        public Point2D get(Point2D key) {
            Node x = get(root, key);
            if (x == null) return null;
            return x.key;
        }

        private Node get(Node x, Point2D key) {
            while (x != null) {
                int cmp = x.getCompareTo(key);
                if (cmp < 0) x = x.left;
                else if (cmp > 0) x = x.right;
                else if (x.key.compareTo(key) == 0) return x;
                else x = x.left;
            }
            return null;
        }

        /**
         * @return is the table empty?
         */
        public boolean isEmpty() {
            return root == null;
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) return 0;
            return x.count;
        }

        /**
         * @param key item key
         * @return is there a value paired with key?
         */
        public boolean contains(Point2D key) {
            return get(key) != null;
        }

        /**
         * Represents a node in a tree with key and value.
         * Left sub-nodes have all keys smaller than this node, right sub-nodes have keys larger than this node.
         */
        private static class Node {
            private final boolean isVertical;
            private final Point2D key;
            private Node left, right;
            private int count;

            public Node(Point2D key, boolean isVertical) {
                this.key = key;
                this.isVertical = isVertical;
                this.count = 1;
            }

            public int getCompareTo(Point2D that) {
                if (this.isVertical) return Double.compare(that.x(), this.key.x());

                return Double.compare(that.y(), this.key.y());
            }
        }
    }
}