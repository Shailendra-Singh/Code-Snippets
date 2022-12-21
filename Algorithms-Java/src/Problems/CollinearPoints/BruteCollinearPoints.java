package Problems.CollinearPoints;
/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java LineSegment.java
 *
 *  Finds all line segments containing 4 points in a plane using brute-force method.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < points.length - 3; i++)
            for (int j = i + 1; j < points.length - 2; j++)
                for (int k = j + 1; k < points.length - 1; k++)
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        double slope_pq, slope_pr, slope_ps;
                        slope_pq = p.slopeTo(q);
                        slope_pr = p.slopeTo(r);
                        slope_ps = p.slopeTo(s);
                        if (slope_pq == slope_pr && slope_pr == slope_ps) {
                            Point min = findMin(p, q, r, s);
                            Point max = findMax(p, q, r, s);
                            LineSegment ls = new LineSegment(min, max);
                            this.segments.add(ls);
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