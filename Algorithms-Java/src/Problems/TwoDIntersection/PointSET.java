package Problems.TwoDIntersection;
/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    none
 *  Dependencies: Point2D.java RecHV.java (algs4.jar)
 * <p>
 *  A mutable data type that represents a set of points in the unit square.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    private final SET<Point2D> pointSet;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        this.pointSet = new SET<>();
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
        PointSET ps = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            ps.insert(p);
        }
        StdDraw.enableDoubleBuffering();
        ps.draw();
        Point2D query = new Point2D(0.55, 0.8);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(0.02);
        query.draw();
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        StdDraw.setPenRadius(0.019);
        ps.nearest(query).draw();
        StdDraw.show();
    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return this.pointSet.isEmpty();
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return this.pointSet.size();
    }

    /**
     * @param p add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        if (this.contains(p)) return;

        this.pointSet.add(p);
    }

    /**
     * @param p query point
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        return this.pointSet.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        for (Point2D p : this.pointSet)
            p.draw();
        StdDraw.show();
    }

    /**
     * @param rect query rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Argument is null");

        ArrayList<Point2D> pointList = new ArrayList<>();
        for (Point2D p : this.pointSet)
            if (rect.contains(p)) pointList.add(p);

        return pointList;
    }

    /**
     * @param p query point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        if (isEmpty()) return null;

        Point2D n = null;
        for (Point2D i : this.pointSet) {
            if (n == null) n = i;
            if (Double.compare(p.distanceSquaredTo(i), p.distanceSquaredTo(n)) < 0) n = i;
        }

        return n;
    }
}