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
import java.util.Comparator;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("The argument to the constructor is null");

        Point[] copiedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++){
            if (points[i] == null) throw new IllegalArgumentException("Any point in the array is null");
            copiedPoints[i] = points[i];
        }

        // Sort to detect duplicates
        Arrays.sort(copiedPoints);

        // Repeated point
        for (int i = 1; i < copiedPoints.length; i++)
            if (copiedPoints[i - 1].compareTo(copiedPoints[i]) == 0)
                throw new IllegalArgumentException("Contains a repeated point");

        this.segments = new ArrayList<>();
        this.findCollinearPoints(points);
    }

    private void findCollinearPoints(Point[] points) {

        for (int i = 0; i < points.length; i++) {
            // ith point is origin
            Point origin = points[i];
            // find rest of the points
            Point[] others = new Point[points.length - 1];
            double[][] othersSlopes = new double[points.length - 1][2];

            int id = 0;
            for (int j = 0; j < points.length; j++)
                if (i != j) others[id++] = points[j];   // skip origin

            // sort points by natural order
            Arrays.sort(others, origin.slopeOrder());

            for (int j = 0; j < others.length; j++) {
                othersSlopes[j][0] = origin.slopeTo(others[j]); // save slope
                othersSlopes[j][1] = j;                         // save original index
            }

            // sort them by slope to bring similar points closer
            Arrays.sort(othersSlopes, new Comparator<double[]>() {
                @Override
                public int compare(double[] o1, double[] o2) {
                    return Double.compare(o1[0], o2[0]);
                }
            });

            int j = 0; // j stores the lower bound
            int k = 1; // k stores the upper bound

            // Loop to find 3 or more adjacent equal-slopes

            while (j < others.length - 1) {
                if (k < others.length && othersSlopes[j][0] == othersSlopes[k][0]) k++;
                else {
                    if (k - j > 2) {
                        // Use sorted slope's index to refer back to the 'Point' object
                        int lo = (int) othersSlopes[j][1];
                        int hi = (int) othersSlopes[k - 1][1];
                        Point src = others[lo];
                        Point dest = others[lo];
                        // Find source and destination points in a line segment
                        for(int idx = lo+1; idx<=hi; idx++){
                            if(src.compareTo(others[idx]) > 0) src = others[idx];
                            if(dest.compareTo(others[idx]) < 0) dest = others[idx];
                        }
                        if (origin.compareTo(src) < 0) {
                            segments.add(new LineSegment(origin, dest));
                        }
                    }
                    j = k++; // move j by k, and k by j+1
                }
            }
        }
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