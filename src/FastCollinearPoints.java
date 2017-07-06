import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] segments;

    private static boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    private static boolean hasNull(Point[] points) {
        for (int i = 0; i < points.length; i++) { if (points[i].equals(null)) return true; }
        return false;
    }

    private static boolean collinear(Point[] points) {
        for (int p = 0; p < points.length - 2; p++) {
            if (points[p].slopeTo(points[p+1]) != points[p+1].slopeTo(points[p+2])) return false;
        }
        return true;
    }

    public FastCollinearPoints(Point[] points) {
        if (points.equals(null) || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException();

        Stack<LineSegment> segmentstack = new Stack<>();
        int N = points.length;
        Arrays.sort(points);

        for (Point base: points) {
            Comparator<Point> slopeorder = base.slopeOrder();

            Arrays.sort(points, slopeorder);

            for (int j = 0; j < N; j++) {
                int c = 1;
                while (j+c < N && collinear(new Point[]{base, points[j], points[j + c]})) c++;
                if (c>2) segmentstack.push(new LineSegment(base, points[j+c-1]));
            }
        }

        int size = segmentstack.size();
        segments = new LineSegment[size];
        for (int i = 0; i < size; i++) this.segments[i] = segmentstack.pop();
    }

    public int numberOfSegments() { return this.segments.length; }

    public LineSegment[] segments() { return this.segments; }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
