package Problems.CollinearPoints;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SampleClient {

    public static void main(String[] args) {
        int n = 6;
        if (args.length > 0 && args[0] != null) n = Integer.parseInt(args[0]);
        Point[] points = new Point[n * n];

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                points[(i - 1) * n + j - 1] = new Point(i, j);


        FastCollinearPoints collinear = new FastCollinearPoints(points);

        StdDraw.setXscale(-1, n + 4);
        StdDraw.setYscale(-1, n + 4);
        StdDraw.setPenRadius(.005);
        LineSegment x = new LineSegment(new Point(0, 0), new Point(n + 2, 0));
        LineSegment y = new LineSegment(new Point(0, 0), new Point(0, n + 2));
        StdDraw.setPenRadius(.012);
        x.draw();
        y.draw();
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenRadius(.008);
        StdDraw.setPenColor(StdDraw.GRAY);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}