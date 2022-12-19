package Problems.CollinearPoints;
/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java LineSegment.java
 *
 *  Finds all line segments containing 4 or more points in a plane using sorting by slope method.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++)
                if (points[j] == null || points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
        }

        this.segments = new ArrayList<>();
        this.findCollinearPoints(points);
    }

    private void findCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            // ith point is origin
            Point origin = points[i];
            // rest are sorted by slope
            Point[] others = new Point[points.length - i - 1];
            int id=0;
            for (int j = i+1; j < points.length; j++)
                    others[id++] = points[j];

            Arrays.sort(others, origin.slopeOrder());
            Point min, max;
            for(int j =0;j<others.length-2;j++){
                Point p,q,r,s;
                p = points[i];
                q = others[j];
                r = others[j+1];
                s = others[j+2];
                double slope_pq, slope_pr, slope_ps;
                slope_pq = p.slopeTo(q);
                slope_pr = p.slopeTo(r);
                slope_ps = p.slopeTo(s);
                if (slope_pq == slope_pr && slope_pr == slope_ps) {
                    min = findMin(p, q, r, s);
                    max = findMax(p, q, r, s);
                    LineSegment ls = new LineSegment(min, max);
                    this.segments.add(ls);
                }
            }
        }
    }

    private Point findMin(Point p, Point q, Point r, Point s) {
        Point min = p;
        if (min.compareTo(q) > 0) min = q;
        if (min.compareTo(r) > 0) min = r;
        if (min.compareTo(s) > 0) min = s;
        return min;
    }

    private Point findMax(Point p, Point q, Point r, Point s) {
        Point max = p;
        if (max.compareTo(q) < 0) max = q;
        if (max.compareTo(r) < 0) max = r;
        if (max.compareTo(s) < 0) max = s;
        return max;
    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return this.segments.size();
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[this.numberOfSegments()];
        for (int i = 0; i < this.numberOfSegments(); i++)
            lines[i] = this.segments.get(i);
        return lines;
    }
}